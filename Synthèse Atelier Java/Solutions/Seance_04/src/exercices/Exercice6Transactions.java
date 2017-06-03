package exercices;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class Exercice6Transactions {
	enum TransactionsLevel {
		VeryHi, Hi, Lo, Me
	}

	public static void main(String[] args) {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");

		List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950));

		Map<Trader, List<Transaction>> map1 = transactions.stream().collect(groupingBy(Transaction::getTrader));
	
		Map<Trader, Long> map2 = transactions.stream().collect(groupingBy(Transaction::getTrader,counting()));
		System.out.println(map2);
		
		Map<Trader, Transaction> map3 = transactions.stream().collect(groupingBy(Transaction::getTrader,collectingAndThen(maxBy(comparingLong(Transaction::getValue)), Optional::get)));
		System.out.println(map3);
		Map<String , Map<Trader,List<Transaction>>> map4 = transactions.stream().collect(groupingBy((Transaction tr)->tr.getTrader().getCity(),groupingBy(Transaction::getTrader)));
		System.out.println(map4);
		
		Map<TransactionsLevel,List<Transaction>> map5 = transactions.stream().collect(groupingBy((Transaction tr)->{if(tr.getValue()>=1000)return TransactionsLevel.VeryHi;else if(tr.getValue()>=800)return TransactionsLevel.Hi;else if(tr.getValue()>=600)return TransactionsLevel.Me; return TransactionsLevel.Lo;}));
		System.out.println(map5);
		
		Map<Boolean,List<Transaction>> map6 = transactions.stream().collect(partitioningBy(tr->tr.getTrader().getCity().equals("Cambridge")));
		System.out.println(map6);
	}
}
