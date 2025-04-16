package simulator.factories;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Create an instance of a law which made force on a body to the poin");
	}	

	@Override
	protected MovingTowardsFixedPoint createInstance(JSONObject data) throws IllegalArgumentException{
		double g=9.8;
		Vector2D c1 = new Vector2D (0.0,0.0);

		if(data.has("g")) {
			g=data.getDouble("g");
		}

		if (data.has("c")) {

			JSONArray aux=(JSONArray)data.get("c");
			c1= new Vector2D (aux.getDouble(0),aux.getDouble(1));
		}
		
		MovingTowardsFixedPoint mtfp = new MovingTowardsFixedPoint(c1, g);

		return mtfp;
	}
	
	public JSONObject getInfo() {
		JSONObject jo = new JSONObject();
		jo.put("type", "mtfp");
		jo.put("desc", "Moving towards a fixed point");
		Map<String, String> data = new HashMap<String, String>();
		data.put("c", "the point towards which bodies move (e.g., [100.0, 50.0]");
		data.put("g", "the lenght of the acceleration vector (a number)");
		jo.put("data", data);//data: {} lo mismo q en el nlug
		
		return jo;
	}

}