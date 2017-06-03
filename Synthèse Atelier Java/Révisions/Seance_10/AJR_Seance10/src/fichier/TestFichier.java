package fichier;

public class TestFichier {

  public static void main(String[] args) {
    Fichier f = new Fichier();
    Thread[] threads = new Thread[100];
    for (byte i = 0; i <= 99; i++) {
      threads[i] = new MonThread(i, f);
      threads[i].start();
    }

    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        // Ignore
      }
    }

    for (int i = 0; i < 100; i++) {
      System.out.println(f.get(i));
    }
  }

  private static class MonThread extends Thread {
    private byte numero;
    private Fichier fichier;

    public MonThread(byte numero, Fichier fichier) {
      this.numero = numero;
      this.fichier = fichier;
    }

    @Override
    public void run() {
      for (int i = 0; i < 25; i++) {
        fichier.set(numero, numero);
      }
    }
  }
}


