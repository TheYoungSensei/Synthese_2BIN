package fichier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {

  private static final int POWER = 100;
  private static final int CONTROL = 1;

  public static void main(String[] args) {
    Fichier fichier = new Fichier();
    BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    Thread threadPrincipal = new Thread(new ThreadPrincipal(fichier, queue));
    threadPrincipal.start();
    fillFile(fichier, queue);
    readFile(fichier);
  }

  private static void fillFile(Fichier fichier, BlockingQueue<Message> queue) {
    List<Thread> list = new ArrayList<>();
    for (int i = 0; i < POWER; i++) {
      Thread thread = new Thread(new W(i, queue));
      thread.start();
    }
    for (Thread thread : list) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private static void readFile(Fichier fichier) {
    int i = 0;
    while (true) {
      System.out.println(fichier.get(i));
      i++;
    }
  }

}
