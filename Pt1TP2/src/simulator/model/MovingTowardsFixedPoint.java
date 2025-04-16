package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	
	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g)  throws IllegalArgumentException{
		if(c == null || g <= 0) {
			throw new IllegalArgumentException();
		}
		this.c = c;
		this.g = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(Body b: bs) {
			Vector2D d = c.minus(b.getPosition());
			Vector2D f = d.direction().scale(b.getMass()*g);
			b.addForce(f);
		}
	}
	
	public String toString() {
		return "Moving towards " + c + " with constant aceleration " + g;
	}
}