package fichier;

import java.util.concurrent.BlockingQueue;

public class ThreadPrincipal implements Runnable {

  private BlockingQueue<Message> queue;
  private Fichier fichier;

  public ThreadPrincipal(Fichier fichier, BlockingQueue<Message> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Message message = queue.take();
        fichier.set(message.getPosition(), (byte) Integer.parseInt(message.getMessage()));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
