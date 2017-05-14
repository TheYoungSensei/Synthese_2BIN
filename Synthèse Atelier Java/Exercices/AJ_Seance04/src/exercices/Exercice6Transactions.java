package exercices;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import exercices.Transaction.TransactionLevel;

public class Exercice6Transactions {

  public static void main(String[] args) {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions =
        Arrays.asList(new Transaction(brian, 2011, 300), new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400), new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950));
    Map<Trader, List<Transaction>> maMap =
        transactions.stream().collect(Collectors.groupingBy(Transaction::getTrader));
    affichageExo1(maMap);
    Map<Trader, Long> exo2 = transactions.stream()
        .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));
    affichageExo2(exo2);
    Map<Trader, Transaction> exo3 = transactions.stream()
        .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.collectingAndThen(
            Collectors.maxBy(Comparator.comparingInt(Transaction::getValue)), Optional::get)));
    affichageExo3(exo3);
    Map<String, Map<Trader, List<Transaction>>> exo4 =
        transactions.stream().collect(Collectors.groupingBy(t -> t.getTrader().getCity(),
            Collectors.groupingBy(Transaction::getTrader, Collectors.toList())));
    affichageExo4(exo4);
    
    Map<TransactionLevel, List<Transaction>> exo5 = transactions.stream().collect(Collectors
        .groupingBy((Transaction t) -> {
          if (t.getValue() >= 1000)
            return TransactionLevel.VeryHi;
          else if (t.getValue() >= 800 && t.getValue() < 1000)
            return TransactionLevel.Hi;
          else if (t.getValue() >= 600 && t.getValue() < 800)
            return TransactionLevel.Me;
          else
            return TransactionLevel.Lo;
        }));
    affichageExo5(exo5);
    Map<Boolean, List<Transaction>> exo6 = transactions.stream().collect(Collectors.partitioningBy(t -> t.getTrader().getCity().equals("Cambridge")));
    affichageExo6(exo6);
    
  }
  
  private static void affichageExo6(Map<Boolean, List<Transaction>> map) {
    for(Boolean bool : map.keySet()) {
      for(Transaction t : map.get(bool)) {
        System.out.println(bool + " " + t);
      }
    }
  }
  
  private static void affichageExo5(Map<TransactionLevel, List<Transaction>> map) {
    for(TransactionLevel t : map.keySet()) {
      for(Transaction trans : map.get(t)) {
        System.out.println(t + " " + trans);
      }
    }
  }

  private static void affichageExo1(Map<Trader, List<Transaction>> map) {
    for (Trader t : map.keySet()) {
      System.out.println(t + ":" + map.get(t).size());
    }
  }

  private static void affichageExo2(Map<Trader, Long> map) {
    for (Trader t : map.keySet()) {
      System.out.println(t + ":" + map.get(t));
    }
  }

  private static void affichageExo3(Map<Trader, Transaction> map) {
    for (Trader t : map.keySet()) {
      System.out.println(t + ":" + map.get(t));
    }
  }

  private static void affichageExo4(Map<String, Map<Trader, List<Transaction>>> map) {
    for (String s : map.keySet()) {
      for (Trader t : map.get(s).keySet()) {
        for (Transaction trans : map.get(s).get(t)) {
          System.out.println(s + " " + t + ":" + trans);
        }
      }
    }
  }
}
