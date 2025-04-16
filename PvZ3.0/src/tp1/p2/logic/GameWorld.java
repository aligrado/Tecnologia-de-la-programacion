package tp1.p2.logic;

import tp1.p2.control.Level;
import tp1.p2.logic.actions.GameAction;//ExplosionAction sólo puede interactuar con el game a través de GameWorld
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;

public interface GameWorld {

	public static final int NUM_ROWS = 4;

	public static final int NUM_COLS = 8;
	

	void addSun();

	void tryToCatchObject(int col, int row)throws GameException;

	boolean addItem(GameObject gameObject);

	/**
	 * Resets the game.
	 */
	void reset() throws GameException;

	/**
	 * Resets the game with the provided level and seed.
	 * 
	 * @param level {@link Level} Used to initialize the game.
	 * @param seed Random seed Used to initialize the game.
	 */
	void reset(Level level, long seed) throws GameException;

	/**
	 * Executes the game actions and update the game objects in the board.
	 * 
	 */
	void update() throws GameException;
	public void playerQuits() throws GameException;//////
	public boolean isPositionEmpty(int col, int row);
	public void enoughSunCoins(int sunCoinsCost) throws GameException;
	public void addSunCoins(int sunCoins);
	public void paySunCoins(int price);
	public void removeDead();
	public void updateRemainingZombies();
	public void updateAliveZombies();
	public GameItem getGameItemInPosition(int col, int row);
	public boolean isFullyOcuppied(int col, int row);
	public void pushAction(GameAction gameAction);
	public void checkValidPlantPosition(int col, int row) throws InvalidPositionException;
	public void checkValidZombiePosition(int col, int row) throws InvalidPositionException;
	public void addPoints();
	public void insideAreaOfEffect(int col, int row);
	public String getSavedRecord();
	public Record getRecord();
	public String getResetInfo();
	
}
