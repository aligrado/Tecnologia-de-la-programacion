package tp1.p2.logic;

import static tp1.p2.view.Messages.error;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.NotEnoughCoinsException;
import tp1.p2.control.Level;
import tp1.p2.logic.actions.AddGameItemAction;
import tp1.p2.logic.actions.GameAction;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.logic.gameobjects.Sun;
import tp1.p2.view.Messages;

public class Game implements GameStatus, GameWorld {

	private long seed;

	private Level level;
	
	private int cycle;

	private GameObjectContainer container;
	private Record record;
	private Deque<GameAction> actions;
	private Random rand;
	private int sun_coins;
	private boolean playerQuits = false; 
	private ZombiesManager zombiesManager;
	private SunsManager sunsManager;
	private int score;

	public Game(long seed, Level level) throws GameException{
		this.seed = seed;
		this.level = level;
		reset();
	}

	/**
	 * Resets the game.
	 */
	@Override
	public void reset() throws GameException{
		reset(this.level, this.seed);
	}

	/**
	 * Resets the game with the provided level and seed.
	 * 
	 * @param level {@link Level} Used to initialize the game.
	 * @param seed Random seed Used to initialize the game.
	 */
	@Override
	public void reset(Level level, long seed) throws GameException{
		this.level = level;
		this.seed = seed;
		this.cycle = 0;
		this.score = 0;
		this.actions = new ArrayDeque<>();
		this.rand = new Random(seed);
		this.zombiesManager = new ZombiesManager(this, level, rand);
		this.sunsManager = new SunsManager(this, this.rand);
		this.sun_coins = 50;
		this.playerQuits = false;
		this.container = new GameObjectContainer();
		this.record = new Record(this.level.toString());	
		this.record.loadRecord();			
	}
	/**
	 * Executes the game actions and update the game objects in the board.
	 * 
	 */
	@Override
	public void update() throws GameException{

		// 1. Execute pending actions
		executePendingActions();

		// 2. Execute game Actions
		
		this.zombiesManager.update();
		this.sunsManager.update();	

		// 3. Game object updates
		
		this.container.update();
				
		this.removeDead();

		// 4. & 5. Remove dead and execute pending actions
		boolean deadRemoved = true;
		while (deadRemoved || areTherePendingActions()) {
			// 4. Remove dead
			deadRemoved = this.container.removeDead();

			// 5. execute pending actions
			executePendingActions();
		}

		this.cycle++;

		// 6. Notify commands that a new cycle started
		Command.newCycle();
		// 7. Update record
		this.record.update(this.score, isFinished());

	}

	private void executePendingActions() {
		while (!this.actions.isEmpty()) {
			GameAction action = this.actions.removeLast();
			action.execute(this);
		}
	}

	private boolean areTherePendingActions() {
		return this.actions.size() > 0;
	}

	@Override
	public void addSun() { //A�ade un sol
		int col = this.rand.nextInt(GameWorld.NUM_COLS);	
		int row = this.rand.nextInt(GameWorld.NUM_ROWS);			
		Sun sun = new Sun(this, col, row);	
		pushAction(new AddGameItemAction(sun));	
	}

	@Override
	public void tryToCatchObject(int col, int row) throws GameException{
		this.container.tryToCatchObject(col, row);
	}

	@Override
	public boolean addItem(GameObject gameObject) { //A�adeel objeto recibido por parametroa la lista
		this.container.addObject(gameObject); //En este metodo s ehace elonEnter
		return false;
	}

	@Override
	public int getCycle() {
		return this.cycle;
	}

	@Override
	public int getSuncoins() {
		return this.sun_coins;
	}

	@Override
	public int getRemainingZombies() {
		return this.zombiesManager.getRemainingZombies();
	}

	@Override
	public String positionToString(int col, int row) {
		return this.container.positionToString(col, row);
	}	

	@Override
	public void playerQuits() throws GameException{				
		this.playerQuits = true;
		this.record.saveRecord();
	}

