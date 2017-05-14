package exercices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Exercice5 {

  public static void main(String[] args) {
    try (
        Stream<String> lignes = Files.lines(Paths.get("etudiants.csv"), Charset.defaultCharset())) {
      // System.out.println(lignes.findFirst().toString());
      // exo2(lignes);
      // List<Etudiant> etudiants = mesEtudiants2(lignes);
      // List<Etudiant> etudiants = mesEtudiants1(lignes);
      List<Etudiant> etudiants = mesEtudiants1(lignes);
      etudiants.stream().map(e -> e.getNom())
          .filter(s -> s.length() < 6 && (s.contains("I") || s.contains("Y")))
          .forEach(System.out::println);
       System.out.println(etudiants.stream().map(e -> e.getPrenom()).filter(s -> isAeWord(s)).findFirst().orElse("None"));
       System.out.println(etudiants.stream().allMatch(e -> e.getEmail() != null));
       System.out.println(etudiants.stream().filter(e -> e.getPrenom().equals("Mike")).findAny().get());
       System.out.println(etudiants.stream().count());
       Files.walk(Paths.get(".."), FileVisitOption.FOLLOW_LINKS).forEach(System.out::println);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void exo2(Stream<String> lignes) {
    List<String> mesStrings = lignes.collect(Collectors.toList());
    mesStrings.forEach(System.out::println);
  }

  private static List<Etudiant> mesEtudiants1(Stream<String> lignes) {
    return lignes.map(s -> new Etudiant(s)).collect(Collectors.toList());
  }
  
  private static boolean isAeWord(String mot) {
    return mot.toUpperCase().contains("AE");
  }

  private static List<Etudiant> mesEtudiants2(Stream<String> lignes) {
    return lignes.map(s -> new Etudiant(s.split(";")[3], s.split(";")[0]))
        .collect(Collectors.toList());
  }
}
