package simulator.factories;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder() {
		super("nf", "Create a void Law");
	}

	@Override
	protected NoForce createInstance(JSONObject data) throws IllegalArgumentException{

		NoForce nf = new NoForce();
		
		return nf;
	}
	
	public JSONObject getInfo() {
		JSONObject jo = new JSONObject();
		JSONObject data = new JSONObject();
		
		jo.put("type", "nf");
		jo.put("desc", "No force");
		jo.put("data", data);//data: {} ns si aquii se pondria un array vacio o q
		
		return jo;
	}
}