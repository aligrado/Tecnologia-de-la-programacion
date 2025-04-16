package simulator.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver{
	
	String[] _header = { "Id", "Force Laws", "Bodies"};
	List<BodiesGroup> _groups;
	
	GroupsTableModel(Controller ctrl){
		_groups = new ArrayList<>();
		ctrl.addObserver(this);
	}
	//Resto de metodos

	@Override
	public int getRowCount() {
		return _groups.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o=null;
		
		BodiesGroup g=this._groups.get(rowIndex);
		
		switch (columnIndex ) {
		case 0: o = g.getId();
			break;
		case 1: o = g.getForceLawsInfo();
		 	break;
		case 2:
				Iterator<Body> i = g.iterator();
				String aux="";
				Body b;
				
				while(i.hasNext()) {
					b = i.next();
					aux = aux+b.getId();
				}
				
				o=aux;
			break;
		}
		
		return o;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._groups.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup g: groups.values()) {
			this._groups.add(g);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		this._groups.add(g);
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}
