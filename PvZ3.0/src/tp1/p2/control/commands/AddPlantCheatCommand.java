package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;
import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class AddPlantCheatCommand extends Command {
	
	private Command hostage;

	@Override
	protected String getName() {
		return Messages.COMMAND_CHEAT_PLANT_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CHEAT_PLANT_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_CHEAT_PLANT_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_CHEAT_PLANT_HELP;
	}	
	@Override
	public Command create(String[] parameters) throws CommandParseException{
		this.hostage = new AddPlantCommand(false);
		hostage = hostage.create(parameters);
		if(hostage != null) {
			return this;
		}else {
			return null;
		}		
	}
		
	@Override
	public boolean execute(GameWorld game) throws CommandExecuteException{	
		
		return this.hostage.execute(game);			
		
	}

}
