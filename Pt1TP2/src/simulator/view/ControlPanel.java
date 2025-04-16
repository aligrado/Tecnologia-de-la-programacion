package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ControlPanel extends JPanel implements SimulatorObserver, ActionListener{
	
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stooped = true;
	
	private JButton _quitButton;
	private JButton _stopButton;
	private JButton _runButton;
	private JButton _viewerButton;
	private JButton _physicsButton;
	private JButton _openButton;
	
	private JTextField _deltaTime;
	private JSpinner _steps;
	//Creo q faltan cosas
	
	private ForceLawsDialog _fld;
	
	ControlPanel(Controller ctrl){
		_ctrl = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		
		/*
		 * BOTONES 
		 * Para poner lo q haga el boton se puede hacer como hemos hecho
		 * (en un metodo a parte en el que ponemos lo q hace cada boton) o
		 * en el addActionListener(new ActionListener(){
		 * 		lo q haga el boton
		 * });
		 * 
		 * Para añadir separacion se puede usar el addSeparator() o el createRigidArea:
		 * _toolaBar.add(Box.createRigidArea(new Dimension()));
		 * 
		 */
		
		_openButton = new JButton();
		_openButton.setToolTipText("Open");
		_openButton.setIcon(new ImageIcon("resources/icons/open.png"));
		_openButton.addActionListener(this);//Se podría poner aqui el new ActionListener(){} y poner dentro del metodo la funcionalidad del boton
		_toolaBar.add(_openButton);
		_toolaBar.addSeparator();
		
		_physicsButton = new JButton();
		_physicsButton.setToolTipText("Physics");
		_physicsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		_physicsButton.addActionListener(this);
		_toolaBar.add(_physicsButton);
		
		_viewerButton = new JButton();
		_viewerButton.setToolTipText("Viewer");
		_viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		_viewerButton.addActionListener(this);
		_toolaBar.add(_viewerButton);
		_toolaBar.addSeparator();
		
		_runButton = new JButton();
		_runButton.setToolTipText("Run");
		_runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		_runButton.addActionListener(this);
		_toolaBar.add(_runButton);
		
		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop");
		_stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopButton.addActionListener(this);
		_toolaBar.add(_stopButton);

		_toolaBar.add(new JLabel("Steps: "));
		_steps = new JSpinner(new SpinnerNumberModel(150, 1, 10000, 100));//valorInicial: 150, minimo: 1, maximo: 10000, incremento: 100
		_steps.setToolTipText("Steps: 1-10000");
		_steps.setPreferredSize(new Dimension(80, 40));
		_steps.setMaximumSize(new Dimension(80, 40));
		_toolaBar.add(_steps);
		
		_toolaBar.add(new JLabel("Delta-Time: "));
		_deltaTime = new JTextField(5);//max 5 caracteres
		_deltaTime.setPreferredSize(new Dimension(80, 40));
		_deltaTime.setMaximumSize(new Dimension(80, 40));
		_deltaTime.setText("2500.0");//DeltaTime inicial, en la demo lo inicializan en 2500.0 (creo q hay q cambiarlo ns)
		//Si meten un valor de dt en el -dt lo tengo q inicializar a ese? lo mismo con steps
		_toolaBar.add(_deltaTime);
		
		_toolaBar.add(Box.createGlue());//A partir de cuando se pone el createGlue las componentes se ponen a la dcha
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);
		
		_fc = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==_openButton) {
			int res =_fc.showOpenDialog(Utils.getWindow(this));
			if(res == JFileChooser.APPROVE_OPTION) {//Si ha seleccionado un fichero
				File file = _fc.getSelectedFile();
				_ctrl.reset();
				try {
					FileInputStream in = new FileInputStream(file);
					_ctrl.loadData(in);
				}catch(FileNotFoundException e1){
					e1.printStackTrace();
				}
			}
		}
		
		if(e.getSource()==_physicsButton) {
			if(_fld == null) {
				_fld = new ForceLawsDialog(Utils.getWindow(this), _ctrl);
			}
			_fld.open();
		}
		
		if(e.getSource()==_viewerButton) {
			ViewerWindow viewer = new ViewerWindow((JFrame) Utils.getWindow(this), _ctrl);
		}
		
		if(e.getSource()==_runButton) {
			_openButton.setEnabled(false);
			_physicsButton.setEnabled(false);
			_viewerButton.setEnabled(false);
			_runButton.setEnabled(false);
			_quitButton.setEnabled(false);
			//Hay q inhabilitar tb lo de los steps y el delta
			
			_stooped = false;
			
			this._ctrl.setDeltaTime(Double.parseDouble(this._deltaTime.getText()));
			run_sim(Integer.parseInt(_steps.getValue().toString()));
		}
		
		if(e.getSource()==_stopButton) {
			_stooped = true;
		}
	}
	
	private void run_sim(int n) {
		if(n > 0 && !_stooped) {
			try {
				_ctrl.run(1);
			}catch(Exception e) {
				Utils.showErrorMsg(e.getMessage());
				
				_openButton.setEnabled(true);
				_physicsButton.setEnabled(true);
				_viewerButton.setEnabled(true);
				_runButton.setEnabled(true);
				_quitButton.setEnabled(true);
				
				_stooped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n-1));
		}else {
			_openButton.setEnabled(true);
			_physicsButton.setEnabled(true);
			_viewerButton.setEnabled(true);
			_runButton.setEnabled(true);
			_quitButton.setEnabled(true);
			
			_stooped = true;
		}
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}

	@Override
	public void onDeltaTimeChanged(double dt) {
		_deltaTime.setText(String.valueOf(dt));
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
	
}
