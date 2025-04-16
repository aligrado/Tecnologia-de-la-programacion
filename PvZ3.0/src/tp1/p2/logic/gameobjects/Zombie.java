package tp1.p2.logic.gameobjects;

import tp1.p2.view.Messages;
import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

public class Zombie extends GameObject{
	
	public static final int FREQUENCY = 3;
	public static final int DAMAGE = 1;
	public static final int ENDURANCE = 5;
	
	public Zombie() {}
	
	public Zombie(GameWorld game, int col, int row) {
		super(game, col, row);
		this.cycles = -1;
		this.health_points = ENDURANCE;
	}
	

	@Override
	public boolean isAlive() {
		return this.health_points > 0;
	}

	protected int getCost() {		
		return 0;
	}

	@Override
	protected String getSymbol() {
		return Messages.ZOMBIE_SYMBOL;
	}

	@Override
	public String getDescription() {
		return Messages.zombieDescription("[Z]ombie", FREQUENCY, DAMAGE, ENDURANCE);
	}

	@Override
	public void update() {
		GameItem item;
		this.cycles++;
		if(this.cycles == Zombie.FREQUENCY) { //Si los ciclos del zombie son iguales a su frecuencia mueve el zombie y resetea los ciclos
			this.move();				
			this.cycles = 0; 
		}
		item = this.game.getGameItemInPosition(this.col - 1, this.row); 
		if(item != null) { //Si hay un objeto a su izquierda lo intenta atacar
			item.receiveZombieAttack(Zombie.DAMAGE);
		}	
	}

	@Override
	public void onEnter() { //Cuando se a�ade un zombie a la lista se hace el update de los zombies totales que quedan por aparecer
		
	}

	@Override
	public void onExit() { //Cuando el zombie sale de la lista se hace elk update de los zombies vivos restantes
		this.game.addPoints();
		this.game.updateAliveZombies();
		
	}
	public void move() {
		if(this.game.isPositionEmpty(this.col - 1, this.row)) { //Si la posicion a la izquierda del zombie esta vacia lo mueve			
			this.col--;						
		}
	}

	@Override
	public boolean receivePlantAttack(int damage) { //Recibe el ataque de una planta devolviendo true si ha sido atacado y false en cc
		boolean attacked = false;
		if(this.isAlive()) { //Si el zombie esta vivo recibe el ataque (se le resta el da�o ejercido por la planta a la vida del zombie
			this.health_points -= damage;
			attacked = true;
		}		
		return attacked;
	}
	@Override
	public boolean receiveZombieAttack(int damage) { //Siempre devuelve false ya que un zombie no puede recibir un ataque de un zombie
		return false;
	}

	@Override
	public boolean fillPosition() {
		return true;
	}

	@Override
	public boolean catchObject() {
		return false;
	}

	@Override
	protected String getName() {
		return Messages.ZOMBIE_NAME;
	}

	protected Zombie copyAndFill(GameWorld game, int col, int row) {
		return new Zombie(game, col, row);
	}
	
	
}
