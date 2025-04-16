package simulator.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel{
	String _title;
	TableModel _tableModel;
	
	InfoTable(String title, TableModel tableModel){
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	
	private void initGUI() {
		//Cambiar el layout del panel a BorderLayout()
		this.setLayout(new BorderLayout());
		
		//Añadir un borde con titulo al JPanel con el texto _title
		Border border = new TitledBorder(_title);
		this.setBorder(border);
		//this.setName(_title);
		
		//Añadir un JTable (con barra de desplazamiento vertical) que use _tableModel
		JTable table = new JTable(_tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		
	}
}
