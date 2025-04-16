package tp1.p2.logic;

import java.util.Random;

import tp1.p2.logic.gameobjects.Sun;

import tp1.p2.logic.actions.AddGameItemAction;

public class SunsManager {

	private static final int COOLDOWN_RANDOM_SUN = 5;

	private GameWorld game;

	private Random rand;

	private int cooldown;

	public SunsManager(GameWorld game, Random rand) {
		this.game = game;
		this.rand = rand;
		this.cooldown = COOLDOWN_RANDOM_SUN;
		Sun.catchedSuns = 0;
		Sun.generatedSuns = 0;
	}

	public int getCatchedSuns() {
		return Sun.catchedSuns;
	}

	public int getGeneratedSuns() {
		return Sun.generatedSuns;
	}

	public void update() { //"Cuenta atr�s" para a�adir soles, si se a�ade un sol se resetea al cooldown
		if (cooldown == 0) {
			addSun();
			cooldown = COOLDOWN_RANDOM_SUN;
		} else {
			cooldown--;
		}
	}

	private int getRandomInt(int bound) {
		return this.rand.nextInt(bound);
	}

	public void addSun() { //Genera un nuevo sol en una fila y columna random
		int col = getRandomInt(GameWorld.NUM_COLS);
		int row = getRandomInt(GameWorld.NUM_ROWS);
		game.pushAction(new AddGameItemAction(new Sun(this.game, col, row)));
	}
}
