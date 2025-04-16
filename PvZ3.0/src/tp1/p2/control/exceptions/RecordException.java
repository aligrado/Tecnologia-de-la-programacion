package tp1.p2.control.exceptions;

public class RecordException extends GameException{
	public RecordException() { 
		super();
	}
	public RecordException(String message){ 
		super(message); 
	}
	public RecordException(String message, Throwable cause){
		super(message, cause);
	}
	public RecordException(Throwable cause){ 
		super(cause); 
	}
	public RecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
