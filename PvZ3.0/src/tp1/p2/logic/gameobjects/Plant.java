package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;

public abstract class Plant extends GameObject{	
	
	Plant(){}
	
	Plant(GameWorld game, int col, int row) {
		super(game, col, row);
	}
	@Override
	public boolean receiveZombieAttack(int damage) { //Recibe el ataque de un zombie (se le resta el da�o del ataque a su vida y se indica que esa planta ha sido atacada)
		boolean attacked = false;
		if(this.isAlive()) {
			this.health_points -= damage;
			attacked = true;
		}		
		return attacked;
	}
	public boolean receivePlantAttack(int damage) {	//Devuelve false siempre ya que una planta no puede recibir da�o de otra		
		return false;
	}
	
	@Override
	public boolean isAlive() { //Devuelve true si la planta esta viva y false en caso contrario
		return this.health_points > 0;
	}
	protected abstract String getName();
	public abstract int getCost(); 
	protected abstract Plant copyAndFill(GameWorld game, int col, int row);
}
