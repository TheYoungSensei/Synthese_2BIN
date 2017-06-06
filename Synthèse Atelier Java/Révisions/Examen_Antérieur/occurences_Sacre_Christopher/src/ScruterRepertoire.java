

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class ScruterRepertoire implements Callable<Integer> {

  private Path file;
  private String motClef;
  private int compteur;

  public ScruterRepertoire(Path file, String motClef) {
    this.file = file;
    this.motClef = motClef;
  }

  public Integer call() {
    /*
     * compteur des occurences du mot
     */
    compteur = 0;

    /*
     * affiche le nom du thread courant suivi du mot Début
     */
    System.out.println(Thread.currentThread().getName() + " Debut");

    DirectoryStream<Path> fichiers;
    try {
      fichiers = Files.newDirectoryStream(file);
      ArrayList<Future<Integer>> resultats = new ArrayList<Future<Integer>>();

      Thread thread = null;
      for (Path fichier : fichiers) {
        /*
         * pour chaque Path , s'il correspond à un répertoire, il faut lancer l'exécution d'une
         * tâche (dans son propre Thread) afin de faire le travail de scruter le répertoire et
         * ajouter la tache à resultats. Sinon faire appel à rechercheDans et incrémenter le
         * compteur si nécessaire.
         */
        if (Files.isDirectory(fichier)) {
          System.out.println(Thread.currentThread().getName() + " lance une tache pour " + file);
          thread = new Thread(new MonThread(resultats, fichier, motClef));
          thread.start();
        } else {
          if (rechercheDans(fichier))
            compteur++;
        }

      }

      /*
       * Gestion des résultats des différentes tâches: Affiche nom du thread puis attendre résultat
       * Récupère le résultat et l'accumule au compteur Affiche le nom du thread obtient la valeur
       * du compteur
       */

      System.out.println(Thread.currentThread().getName() + " Fin");
      while (!Thread.interrupted()) {
        try {
          System.out.println(Thread.currentThread().getName() + " attendre resultats");
          synchronized (resultats) {
            for (Future<Integer> future : resultats) {
              compteur += future.get();
            }
          }
          System.out.println(Thread.currentThread().getName() + " obtient : " + compteur);
          break;
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }

    } catch (IOException e) {

    }



    return compteur;
  }

  public boolean rechercheDans(Path fichier) {
    try {
      Scanner in = new Scanner(new FileInputStream(fichier.toString()));
      boolean trouve = false;
      while (!trouve && in.hasNextLine()) {
        String line = in.nextLine();
        if (line.toLowerCase().contains(motClef.toLowerCase()))
          trouve = true;
      }
      in.close();
      return trouve;
    } catch (IOException e) {
      return false;
    }
  }
}
