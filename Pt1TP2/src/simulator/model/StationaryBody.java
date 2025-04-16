package simulator.model;

import simulator.misc.Vector2D;

public class StationaryBody extends Body{

	public StationaryBody(String id, String gid, Vector2D position, double mass)
			throws IllegalArgumentException {
		super(id, gid, new Vector2D(0.0, 0.0), position, mass);
	}

	@Override
	void advance(double dt) {}

}
