package simulator.control;

import simulator.factories.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;////////////////NS SI ES ESTA LIBRERIA
import simulator.model.*;

public class Controller {
	
	private PhysicsSimulator simulator;
	private Factory<Body> fb;
	private Factory<ForceLaws> ffl;
	

	public Controller(PhysicsSimulator simulator, Factory<Body> fb, Factory<ForceLaws> ffl) {
		this.simulator = simulator;
		this.fb = fb;
		this.ffl = ffl;
	}
	
	public void loadData(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		//addGroup del simulador para cada gi
		JSONArray groups = jsonInput.getJSONArray("groups");
		for(int i = 0; i < groups.length(); i++) {
			simulator.addGroup(groups.getString(i));
		}
		
		//setForceLaws del simulador para cada li (hay que convertir li en un
		//objeto usando la factoria
		if(jsonInput.has("laws")) {
			JSONArray laws = jsonInput.getJSONArray("laws");////////////////revisar
			for(int j = 0; j < laws.length(); j++) {
				JSONObject jo = laws.getJSONObject(j);
				ForceLaws fl = ffl.createInstance(jo.getJSONObject("laws"));
				simulator.setForceLaws(jo.getString("id"), fl);
			}
		}
		
		//addBody para cada bbi (hay que converit bbi a un objeto con la
		//factoria
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		for(int k = 0; k < bodies.length(); k++) {
			Body b = fb.createInstance(bodies.getJSONObject(k));
			simulator.addBody(b);
		}
		
		
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println("\"states\": [");
		
		for(int i = 0; i <= n; i++) {
			p.println(simulator.toString());;
			if(i < n) {
				simulator.advance();
				p.print(",");
			}
		}
		
		p.println("]");
		p.println("}");
	}
	
	public void reset() {
		simulator.reset();
	}
	
	public void setDeltaTime(double dt) {
		simulator.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		simulator.addObserver(o);
	}
	
	public void removeObserver(SimulatorObserver o) {
		simulator.removeObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return ffl.getInfo();
	}
	
	public void setForceLaws(String gId, JSONObject info) {
		ForceLaws fl = ffl.createInstance(info);
		simulator.setForceLaws(gId, fl);
	}
	
	public void run(int n) {
		//esto lo está mostrando en un output???
		for(int i = 0; i < n; i++) {
			simulator.advance();
		}
	}
}
