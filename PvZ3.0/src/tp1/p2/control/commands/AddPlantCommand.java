package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;

public class AddPlantCommand extends Command implements Cloneable {

	private int col;

	private int row;

	private String plantName;

	private boolean consumeCoins;

	public AddPlantCommand() {
		this(true);
	}

	public AddPlantCommand(boolean consumeCoins) {
		this.consumeCoins = consumeCoins;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_HELP;
	}


	@Override
	public boolean execute(GameWorld game) throws CommandExecuteException{
		Plant plant;
		try {
			game.checkValidPlantPosition(this.col, this.row);
			if(!game.isFullyOcuppied(col, row)){
				plant = PlantFactory.spawnPlant(plantName, game, col, row); //Crea la planta
				if(this.consumeCoins) {				
					game.enoughSunCoins(plant.getCost()); //Comprueba si tiene suficiente dinero, en caso de que no se lanzara una excepcion
					game.addItem(plant); //Si todo es correcto (tiene suficientes suncoins y la posicion es valida) la añade a la lista				
				}else {
					game.addItem(plant);  //Si todo es correcto (tiene suficientes suncoins y la posicion es valida) la añade a la lista
				}
				game.update();
			}else {
				throw new CommandExecuteException(Messages.INVALID_POSITION.formatted(col, row),new InvalidPositionException());
			}
			
		}catch(GameException ge) {
			throw new CommandExecuteException(ge.getMessage(), ge);
		}		
		return true;		
	}

	@Override
	public Command create(String[] parameters) throws CommandParseException{
		if(parameters.length == 4) {
			plantName = parameters[1];
			if(PlantFactory.isValidPlant(plantName)) {				
				try {
					col = Integer.parseInt(parameters[2]);
					row = Integer.parseInt(parameters[3]);					
					return this;					
				}catch(NumberFormatException e){
					throw new CommandParseException(Messages.INVALID_POSITION.formatted(parameters[2], parameters[3]), e);
				}				
			}else {
				throw new CommandParseException(Messages.INVALID_GAME_OBJECT);
			}
		}else if(parameters.length < 4){
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		}else {
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
	}
}


