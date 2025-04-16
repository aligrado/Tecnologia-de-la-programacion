package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sun extends GameObject {	
	// Remember that a Sun is updated the very same cycle is added to the container
	public static final int SUN_COOLDOWN = 10+1;
	public static final int SUN_COINS = 10;

	public static int catchedSuns;
	public static int generatedSuns;
	
	public Sun() {}
	public Sun(GameWorld game, int col, int row) {
		super(game, col, row);	
		this.health_points = SUN_COOLDOWN;
		this.cycles = 0;
	}	

	@Override
	public boolean catchObject() {//Atrapa el sol: se le pone a 0 la vida, se le a�aden las suncoins al jugador y se le suma 1 al contador de soles cogidos
		this.health_points = 0;
		this.game.addSunCoins(SUN_COINS);		
		Sun.catchedSuns++;		
		return true;
	}

	@Override
	public boolean fillPosition() {
		return false;
	}

	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}

	@Override
	public boolean receivePlantAttack(int damage) {
		return false;
	}

	@Override
	public boolean isAlive() {		
		return this.health_points > 0;
	}

	@Override
	protected String getSymbol() {
		return Messages.SUN_SYMBOL;
	}

	@Override
	public String getDescription() {
		return Messages.SUN_DESCRIPTION;
	}

	@Override
	public void update() {
		this.cycles++;
		this.health_points--;		
	}

	@Override
	public void onEnter() {
		Sun.generatedSuns++;
	}

	@Override
	public void onExit() {			
	}
	@Override
	protected String getName() {
		return null;
	}
	@Override
	protected Sun copyAndFill(GameWorld game, int col, int row) {		
		return new Sun(game, col, row);
	}
}
