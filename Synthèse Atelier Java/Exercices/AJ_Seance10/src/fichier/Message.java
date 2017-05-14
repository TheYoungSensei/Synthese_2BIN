package fichier;


public class Message {
  private int position;
  private String message;

  public Message(int position, String message) {
    this.position = position;
    this.message = message;
  }

  public int getPosition() {
    return this.position;
  }

  public String getMessage() {
    return this.message;
  }

}
