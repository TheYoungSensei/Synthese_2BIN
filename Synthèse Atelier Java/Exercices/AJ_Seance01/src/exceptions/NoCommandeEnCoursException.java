package exceptions;

public class NoCommandeEnCoursException extends RuntimeException {

	private String msg;

	public NoCommandeEnCoursException() {
		super();
	}

	public NoCommandeEnCoursException(String msg) {
		super(msg);
	}
	
	
}
