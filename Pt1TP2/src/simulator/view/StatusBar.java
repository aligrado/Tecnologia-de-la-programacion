package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class StatusBar extends JPanel implements SimulatorObserver{
	//Añadir atributos necesarios si hacen falta
	private JLabel _time;
	private JLabel _numGroups;
	
	StatusBar(Controller ctrl){
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		//Crear etiqueta de tiempo y añadirla al panel
		_time = new JLabel();
		_time.setText("Time: " + 0);
		_time.setPreferredSize(new Dimension(150, 20));
		this.add(_time);
		
		//Codigo separador vertical
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
		
		//Crear etiqueta de numero de grupos y añadirla al panel
		_numGroups = new JLabel();
		_numGroups.setText("Groups: " + 0);
		_numGroups.setPreferredSize(new Dimension(100, 20));
		this.add(_numGroups);
		
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		separator.setPreferredSize(new Dimension(10, 20));
		this.add(separator);
		
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		_time.setText("Time: " + time);
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_numGroups.setText("Groups: " + 0);
		_time.setText("Time: " + 0);
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_numGroups.setText("Groups: " + groups.size());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}
