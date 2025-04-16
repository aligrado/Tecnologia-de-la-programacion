package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.actions.ExplosionAction;
import tp1.p2.view.Messages;

public class ExplosiveZombie extends Zombie{
	public static final int EXPLOSIVE_DAMAGE = 3;
	
	public ExplosiveZombie() {}
	
	public ExplosiveZombie(GameWorld game, int col, int row) {
		super(game, col, row);
		this.cycles = -1;
	}	
	@Override
	protected String getName() {
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
	}
	@Override
	protected String getSymbol() {
		return Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
	}
	protected ExplosiveZombie copyAndFill(GameWorld game, int col, int row) {
		return new ExplosiveZombie(game, col, row);
	}
	public void onExit() { //Si el zombie explosivo muere: se hace el update de los zombies vivos restantes (como en todos los zombies) y hace la explosion
		this.game.addPoints();
		this.game.updateAliveZombies();
		this.game.pushAction(new ExplosionAction (this.col, this.row, EXPLOSIVE_DAMAGE)); //Explosion
	}
	@Override
	public String getDescription() {
		return Messages.zombieDescription("[E]xplosive [z]ombie", FREQUENCY, ExplosiveZombie.EXPLOSIVE_DAMAGE, ExplosiveZombie.ENDURANCE);
	}
	@Override
	public void update() {
		GameItem item;
		this.cycles++;
		if(this.cycles == FREQUENCY) { //Si los ciclos del zombie son iguales a su frecuencia mueve el zombie y resetea los ciclos
			this.move();				
			this.cycles = 0; 
		}
		item = this.game.getGameItemInPosition(this.col - 1, this.row); 
		if(item != null) { //Si hay un objeto a su izquierda lo intenta atacar
			item.receiveZombieAttack(DAMAGE);
		}	
	}
}