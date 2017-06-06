
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OccurencesStream {
  static int compteur = 0;
  static String motClef = null;

  @SuppressWarnings("resource")
  public static void main(String[] args) {

    String repertoire = null;

    Scanner in = new Scanner(System.in);
    System.out.print("Donner repertoire de depart ");
    repertoire = in.nextLine();

    System.out.print("Mot a chercher a partir de ce repertoire : ");
    motClef = in.nextLine();

    /*
     * une seule instruction pour traiter tout le calcul des occurences en invoquant la méthode de
     * classe walk de la classe Files.
     */
    try {

      Predicate<Path> predicate = new Predicate<Path>() {

        @Override
        public boolean test(Path p) {
          try {
            return rechercheDans(p, motClef);
          } catch (IOException e) {
            return false;
          }
        }

      };

      compteur = (int) Files.walk(Paths.get(repertoire)).filter(p -> Files.isDirectory(p))
          .filter(predicate).count();

    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(compteur);
    System.out.println(Thread.currentThread().getName() + " Fin");
    in.close();
  }

  /*
   * cette méthode effectue la même chose que dans la version 1 mais doit être écrite en 2 lignes
   * grâces aux Streams et un try-with-resources
   */
  public static boolean rechercheDans(Path fichier, String motClef) throws IOException {
    try (Stream<Path> lines = Files.list(fichier)) {
      return lines.anyMatch(p -> p.toString().toLowerCase().contains(motClef.toLowerCase()));
    }
  }
}
