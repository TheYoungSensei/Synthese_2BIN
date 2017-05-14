package exceptions;

public class EnchereInexistanteException extends RuntimeException {
  
  public EnchereInexistanteException() {
    super();
  }
  
  public EnchereInexistanteException(String message) {
    super(message);
  }
}
