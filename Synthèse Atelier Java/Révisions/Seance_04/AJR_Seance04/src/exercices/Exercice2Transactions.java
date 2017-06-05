package exercices;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Exercice2Transactions {

  public static void main(String[] args) {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions =
        Arrays.asList(new Transaction(brian, 2011, 300), new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400), new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950));

    List<Transaction> transac1 = transactions.stream().filter(t -> t.getYear() == 2011)
        .sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
    transac1.forEach(System.out::println);

    List<String> transac2 = transactions.stream().map(trans -> trans.getTrader().getCity())
        .distinct().collect(Collectors.toList());
    transac2.forEach(System.out::println);

    List<Trader> transac3 = transactions.stream().map(Transaction::getTrader)
        .filter(trader -> trader.getCity().equals("Cambridge"))
        .sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
    transac3.forEach(System.out::println);

    String transac4 = transactions.stream().map(Transaction::getTrader).map(Trader::getName)
        .distinct().sorted().collect(Collectors.joining(","));
    System.out.println(transac4);

    boolean transac5 = transactions.stream()
        .anyMatch(transaction -> "Milan".equals(transaction.getTrader().getCity()));
    System.out.println(transac5);

    List<Integer> transactions6 =
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
            .map(t -> t.getValue()).collect(Collectors.toList());
    transactions6.forEach(System.out::println);

    Integer transac7 = transactions.stream().map(Transaction::getValue).reduce(Integer::max).get();
    System.out.println(transac7);

    Transaction transac8 =
        transactions.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2).get();

    System.out
        .println(transactions.stream().collect(Collectors.summarizingInt(Transaction::getValue)));



  }
}