	@Override
	public boolean isPositionEmpty(int col, int row) { //True si la posicion esta vacia
		return this.container.isPositionEmpty(col, row);
	}
	public boolean isFullyOcuppied(int col, int row) {
		return this.container.isFullyOccupied(col, row);
	}
	@Override
	public void enoughSunCoins(int sunCoinsCost)throws GameException { //Comrpueba si eljugador tiene suficientes suncoins y si es asi las gasta
		if(this.sun_coins >= sunCoinsCost) {
			this.paySunCoins(sunCoinsCost);
		}else {
			throw new NotEnoughCoinsException(Messages.NOT_ENOUGH_COINS);
		}
	}
	public void pushAction(GameAction gameAction) { //A�ade una accion a la lista de acciones
	    this.actions.addLast(gameAction);
	}

	@Override
	public void addSunCoins(int sunCoins) {
		this.sun_coins += sunCoins;		
	}

	@Override
	public void paySunCoins(int price) { //Se le elimina el numero de suncoins recibidas por parametro
		this.sun_coins-= price;		
	}

	@Override
	public void removeDead() { //Elimina todos los objetos muertos de la lista
		this.container.removeDead();
	}

	@Override
	public void updateRemainingZombies() {
		this.zombiesManager.zombiesManagerUpdateRemainingAliveZombies();	
	}

	@Override
	public void updateAliveZombies() {
		this.zombiesManager.updateAliveZombies();
	}

	@Override
	public GameItem getGameItemInPosition(int col, int row) { //Devuelve el objeto en la posicion (null si la posicion esta vacia)
		return this.container.getGameItemInPosition(col, row);
	}

	@Override
	public boolean doZombiesWin() {		
		return this.container.doesPlayerLose();
	}
	@Override
	public boolean doesPlayerWin() { //Devuelve true si el jugador ha ganado (no quedan zombies por spawnear y estan todos muertos)		
		return this.zombiesManager.getRemainingZombies() == 0 && this.zombiesManager.getZombiesAlived() == 0;
	}
	public boolean isFinished(){ //Comprueba si el juego ha acabado	
		boolean finished = this.doesPlayerWin()
				|| this.doZombiesWin();				
		return finished;
	}
	public boolean isPlayerQuits(){	
		
		return this.playerQuits;
	}
	public boolean execute(Command command) throws GameException{
		boolean result = command.execute(this);		
		return result;		
	}

	@Override
	public int getGeneratedSuns() {
		return this.sunsManager.getGeneratedSuns();
	}

	@Override
	public int getCaughtSuns() {
		return this.sunsManager.getCatchedSuns();
	}

	@Override
	public void checkValidPlantPosition(int col, int row) throws InvalidPositionException {
		if(!(col >= 0 && col < Game.NUM_COLS && row >= 0 && row < NUM_ROWS)) {
			throw new InvalidPositionException(Messages.INVALID_POSITION.formatted(col, row));
		}
	}

	@Override
	public void checkValidZombiePosition(int col, int row) throws InvalidPositionException {
		if(!(col >= 0 && col <= Game.NUM_COLS && row >= 0 && row < NUM_ROWS)) {
			throw new InvalidPositionException(Messages.INVALID_POSITION.formatted(col, row));
		}
	}
	public int getScore() {
		return this.score;
	}
	public void addPoints() {
		this.score += 10;
	}
	public void insideAreaOfEffect(int col, int row) {
		if(!isFullyOcuppied(col,row)) {//bicho muerto == false;
			addPoints();
		}
	}	
	public boolean thereIsANewRecord() {
		return this.record.thereIsANewRecord();
	}
	public int getNewCurrentRecord() {
		return this.score;
	}
	public String getSavedRecord() {
		return Messages.CURRENT_RECORD.formatted(this.record.getLevelName(), (this.record.getSavedRecord()));
	}
	public Record getRecord() {
		return this.record;
	}
	public String getResetInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(Messages.CONFIGURED_LEVEL, level.toString())+"\n");
		sb.append(String.format(Messages.CONFIGURED_SEED, seed));
		return sb.toString();
	}
}
