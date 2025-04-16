package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver{
	
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = {"Key", "Value", "Description"};
	private JButton _ok;
	private JButton _cancel;
	private int _status;
	private int _selectedLawsIndex;
	
	ForceLawsDialog(Frame parent, Controller ctrl){
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		_forceLawsInfo = _ctrl.getForceLawsInfo();
		
		//Crear JTable q use _dataTableModel y añadirla al panel
		_dataTableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return column == 1;//Hacer q solo sea editable la primera columna
			}
		};
		_dataTableModel.setColumnIdentifiers(_headers);//Añadir los headers de las columnas
		
		JTable dtm = new JTable(_dataTableModel);
		JScrollPane scrollPane = new JScrollPane(dtm, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(scrollPane);
		
		
		JPanel comboBoxPanel = new JPanel();//Hago otro panel para q esto quede mejor (aqui metere los comboBox)
		mainPanel.add(comboBoxPanel);
		
		_lawsModel = new DefaultComboBoxModel<>();
		//añadir la descripcion de todas las leyes de fuerza a _lawsModel
		for(int i = 0; i < _forceLawsInfo.size(); i++) {
			_lawsModel.addElement(_forceLawsInfo.get(i).getString("desc"));
		}
		//crear un comboBox que use _lawsModel y añadirlo al panel
		JComboBox<String> lm = new JComboBox<String>(_lawsModel);
		lm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_selectedLawsIndex = lm.getSelectedIndex();
				JSONObject info = _forceLawsInfo.get(_selectedLawsIndex);
				JSONObject data = info.getJSONObject("data");
				
				Object[] dat = new Object[3];
				for(int i = 0; i < _dataTableModel.getRowCount(); i++) {
					_dataTableModel.removeRow(i);
				}
				
				_dataTableModel.setRowCount(0);
				for(String key: data.keySet()) {
					dat[0] = key;
					dat[1] = "";
					dat[2] = data.get(key);
					_dataTableModel.addRow(dat);
				}
				/*
				int i = 0;
				for(String k: data.keySet()) {
					_dataTableModel.setValueAt(k, i, 0);
					_dataTableModel.setValueAt(data.get(k), i, 2);
					i++;
				}
				*/
			}
			
		});
		comboBoxPanel.add(lm);
		
		
		_groupsModel = new DefaultComboBoxModel<>();
		//Crear un comboBox que use _groupsModel y añadirlo al panel
		JComboBox<String> gm = new JComboBox<String>(_groupsModel);
		//ActionListener??
		comboBoxPanel.add(gm);
		
		
		//Como para las comboBox hago otro panel que añado al panel principal
		JPanel buttonPanel = new JPanel();
		mainPanel.add(buttonPanel);
		
		//Crear los botones ok y cancel y añadirlos al panel 
		_ok = new JButton();
		_ok.setText("OK");
		_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject data = new JSONObject(datosTabla());//Datos de la tabla
				JSONObject dataType = new JSONObject();
				
				dataType.put("data", data);
				dataType.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));

				try {
					_ctrl.setForceLaws(_groupsModel.getSelectedItem().toString(), dataType);
					_status = 1;
					setVisible(false);
				}catch(Exception e1) {
					Utils.showErrorMsg(e1.getMessage());
				}
			}
			
		});
		buttonPanel.add(_ok);
		
		_cancel = new JButton();
		_cancel.setText("Cancel");
		_cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				setVisible(false);
			}
			
		});
		buttonPanel.add(_cancel);
		
		
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public void open() {
		//Establecer la posicion de la ventana de dialogo de tal manera que se abra en el centro de la ventana principal
		this.setLocationRelativeTo(this.getParent());
		
		pack();
		setVisible(true);
	}
	//Resto de metodos

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup b: groups.values()) {
			_groupsModel.addElement(b.getId());
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsModel.addElement(g.getId());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
	
	public String datosTabla() {
		StringBuilder sb = new StringBuilder();
		
		sb.append('{');
		for(int i = 0; i < _dataTableModel.getRowCount(); i++) {
			String s1 = _dataTableModel.getValueAt(i, 0).toString();
			String s2 = _dataTableModel.getValueAt(i,  1).toString();
			
			if(!s2.isEmpty()) {
				sb.append('"');
				sb.append(s1);
				sb.append('"');
				sb.append(":");
				sb.append(s2);
				sb.append(',');
			}
			
		}

		if(sb.length()>1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("}");
		
		return sb.toString();
	}
}