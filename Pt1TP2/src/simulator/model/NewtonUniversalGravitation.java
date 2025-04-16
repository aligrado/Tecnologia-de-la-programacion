package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private double G;
	
	public NewtonUniversalGravitation(double G) throws IllegalArgumentException{
		if(G<=0) {throw new IllegalArgumentException();}
		this.G=G;
	}
	
	@Override
	public void apply(List<Body> bs) {

		for(Body a: bs) {
			Vector2D sum=new Vector2D();

			for(Body b: bs) {
				
				if(!a.getPosition().equals(b.getPosition())) {
					//Calculo el vector director de la ferza
					Vector2D d = b.getPosition().minus(a.getPosition());
					//Calculo la magnitud de la fuerza
					double magnitude_aux=(G*a.getMass()*b.getMass())/(Math.pow(d.magnitude(), 2));
					sum=sum.plus(d.direction().scale(magnitude_aux));	
				}
			}
			//Sumo el total de las fuerzas restantes al objeto en iteracion
			a.addForce(sum);
		}
	}
	
	public String toString() {
		return "Newtons Universal Gravitation with G=" +G;
	}
	

}