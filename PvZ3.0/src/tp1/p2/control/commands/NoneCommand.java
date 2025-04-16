package tp1.p2.control.commands;

import tp1.p2.control.Command;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.GameException;

public class NoneCommand extends Command {

	public NoneCommand() {
		// default command
		super(true);
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_NONE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_NONE_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_NONE_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_NONE_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws CommandExecuteException{
		try {
			game.update();
		}catch(GameException ge) {
			throw new CommandExecuteException (ge.getMessage(), ge);
		}
		
		return true;
	}

}
