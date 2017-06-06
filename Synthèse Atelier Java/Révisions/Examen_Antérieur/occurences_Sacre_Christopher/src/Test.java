

import java.nio.file.FileSystems;
import java.util.Scanner;

public class Test {
  public static void main(String[] args) {
    Integer retour = null;
    String motClef = null;
    String repertoire = null;

    Scanner in = new Scanner(System.in);
    System.out.print("Indiquez le répertoire de départ: ");
    repertoire = in.nextLine();

    System.out.print("Indiquez un mot à chercher à partir de ce répertoire : ");
    motClef = in.nextLine();

    ScruterRepertoire scRepertoire =
        new ScruterRepertoire(FileSystems.getDefault().getPath(repertoire), motClef);

    // lancer l'exécution d'une tache dans un nouveau Thread afin de faire le travail de scruter le
    // répertoire
    // et d'en récupérer le résultat
    Thread thread = new Thread(new ThreadResult(scRepertoire));
    thread.start();

    System.out.println("Le mot " + motClef + " se trouve dans " + retour + " fichiers.");

    in.close();

  }

  private static class ThreadResult implements Runnable {

    ScruterRepertoire rep;

    public ThreadResult(ScruterRepertoire rep) {
      this.rep = rep;
    }

    @Override
    public void run() {
      rep.call();
    }

  }
}
