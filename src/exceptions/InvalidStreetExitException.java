package exceptions;

public class InvalidStreetExitException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidStreetExitException() {}
	
	public InvalidStreetExitException(String message){
		super(message);
	}
	
}
