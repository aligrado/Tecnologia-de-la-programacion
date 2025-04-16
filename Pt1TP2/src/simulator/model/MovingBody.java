package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	
	//private Vector2D aceleration;
	//Aceleration como atributo o variable

	public MovingBody(String id, String gid, Vector2D position, Vector2D  velocity, double mass)
			throws IllegalArgumentException {
		
		super(id, gid, velocity, position, mass);
	}

	@Override
	void advance(double dt) {
		Vector2D aceleration;
		
		if(mass == 0) {
			aceleration = new Vector2D();
		}else {
			aceleration = force.scale(1/mass);
		}
		//Cambiar posicion y velocidad
		this.position=position.plus(velocity.scale(dt).plus(aceleration.scale(dt * dt * 0.5)));
//		this.position=position.plus(aceleration.scale(dt * dt * 0.5));
		this.velocity=aceleration.scale(dt).plus(velocity);
	}

}
