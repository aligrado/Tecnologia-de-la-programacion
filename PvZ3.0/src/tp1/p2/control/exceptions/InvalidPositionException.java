package tp1.p2.control.exceptions;

public class InvalidPositionException extends GameException{
	public InvalidPositionException() { 
		super();
	}
	public InvalidPositionException(String message){ 
		super(message); 
	}
	public InvalidPositionException(String message, Throwable cause){
		super(message, cause);
	}
	public InvalidPositionException(Throwable cause){ 
		super(cause); 
	}
	public InvalidPositionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
