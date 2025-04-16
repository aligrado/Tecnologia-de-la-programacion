package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		//Crear ControlPanel y añadirlo en PAGE_START de mainPanel
		ControlPanel ctrlPanel = new ControlPanel(_ctrl);
		mainPanel.add(ctrlPanel, BorderLayout.PAGE_START);
		//Crear StatusBar y añadirlo en PAGE_END de mainPanel
		StatusBar statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		//Crear la tabla de grupos y añadirla a contentPanel
		InfoTable groupTable = new InfoTable("Groups", new GroupsTableModel(_ctrl));
		groupTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupTable);
		
		//Crear la tabla de cuerpos y añadirla a contentPanel
		InfoTable bodiesTable = new InfoTable("Bodies", new BodiesTableModel(_ctrl));
		bodiesTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(bodiesTable);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing() {
				Utils.quit(MainWindow.this);
			}
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
