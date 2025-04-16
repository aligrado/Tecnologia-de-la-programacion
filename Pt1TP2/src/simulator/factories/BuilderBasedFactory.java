package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	
	public BuilderBasedFactory() {
		_builders = new HashMap<String, Builder<T>>();
		_buildersInfo = new LinkedList<JSONObject>();
	}
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		
		for(Builder b: builders) {
			addBuilder(b);
		}
	}
	
	public void addBuilder(Builder<T> b) {
		
		if(_builders.containsKey(b.getTypeTag()))
			throw new IllegalArgumentException();
		
		_builders.put(b.getTypeTag(), b);
		_buildersInfo.add(b.getInfo());
	}
	
	@Override
	public T createInstance(JSONObject info) {
		if(info == null || !_builders.containsKey(info.get("type"))) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		
		T instance = _builders.get(info.getString("type")).createInstance(info . has("data") ? info . getJSONObject("data") : new JSONObject());
		
		if(instance == null) {
			throw new IllegalArgumentException();
		}else {
			return instance;
		}
		

	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}

}