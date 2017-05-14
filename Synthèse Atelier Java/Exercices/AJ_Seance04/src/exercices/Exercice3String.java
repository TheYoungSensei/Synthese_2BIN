package exercices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Exercice3String {

  private static List<String> strings = Arrays.asList("bonjour", "comment", "revoir", "eau");
  
  public static void main(String[] args) {
    exo1();
    exo2().stream().forEach(System.out::println);
    Arrays.asList(exo3()).stream().forEach(System.out::println);
    exo4().stream().forEach(System.out::println);
    System.out.println(exo5());
    System.out.println(exo6(strings, 'a'));
    System.out.println(exo8Map());
    System.out.println(exo8NoMap());
    System.out.println(exo9());
    System.out.println(exo10());
  }
  
  private static void exo1() {
    strings.stream().forEach(System.out::println);;
  }
  
  private static List<String> exo2() {
    return strings.stream().map((s) -> s = s + "!").collect(Collectors.toList());
  }
  
  private static String[] exo3() {
    return strings.stream().toArray(String[] ::new);
  }
  
  private static List<String> exo4() {
    return strings.stream().map((String s) -> s.toUpperCase() + "!").sorted((s1, s2) -> s2.compareTo(s1)).collect(Collectors.toList());
  }
  
  private static String exo5() {
    return strings.stream().filter(s -> s.length() % 2 == 0).collect(Collectors.joining(" "));
  }
  
  private static String exo6(List<String> strings, char carac) {
    return strings.stream().filter(s -> s.length() < 4).filter(s -> s.contains(String.valueOf(carac))).findFirst().map(s -> s.toUpperCase() + " TO UPPER").get();
  }
  
  private static String exo8Map() {
    return strings.stream().map(s -> s.toUpperCase()).collect(Collectors.joining(" "));
  }
  
  private static String exo8NoMap() {
    return strings.stream().collect(Collectors.joining(" ")).toUpperCase();
  }
  
  private static int exo9() {
    return strings.stream().collect(Collectors.joining()).length();
  }
  
  private static int exo10() {
    return strings.stream().filter(s -> s.contains(String.valueOf('e'))).collect(Collectors.toList()).size();
  }
  
  
}
