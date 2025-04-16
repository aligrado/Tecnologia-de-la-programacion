package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sunflower extends Plant{
	public static final int COST = 20;
	public static final int FREQUENCY = 3;
	public static final int DAMAGE = 0;
	public static final int ENDURANCE = 1;
	
	public Sunflower() {}
	public Sunflower(GameWorld game, int col, int row) {
		super(game, col, row);	
		this.health_points = ENDURANCE;
		this.cycles = -1;
	}
	protected String getSymbol() {
		return Messages.SUNFLOWER_SYMBOL;
	}

	public String getDescription() {
		return Messages.plantDescription(Messages.SUNFLOWER_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}
	@Override
	public void update() {			
		if(this.cycles == Sunflower.FREQUENCY) { //Si los ciclos del sunflower son iguales a la frecuencia genera soles y resetea los ciclos
			this.game.addSun();
			this.cycles = 1;
		}else { //En otro caso solo a�ade un ciclo
			this.cycles++;
		}		
	}

	@Override
	public void onEnter() {
	}
	@Override
	public void onExit() {		
	}
	protected String getName() {
		return Messages.SUNFLOWER_NAME;
	}
	public int getCost() {
		return COST;
	}
	protected Sunflower copyAndFill(GameWorld game, int col, int row) {
		return new Sunflower(game, col, row);
	}
	@Override
	public boolean fillPosition() {
		return true;
	}
	@Override
	public boolean catchObject() {
		return false;
	}
}
