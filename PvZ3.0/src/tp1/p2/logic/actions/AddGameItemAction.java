package tp1.p2.logic.actions;


import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.GameObject;


public class AddGameItemAction implements GameAction {	
	
	private GameObject obj;
	
	public AddGameItemAction(GameObject obj) {
		this.obj = obj;
	}
	@Override
	public void execute(GameWorld game) {
		obj.update();
		game.addItem(this.obj);		
	}

}
