package tp1.p2.control.exceptions;

public class GameException extends Exception{
	public GameException() { 
		super(); 
	}
	public GameException(String message){ 
		super(message); 
	}
	public GameException(String message, Throwable cause){
		super(message, cause);
	}
	public GameException(Throwable cause){ 
		super(cause); 
	}
	public GameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
