package exercices;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Exercice2Transactions {

  /*
   * Utiliser distinct + map
   */

  public static void main(String[] args) {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions =
        Arrays.asList(new Transaction(brian, 2011, 300), new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400), new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950));
    exo1(transactions);
    exo2(transactions);
    exo3(transactions);
    System.out.println(exo4(transactions));
    System.out.println(exo5(transactions));
    exo6(transactions);
    System.out.println(exo7(transactions));
    System.out.println(exo8(transactions));
    exo9(transactions);
  }
  
  private static void exo9(List<Transaction> transactions) {
    System.out.println(transactions.stream().collect(Collectors.summarizingInt(Transaction::getValue)));
  }
  
  private static String exo8(List<Transaction> transactions) {
    return transactions.stream().min(Comparator.comparing(Transaction::getValue)).get().toString();
  }
  
  private static int exo7(List<Transaction> transactions) {
    return transactions.stream().mapToInt((Transaction t) -> t.getValue()).max().getAsInt();
  }

  private static void exo6(List<Transaction> transactions) {
    transactions.stream().filter((Transaction t) -> t.getTrader().getCity().equals("Cambridge")).map((Transaction t) -> t.getValue()).forEach(System.out::println);;
  }
  
  private static boolean exo5(List<Transaction> transactions) {
    Predicate<Transaction> predicate = (Transaction t) -> t.getTrader().getCity().equals("Milan");
    return transactions.stream().anyMatch(predicate);
  }

  private static String exo4(List<Transaction> transactions) {
    return transactions.stream().map((Transaction t) -> t.getTrader().getName()).distinct().sorted((String s1, String s2) -> s1.compareTo(s2)).collect(Collectors.joining());    
  }

  private static void exo3(List<Transaction> transactions) {
    transactions.stream().map((Transaction t) -> t.getTrader()).distinct().filter((Trader t) -> t.getCity().equals("Cambridge")).sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);;
  }

  private static void exo2(List<Transaction> transactions) {
    transactions.stream().map((Transaction t) -> t.getTrader().getCity()).distinct().forEach(System.out::println);
  }

  private static void exo1(List<Transaction> transactions) {
    transactions.stream().filter((Transaction t) -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).forEach(System.out::println);
  }
}
