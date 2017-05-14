package exercices;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Main {

  public static void main(String[] args) {
    test1();
    test2();
    test3();
    test4();
  }

  private static void test2() {
    List<Integer> nums = Arrays.asList(1, 10, 100, 1000, 10000);
    Predicate<Integer> predicat = (Integer s)  -> s == 1;
    List<Integer> result = Utils.allMatches(nums, predicat);
    affichage(result);
  }

  private static <T> void affichage(List<T> result) {
    result.forEach(System.out::println);
  }
  
  private static void test1() {
    List<String> words = Arrays.asList("hi", "hello", "hola", "bye", "goodbye", "adios");
    Predicate<String> predicat = (String s)  -> s.contains("o");
    List<String> result = Utils.allMatches(words, predicat);
    affichage(result);
  }
  
  private static void test3() {
    List<String> words = Arrays.asList("hi", "hello", "hola", "bye", "goodbye", "adios");
    List<String> result = Utils.transformedList(words, (String s) -> s += "!");
    affichage(result);
  }
  
  private static void test4() {
    List<String> words = Arrays.asList("hi", "hello", "hola", "bye", "goodbye", "adios");
    List<Integer> nums = Arrays.asList(1, 10, 100, 1000, 10000);
    List<Integer> wordLengths = Utils.transformedList(words, String::length);
    List<Double> inverses = Utils.transformedList(nums, i -> 1.0/i);
    affichage(wordLengths);
    affichage(inverses);
  }
}
