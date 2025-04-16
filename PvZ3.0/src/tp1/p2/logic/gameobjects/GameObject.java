package tp1.p2.logic.gameobjects;

import static tp1.p2.view.Messages.status;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

/**
 * Base class for game non playable character in the game.
 *
 */
public abstract class GameObject implements GameItem {

	protected GameWorld game;

	protected int col;

	protected int row;

	protected int health_points;
	protected int cycles;


	GameObject() {
	}

	GameObject(GameWorld game, int col, int row) {
		this.game = game;
		this.col = col;
		this.row = row;
	}

	public boolean isInPosition(int col, int row) {//True si el objeto esta en la posicion recibida por parametro
		return this.col == col && this.row == row;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
	
	public abstract boolean isAlive();

	public String toString() {
		if (isAlive()) {
			return status(this.getSymbol(), this.health_points);		
			} else {
			return "";
		}
	}
	abstract protected String getName();
	abstract protected String getSymbol();

	abstract public String getDescription();
	abstract protected GameObject copyAndFill(GameWorld game, int col, int row);

	abstract public void update();
	
	abstract public void onEnter();
	
	abstract public void onExit();
}
