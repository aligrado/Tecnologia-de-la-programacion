package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body>{

	public StationaryBodyBuilder() {
		super("st_body", "Create a body who can not move");
	}

	@Override
	protected StationaryBody createInstance(JSONObject data) throws IllegalArgumentException{
		if(data == null || !data.has("id") ||!data.has("gid") ||!data.has("p") ||!data.has("m")||data.getJSONArray("p").length()!=2 ) {
			throw new IllegalArgumentException();
		}
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray position = data.getJSONArray("p");
		Double mass = data.getDouble("m");
		Vector2D pos = new Vector2D(position.getDouble(0), position.getDouble(1));
		
		StationaryBody sb = new StationaryBody(id, gid, pos, mass);
		
		return sb;
	}

}