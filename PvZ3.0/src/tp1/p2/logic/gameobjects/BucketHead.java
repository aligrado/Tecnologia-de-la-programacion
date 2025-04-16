package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class BucketHead extends Zombie{
	public static final int BUCKET_HEAD_ENDURANCE = 8;
	public static final int BUCKET_HEAD_FREQUENCY = 4;
	
	public BucketHead() {}
	
	public BucketHead(GameWorld game, int col, int row) {
		super(game, col, row);
		this.health_points = BUCKET_HEAD_ENDURANCE;
	}	
	protected String getName() {
		return Messages.BUCKET_HEAD_ZOMBIE_NAME;
	}
	
	protected BucketHead copyAndFill(GameWorld game, int col, int row) {
		return new BucketHead(game, col, row);
	}
	@Override
	protected String getSymbol() {
		return Messages.BUCKET_HEAD_ZOMBIE_SYMBOL;
	}

	@Override
	public String getDescription() {
		return Messages.zombieDescription("[B]uckethead [z]ombie", BucketHead.BUCKET_HEAD_FREQUENCY, BucketHead.DAMAGE, BucketHead.BUCKET_HEAD_ENDURANCE);
	}
	@Override
	public void update() {
		GameItem item;
		this.cycles++;
		if(this.cycles == BUCKET_HEAD_FREQUENCY) { //Si los ciclos del zombie son iguales a su frecuencia mueve el zombie y resetea los ciclos
			this.move();				
			this.cycles = 0; 
		}
		item = this.game.getGameItemInPosition(this.col - 1, this.row); 
		if(item != null) { //Si hay un objeto a su izquierda lo intenta atacar
			item.receiveZombieAttack(DAMAGE);
		}	
	}
	
}
