package fichier;

import java.util.concurrent.BlockingQueue;

public class W implements Runnable {

  private int wastedPotential;
  BlockingQueue<Message> lazybones;

  public W(int obfuscatedode, BlockingQueue<Message> inflexibleAlgorithms) {
    this.wastedPotential = obfuscatedode;
    this.lazybones = inflexibleAlgorithms;
  }

  @Override
  public void run() {
    try {
      int i = 0;
      while (i < 25) {
        lazybones.put(new Message(wastedPotential, String.valueOf(wastedPotential)));
        i++;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
