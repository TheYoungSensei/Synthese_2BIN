package exceptions;

public class ObjetInexistantException extends RuntimeException {

  public ObjetInexistantException() {
    super();
  }

  public ObjetInexistantException(String message) {
    super(message);
  }
}
