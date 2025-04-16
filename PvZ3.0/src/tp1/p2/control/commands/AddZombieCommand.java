package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.logic.gameobjects.ZombieFactory;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;

public class AddZombieCommand extends Command {

	private int zombieIdx;

	private int col;

	private int row;

	public AddZombieCommand() {

	}

	private AddZombieCommand(int zombieIdx, int col, int row) {
		this.zombieIdx = zombieIdx;
		this.col = col;
		this.row = row;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_ZOMBIE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_ZOMBIE_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_ZOMBIE_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_ZOMBIE_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws CommandExecuteException{
		Zombie zombie;
		try {
			game.checkValidZombiePosition(this.col, this.row);
			zombie = ZombieFactory.spawnZombie(this.zombieIdx, game, this.col, this.row); //Crea el zombie
			game.addItem(zombie); 
			game.update();
		}catch(InvalidPositionException ipe){
			throw new CommandExecuteException(ipe.getMessage(), ipe);
		}catch(GameException ge) {
			
		}			
		return true;			
	}

	@Override
	public Command create(String[] parameters) throws CommandParseException{
		if(parameters.length == 4) {
			try {
				zombieIdx = Integer.parseInt(parameters[1]);
				if(ZombieFactory.isValidZombie(zombieIdx)) {					
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
			}catch(NumberFormatException e) {
				throw new CommandParseException(Messages.INVALID_GAME_OBJECT);
			}			
		}else if(parameters.length < 4){
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		}else {
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
	}

}
