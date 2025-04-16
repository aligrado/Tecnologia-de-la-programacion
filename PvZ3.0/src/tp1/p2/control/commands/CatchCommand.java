package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.NotCatchablePositionException;

public class CatchCommand extends Command {

	private static boolean caughtSunThisCycle = false;

	private int col;

	private int row;

	public CatchCommand() {
		caughtSunThisCycle = false;
	}
	
	@Override
	protected void newCycleStarted() {
		caughtSunThisCycle = false;
	}

	private CatchCommand(int col, int row) {
		this.col = col;
		this.row = row;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_CATCH_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CATCH_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_CATCH_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_CATCH_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws CommandExecuteException {
		try {
			if(!CatchCommand.caughtSunThisCycle) {
				game.tryToCatchObject(col, row);
				CatchCommand.caughtSunThisCycle = true;				
			}else {
				throw new NotCatchablePositionException(Messages.SUN_ALREADY_CAUGHT);
			}
		}catch(GameException ge) {
			throw new CommandExecuteException(ge.getMessage(), ge);
		}	
		return true;
	}

	@Override
	public Command create(String[] parameters) throws CommandParseException {
		if(parameters.length == 3) {
			try {
				col = Integer.parseInt(parameters[1]);
				row = Integer.parseInt(parameters[2]);
				if(col >= 0 && col < GameWorld.NUM_COLS && row >= 0 && row < GameWorld.NUM_ROWS) {					
					return this;
				}else {
					throw new CommandParseException(Messages.INVALID_POSITION.formatted(col, row));
				}
			}catch(NumberFormatException e) {
				throw new CommandParseException(Messages.INVALID_POSITION.formatted(parameters[1], parameters[2]), e);
			}		
		}else if(parameters.length < 3){
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);		
		}else {
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
	}

}
