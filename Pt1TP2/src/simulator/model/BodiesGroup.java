package simulator.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body>{
	private String id;
	private List<Body> _bodies;
	private List<Body> _bodiesRO;
	private ForceLaws _forceLaws;
	
	//Constructor
	public BodiesGroup(String id, ForceLaws forceLaws) throws IllegalArgumentException{
		//Debe recibir las leyes de la fuerza
		if(id == null || id.trim().length()<=0 || forceLaws == null) {
			throw new IllegalArgumentException("Wrong arguments");
		}
		this.id = id;
		this._forceLaws = forceLaws;
		this._bodies=new LinkedList<Body>();
		this._bodiesRO = Collections.unmodifiableList(_bodies);
	}
	
	public String getId() {
		return this.id;
	}
	
	void setForceLaws(ForceLaws fl) throws IllegalArgumentException{
		if(fl==null)
			throw new IllegalArgumentException("Force Laws is null");
		
		this._forceLaws=fl;
	}
	
	void addBody(Body b) throws IllegalArgumentException{
		//Comprobar q el identificador no esta repe
		if(b == null ||_bodies.contains(b))
				throw new IllegalArgumentException("Bodies already contains this body or is null");
			
		_bodies.add(b);

	}
	
	void advance(double dt)  throws IllegalArgumentException{
		if(dt > 0) { //Excepcion si dt no es positivo
			for(Body body: _bodies) {
				body.resetForce();
			}
			_forceLaws.apply(_bodies);
			for(Body body: _bodies) {
				body.advance(dt);
			}
		} else {
			throw new IllegalArgumentException("Dt is negative");
		}
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		
		JSONArray ja = new JSONArray();
		for(Body body: _bodies) {
			ja.put(body.getState());
		}

		jo.put("id", id);
		jo.put("bodies", ja);

		return jo;
	}
	
	public String toString() {
		
		return getState().toString();
	}
	
	public String getForceLawsInfo(){
		return _forceLaws.toString();
	}

	@Override
	public Iterator<Body> iterator() {
		return _bodiesRO.iterator();
	}


	
}