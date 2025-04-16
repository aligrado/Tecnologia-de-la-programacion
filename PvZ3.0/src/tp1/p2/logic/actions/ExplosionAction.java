package tp1.p2.logic.actions;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

public class ExplosionAction implements GameAction {

	private int col;

	private int row;

	private int damage;

	public ExplosionAction(int col, int row, int damage) {
		this.col = col;
		this.row = row;
		this.damage = damage;
	}

	@Override
	public void execute(GameWorld game) {
		GameItem item;
		for(int i = row - 1; i <= row + 1; i++) { //Explosion
			for(int j = col - 1; j <= col + 1; j++) {
				if(!(i == row && j == col)) { //Si la fila y la columna son distintas (si son iguales es la misma posicion en la que esta el objeto que hace la explosion)
					item = game.getGameItemInPosition(j, i);
					if(item != null) {
						item.receiveZombieAttack(this.damage);
					}
				}
			}
		}
	}

}
