package exercices;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Exercice3String {

  public static void main(String[] args) {
    List<String> words = Arrays.asList("hi", "hello", "bye", "Ola");

    // 1
    words.forEach(System.out::println);
    // 2
    System.out.println(words.stream().map(w -> w + "!").collect(Collectors.toList()));
    // 3
    String[] excitingWords2 = words.stream().toArray(String[]::new);
    // 4
    System.out.println(words.stream().map(String::toUpperCase).sorted(Comparator.reverseOrder())
        .collect(Collectors.toList()));
    // 5
    String res = words.stream().filter(s -> s.length() % 2 == 0).collect(Collectors.joining(","));
    // 6
    System.out.println("6->" + check(words, 'w'));
    // 7
    Function<String, String> toUpper = t -> {
      System.out.println("TO UPPER " + t);
      return t.toUpperCase();
    };
    System.out.println(words.stream().map(toUpper).filter(s -> s.length() < 4)
        .filter(s -> s.contains("E")).findFirst().orElse("no match"));

    // 8 - sans map

    String upperCaseWords = words.stream().reduce("", (s1, s2) -> s1 + s2.toUpperCase());
    System.out.println(upperCaseWords);

    String upperCaseWordsAlt = words.stream().reduce("", String::concat).toUpperCase();
    System.out.println(upperCaseWordsAlt);

    // 8 - avec map

    String upperCaseWords2 = words.stream().map(String::toUpperCase).reduce("", String::concat);
    System.out.println(upperCaseWords2);

    // 9

    int numberOfChars = words.stream().mapToInt(String::length).sum();
    System.out.println("Nombre total carac : " + numberOfChars);

    // 10

    long numberOfWordsWithE = words.stream().filter(s -> s.contains("e")).count();
    System.out.println("Nombre mot avec e : " + numberOfWordsWithE);
  }

  public static String check(List<String> strings, char c) {
    return strings.stream().filter(s -> s.indexOf(c) >= 0).filter(s -> s.length() < 4).findFirst()
        .map(String::toUpperCase).orElse("No match");
  }
}
