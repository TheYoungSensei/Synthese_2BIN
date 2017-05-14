package exceptions;

public class UtilisateurInexistantException extends RuntimeException {

  public UtilisateurInexistantException() {
    super();
  }
  
  public UtilisateurInexistantException(String message) {
    super(message);
  }
}
