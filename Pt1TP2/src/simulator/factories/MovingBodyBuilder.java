package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {
	
	public MovingBodyBuilder() {
		super("mv_body", "Create a body who can move");
	}

	@Override
	protected MovingBody createInstance(JSONObject data)  throws IllegalArgumentException{
		if(data == null || !data.has("id") ||!data.has("gid") ||!data.has("p") ||!data.has("v") ||!data.has("m")||data.getJSONArray("p").length()!=2 || data.getJSONArray("v").length()!=2) 
			throw new IllegalArgumentException();
		
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray position = data.getJSONArray("p");
		JSONArray velocity = data.getJSONArray("v");
		Double mass = data.getDouble("m");
		Vector2D pos = new Vector2D(position.getDouble(0), position.getDouble(1));
		Vector2D vel = new Vector2D(velocity.getDouble(0), velocity.getDouble(1));
		
		MovingBody mb = new MovingBody(id, gid, pos, vel, mass);
		
		return mb;
	}

}