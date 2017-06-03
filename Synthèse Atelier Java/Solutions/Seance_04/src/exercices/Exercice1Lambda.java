package exercices;

import java.util.Arrays;
import java.util.List;

import static exercices.Utils.*;

public class Exercice1Lambda {
public static void main(String[] args) {
	List<String> words = 
	        Arrays.asList("hi", "hello", "hola", "bye", "goodbye", "adios");
	    System.out.printf("Original words: %s.%n", words);
	    List<String> shortWords = allMatches(words, s -> s.length() < 4);
	    System.out.printf("Short words: %s.%n", shortWords);
	    List<String> wordsWithB = allMatches(words, s -> s.contains("b"));
	    System.out.printf("B words: %s.%n", wordsWithB);
	    List<String> evenLengthWords = 
	        Utils.allMatches(words, s -> (s.length() % 2) == 0);
	    System.out.printf("Even-length words: %s.%n", evenLengthWords);
	    List<String> shortWords2 = 
	        Utils.allMatches(words, s -> s.length() < 4);
	    System.out.printf("Short words: %s.%n", shortWords2);
	    List<String> wordsWithB2 = 
	        Utils.allMatches(words, s -> s.contains("b"));
	    System.out.printf("B words: %s.%n", wordsWithB2);
	    List<String> evenLengthWords2 = 
	        Utils.allMatches(words, s -> (s.length() % 2) == 0);
	    System.out.printf("Even-length words: %s.%n", evenLengthWords2);
	    List<Integer> nums = Arrays.asList(1, 10, 100, 1000, 10000);
	    List<Integer> bigNums =
	        Utils.allMatches(nums, n -> n>500);
	    System.out.printf("Nums bigger than 500: %s.%n", bigNums);
	    List<String> excitingWords = Utils.transformedList(words, s -> s + "!");
	    System.out.printf("Exciting words: %s.%n", excitingWords);
	    List<String> eyeWords = Utils.transformedList(words, s -> s.replace("i", "eye"));
	    System.out.printf("Eye words: %s.%n", eyeWords);
	    List<String> upperCaseWords = Utils.transformedList(words, String::toUpperCase);
	    System.out.printf("Uppercase words: %s.%n", upperCaseWords);
	    List<Integer> lengthOfWords = Utils.transformedList(words, String::length);
	    System.out.printf("Length of words: %s.%n", lengthOfWords);
	    List<Double> inversingNums = transformedList(nums, i -> 1.0/i);
	    System.out.printf("Inversing nums: %s.%n", inversingNums);

}
	

}
