package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class WallNut extends Plant{
	public static final int COST = 50;
	public static final int FREQUENCY = 0;
	public static final int DAMAGE = 0;
	public static final int ENDURANCE = 10;
	
	public WallNut() {}
	public WallNut(GameWorld game, int col, int row) {
		super(game, col, row);	
		this.health_points = ENDURANCE;
		this.cycles = 1;
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
		return Messages.WALL_NUT_NAME;
	}

	@Override
	public int getCost() {
		return WallNut.COST;
	}

	@Override
	protected Plant copyAndFill(GameWorld game, int col, int row) {
		return new WallNut(game, col, row);
	}

	@Override
	protected String getSymbol() {
		return Messages.WALL_NUT_SYMBOL;
	}

	@Override
	public String getDescription() {
		return Messages.plantDescription(Messages.WALL_NUT_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}

	@Override
	public void update() {	
		
	}

	@Override
	public void onEnter() {}

	@Override
	public void onExit() {}	
	

}
