package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Peashooter extends Plant{
	
	public static final int COST = 50;
	public static final int FREQUENCY = 1;
	public static final int DAMAGE = 1;
	public static final int ENDURANCE = 3;
	
	public Peashooter() {}
	public Peashooter(GameWorld game, int col, int row) {
		super(game, col, row);
		this.health_points = ENDURANCE;
		this.cycles = 0;
	}
	
	protected String getSymbol() {
		return Messages.PEASHOOTER_SYMBOL;
	}

	public String getDescription() {
		return Messages.plantDescription(Messages.PEASHOOTER_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}

	@Override
	public void update() {
		GameItem item;
		int i = this.col + 1;
		boolean found = false;
		if(this.cycles == Peashooter.FREQUENCY) { //Actualiza los ciclos del peashooter (Si son iguales a la frecuencia los resetea y si no le suma uno)		
			this.cycles = 0;
		}else {
			this.cycles++;
		}	
		while(i < GameWorld.NUM_COLS && !found){ //Recorre la fila en la que se encuentra hasta que encuentra un zombie al que pegar
			item = this.game.getGameItemInPosition(i, this.row);
			if(item != null) { //Si hay un objeto
				found = item.receivePlantAttack(Peashooter.DAMAGE); //Le intenta pegar				
			}	
			i++;
		}
	}

	@Override
	public void onEnter() { //Al meter al peashooter a la lista se consumen las monedas y se hace su update		
	}

	@Override
	public void onExit() {		
	}
	protected String getName() {
		return Messages.PEASHOOTER_NAME;
	}
	public int getCost() {
		return COST;
	}
	
	protected Peashooter copyAndFill(GameWorld game, int col, int row) {
		return new Peashooter(game, col, row);
	}
	@Override
	public boolean fillPosition() {
		return true;
	}
	@Override
	public boolean catchObject() {
		return false;//false significa "ya cogido" sólo con sentido para suncoins
	}
	
}
