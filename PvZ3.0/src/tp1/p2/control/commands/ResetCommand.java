package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.control.Level;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;

public class ResetCommand extends Command {

	private Level level;

	private long seed;

	public ResetCommand() {
	}

	public ResetCommand(Level level, long seed) {
		this.level = level;
		this.seed = seed;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_RESET_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_RESET_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_RESET_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_RESET_HELP;
	}

	@Override
	public boolean execute(GameWorld game)throws CommandExecuteException{
		try {
			if(this.level != null) {
				game.reset(this.level, this.seed);	
				System.out.println(game.getResetInfo());
			}else {
				game.reset();	
				System.out.println(game.getResetInfo());
			}
		}catch(GameException ge) {
			throw new CommandExecuteException(ge);
		}
		
		return true;
	}

	@Override
	public Command create(String[] parameters)throws CommandParseException {
		if(parameters.length == 1) {
			return this;
		}else if(parameters.length == 3) {
			if(Level.valueOfIgnoreCase(parameters[1]) != null) {				
				try {
					level = Level.valueOfIgnoreCase(parameters[1]);
					seed = Long.parseLong(parameters[2]);
					return this;
				}catch(NumberFormatException e) {
					throw new CommandParseException(Messages.SEED_NOT_A_NUMBER_ERROR.formatted(parameters[2]), e);
				}
			}else {
				throw new CommandParseException(Messages.INVALID_COMMAND);			
			}
		}else if(parameters.length == 2){
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);		
		}else {
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
	}

}
