package tp1.p2.logic;

import java.util.ArrayList;
import java.util.List;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.NotCatchablePositionException;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.view.Messages;

public class GameObjectContainer {

	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
	}

	public String positionToString(int col, int row) {
		StringBuilder buffer = new StringBuilder();
		boolean sunPainted = false;
		boolean sunAboutToPaint = false;

		for (GameObject g : gameObjects) {
			if(g.isAlive() && g.getCol() == col && g.getRow() == row) {
				String objectText = g.toString();
				sunAboutToPaint = objectText.indexOf(Messages.SUN_SYMBOL) >= 0;
				if (sunAboutToPaint) {
					if (!sunPainted) {
						buffer.append(objectText);
						sunPainted = true;
					}
				} else {
					buffer.append(objectText);
				}
			}
		}

		return buffer.toString();
	}

	public boolean removeDead() { //Lista donde solo s emeten los objetos vivos
		List<GameObject> newList = new ArrayList<>();
		for(GameObject obj : gameObjects) {
			if(obj.isAlive()) {
				newList.add(obj);				
			}else { //Si no esta vivo el objeto se hace el onExit de dicho objeto
				obj.onExit();
			}
		}
		this.gameObjects = newList;	
		return false;
	}

	public void addObject(GameObject gameObject) { //A�ade el objeto recibido por paramtro a la lista y hace el onEnter de ese objeto
		this.gameObjects.add(gameObject);	
		gameObject.onEnter();
	}

	public boolean isPositionEmpty(int col, int row) {//True si la posicion esta vacia
		for(GameObject object: gameObjects) {
			if(object.isInPosition(col, row)&& object.fillPosition()) {
				return false;
			}
		}
		return true;
	}

	public GameItem getGameItemInPosition(int col, int row) { //Devuelve el objeto que esta en la posicion recibida por parametro y si esta vaciadevuelve null
		for(GameObject obj: gameObjects) {
			if(obj.isInPosition(col, row)&& obj.fillPosition()) {
				return obj;
			}
		}
		return null;
	}
	public void update() { //Update de toda la lista (solo si el objeto esta vivo)	
		for(int i = 0; i < gameObjects.size(); i++) {
			GameObject g = gameObjects.get(i);
			if(g.isAlive()) {
				g.update();
			}
		}
	}

	public boolean isFullyOccupied(int col, int row) {//////////
		int i=0;
		boolean fullyOcuppied = false;

		while (i<gameObjects.size() && !fullyOcuppied) {
			GameObject g = gameObjects.get(i);
			if (g.isAlive() && g.isInPosition(col, row)) {
				fullyOcuppied = g.fillPosition();
			}
			i++;
		}

		return fullyOcuppied;
	}

	public boolean doesPlayerLose() {//True si el jugador ha perdido (ha ganado un zombie)
		int i = 0;
		while(i < this.gameObjects.size()) {
			if(this.gameObjects.get(i).getCol() == -1) {
				return true;
			}
			i++;
		}
		return false;
	}
	public boolean tryToCatchObject(int col, int row) throws GameException{ //Intenta coger el objeto de la posicion recibida por parametro (solo podra si hay un sol en la posicion recibida por parametro)
        boolean picked = false;
        for(GameObject object: gameObjects) {
            if(object.isInPosition(col, row) && object.isAlive() && !object.fillPosition()) {
                picked = object.catchObject();
                return picked;
            }
        }
        throw new NotCatchablePositionException(Messages.NO_CATCHABLE_IN_POSITION.formatted(col, row));
    }

}
