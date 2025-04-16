package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sporty extends Zombie{
	public static final int SPORTY_ENDURANCE = 2;
	public static final int SPORTY_FREQUENCY = 1;
	public Sporty() {}
	
	public Sporty(GameWorld game, int col, int row) {
		super(game, col, row);
		this.health_points = SPORTY_ENDURANCE;
		this.cycles = -1;
	}	
	protected String getName() {
		return Messages.SPORTY_ZOMBIE_NAME;
	}
	@Override
	protected String getSymbol() {
		return Messages.SPORTY_ZOMBIE_SYMBOL;
	}
	public String getDescription(){
		return Messages.zombieDescription("[S]porty [z]ombie", SPORTY_FREQUENCY, DAMAGE, SPORTY_ENDURANCE);
	}
	@Override
	public void update() {
		GameItem item;
		this.cycles++;
		if(this.cycles == SPORTY_FREQUENCY) { //Si los ciclos del zombie son iguales a su frecuencia mueve el zombie y resetea los ciclos
			this.move();				
			this.cycles = 0; 
		}
		item = this.game.getGameItemInPosition(this.col - 1, this.row); 
		if(item != null) { //Si hay un objeto a su izquierda lo intenta atacar
			item.receiveZombieAttack(DAMAGE);
		}	
	}
	protected Sporty copyAndFill(GameWorld game, int col, int row) {
		return new Sporty(game, col, row);
	}
}
