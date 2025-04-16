package simulator.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.factories.Builder;

public class PhysicsSimulator implements Observable<SimulatorObserver>{

	private double dt;
	
	private ForceLaws laws;	
	
	private double time;
	
	private List<String> gIds;
	
	private Map<String, BodiesGroup> groups;
	
	private Map<String, BodiesGroup> _groupsRO;
	
	private List<SimulatorObserver> observers; //Lista de observadores inicialmente vacia??? //Si, son otras clases las que deben crear los observadores y son ellos los que se añaden directamente
	
	public PhysicsSimulator(ForceLaws laws, double dt) throws IllegalArgumentException {
		if(dt<=0||laws==null)//<=?? // Menor o igual que 0
			throw new IllegalArgumentException();
		
		this.dt = dt;
		this.laws = laws;
		this.time = 0.0;
		this.gIds= new LinkedList<String>();
		this.groups = new HashMap<String, BodiesGroup>();
		this.observers = new LinkedList<SimulatorObserver>();//ns tiene q ser una linkedlist //No  creo que eso importe demasiado rollo una lista cualquiera vale 
		_groupsRO = Collections.unmodifiableMap(groups);
	}

	public void advance() {
		BodiesGroup aux;
		//Con un forEach iteramos sobre la lista de claves
		/*for(String key: this.groups.keySet()) {
			groups.get(key).advance(dt);
		}*/
		/*
		for(BodiesGroup g: this.groups.values()) {
			g.advance(dt);
		}*/
		/*
		for(Map.Entry<String, BodiesGroup> t: this.groups.entrySet())
			t.getValue().advance(dt);
		*/
		for(String key:gIds) {
			aux=groups.get(key);
			aux.advance(dt);
		}

		time += dt;
		
		//Notify()
		for(SimulatorObserver o: observers) {
			o.onAdvance(_groupsRO, time);
		}
	}
	
	public void addGroup(String g) throws IllegalArgumentException{
		if(g==null||groups.containsKey(g)) 
			throw new IllegalArgumentException();
		
		gIds.add(g);
		groups.put(g, new BodiesGroup(g,laws));
		
		BodiesGroup aux = groups.get(g);
		
		//Notify()
		for(SimulatorObserver o: observers) {
			o.onGroupAdded(_groupsRO, aux);
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException{
		if(b==null||!groups.containsKey(b.getgId())) 
			throw new IllegalArgumentException();
		
		BodiesGroup aux=groups.get(b.getgId());
		aux.addBody(b);
		
		//Notify()
		for(SimulatorObserver o: observers) {
			o.onBodyAdded(_groupsRO, b);
		}
	}
	
	public void setForceLaws(String id, ForceLaws fl) throws IllegalArgumentException{
		if(!groups.containsKey(id)) 
			throw new IllegalArgumentException();
		
		BodiesGroup aux=groups.get(id);
		aux.setForceLaws(fl);
		
		//Notify()
		for(SimulatorObserver o: observers) {
			o.onForceLawsChanged(aux);
		}
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		for(String gId: gIds) {
			BodiesGroup aux = groups.get(gId);
			ja.put(aux.getState());
		}

		jo.put("time", time);
		jo.put("groups", ja);
		
		return jo;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public void reset() {
		gIds.clear();
		groups.clear();
		this.time = 0.0;//dt o time?? //Time
		
		//Notify()
		for(SimulatorObserver o: observers) {
			o.onReset(_groupsRO, time, dt);
		}
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException {
		if(dt < 0) {
			throw new IllegalArgumentException("dt no es positivo");
		}
		this.dt = dt;
		
		//Notify()
		for(SimulatorObserver o: observers) {
			o.onDeltaTimeChanged(dt);
		}
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		// A�adir el observador o si no est� en la lista
		if(!observers.contains(o)){
			observers.add(o);
			
			//Notify()
			o.onRegister(_groupsRO, time, dt);
		}
		
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		// Eliminar el observador o de la lista de observadores
		observers.remove(o);
	}
}