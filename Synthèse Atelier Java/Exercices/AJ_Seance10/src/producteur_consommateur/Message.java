package producteur_consommateur;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.print.attribute.UnmodifiableSetException;


public class Message {
  private String msg;
  private LinkedBlockingQueue<String> response = new LinkedBlockingQueue<String>(1);
  AtomicBoolean setted = new AtomicBoolean(false);

  public Message(String msg) {
    this.msg = msg;
  }

  public void setResponse(String msgbis) {
    try {
      if (setted.getAndSet(true) == true)
        throw new UnmodifiableSetException();
      response.put(msgbis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public String getResponse() {
    try {
      return response.take();
    } catch (InterruptedException e) {
      return null;
    }
  }

  public String getMessage() {
    return this.msg;
  }
}
