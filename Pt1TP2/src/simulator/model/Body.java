package simulator.model;

import java.util.Comparator;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {
	protected String id;
	protected String gid;
	protected Vector2D velocity;
	protected Vector2D force;
	protected Vector2D position;
	protected double mass;
	
	//Constructor
	public Body(String id, String gid, Vector2D velocity, Vector2D position, double mass) throws IllegalArgumentException{
		if(id == null || gid == null || id.trim().length()<=0 || gid.trim().length()<=0 || velocity == null || position == null || mass <= 0) { //Ver si lo del trim esta bien
			throw new IllegalArgumentException();
		}else {
			//No me deja ver si mass es null
			
			this.velocity = new Vector2D(0, 0);
			this.force = new Vector2D();			
			this.id = id;
			this.gid = gid;
			this.velocity = velocity;
			this.position = position;
			this.mass = mass;
		}

	}
	
	public String getId() {
		return this.id;
	}
	
	public String getgId() {
		return this.gid;
	}
	
	public Vector2D getVelocity() {
		return this.velocity;
	}
	
	public Vector2D getForce() {
		return this.force;
	}
	
	public Vector2D getPosition() {
		return this.position;
	}
	
	public double getMass() {
		return this.mass;
	}
	
	 void addForce(Vector2D f) {
		this.force=force.plus(f);
		
	}
	
	void resetForce() {
		this.force = new Vector2D();
	}
	
	abstract void advance(double dt);
	
	public JSONObject getState() {
		JSONObject jo1 = new JSONObject();
		jo1.put("p",position.asJSONArray());
		jo1.put("v",velocity.asJSONArray());
		jo1.put("f",force.asJSONArray());
		jo1.put("id", id);
		jo1.put("m", mass);

		return jo1;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	@Override
	public boolean equals (Object obj) {
		Body b= (Body)obj;
		return this.id.equals(b.getId());
	}
}