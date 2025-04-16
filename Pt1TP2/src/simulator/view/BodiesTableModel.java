package simulator.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{
	
	String[] _header = {"Id", "gId", "Mass", "Velocity", "Position", "Force"};
	List<Body> _bodies;
	
	BodiesTableModel(Controller ctrl){
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	//Resto de metodos

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o=null;
		
		Body g=this._bodies.get(rowIndex);
		switch (columnIndex ) {
		case 0: o = g.getId();
			break;
		case 1: o = g.getgId();
		 	break;
		case 2: o = g.getMass();
			break;
		case 3: o = g.getVelocity();
		 	break;
		case 4: o = g.getPosition();
		 	break;
		case 5: o = g.getForce();
		 	break;
		}
		return o;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		fireTableStructureChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._bodies.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {

		for(BodiesGroup g: groups.values()) {
			Iterator<Body> i = g.iterator();
			while(i.hasNext()) {
				this._bodies.add(i.next());
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		Iterator<Body> i = g.iterator();
		while(i.hasNext()) {
			Body b=i.next();
			this._bodies.add(b);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		this._bodies.add(b);
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}
