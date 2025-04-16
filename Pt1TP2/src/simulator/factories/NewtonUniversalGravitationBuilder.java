package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Create an instance of a Newton law");
	}

	@Override
	protected NewtonUniversalGravitation createInstance(JSONObject data) throws IllegalArgumentException{
		
		double G =6.67E-11;
			if(data.has("G")) {
				G=data.getDouble("G");
				
			}

		
		NewtonUniversalGravitation mb = new NewtonUniversalGravitation(G);
		
		return mb;
	}
	
	public JSONObject getInfo() {
		JSONObject jo = new JSONObject();
		jo.put("type", "nlug");
		jo.put("desc", "Newtons law of universal gravitation");
		JSONObject jo2 = new JSONObject();
		jo2.put("G", "the gravitational constant (a number)");
		jo.put("data", jo2);//data: {} creo q sería un JSONArray con la info de cada cte
		
		return jo;
	}

}