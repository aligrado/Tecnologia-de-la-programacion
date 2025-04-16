package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class CherryBomb extends Plant{
	public static final int COST = 50;
	public static final int FREQUENCY = 2;
	public static final int DAMAGE = 10;
	public static final int ENDURANCE = 2;
	
	public CherryBomb() {}
	public CherryBomb(GameWorld game, int col, int row) {
		super(game, col, row);	
		this.health_points = ENDURANCE;
		this.cycles = -1;
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
		return Messages.CHERRY_BOMB_NAME;
	}

	@Override
	public int getCost() {
		return CherryBomb.COST;
	}

	@Override
	protected CherryBomb copyAndFill(GameWorld game, int col, int row) {
		return new CherryBomb(game, col, row);
	}

	@Override
	protected String getSymbol() {
		if(this.cycles == FREQUENCY - 1) {
			return Messages.CHERRY_BOMB_SYMBOL.toUpperCase();
		}else {
			return Messages.CHERRY_BOMB_SYMBOL;
		}		
	}

	@Override
	public String getDescription() {
		return Messages.plantDescription(Messages.CHERRY_BOMB_NAME_SHORTCUT, COST, DAMAGE, ENDURANCE);
	}

	@Override
	public void update() {	
		this.cycles++;	
		if(this.cycles == FREQUENCY) {	
			GameItem item;
			for(int i = row - 1; i <= row + 1; i++) { //Explosion en todas las casillas que estan alrededor de la planta
				for(int j = col - 1; j <= col + 1; j++) {
					if(!(i == row && j == col)) { //Si no se cumple que la fila y la columna sean iguales (si fueran iguales ser�a la casilla de la planta)
						item = game.getGameItemInPosition(j, i);
						if(item != null && item.receivePlantAttack(CherryBomb.DAMAGE)) {
							this.game.insideAreaOfEffect(j,i);
						}
					}
				}
			}
			this.health_points = 0;
		}
			
	}

	@Override
	public void onEnter() {}

	@Override
	public void onExit() {
		
	}

	

}
