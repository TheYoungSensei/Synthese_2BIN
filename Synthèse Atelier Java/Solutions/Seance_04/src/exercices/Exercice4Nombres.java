package exercices;

import java.util.List;
import java.util.Random;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.*;

public class Exercice4Nombres {
	public static void main(String[] args) {
		double[] tabDouble = new Random().doubles(10,0,100).toArray();

		DoubleStream sDouble = new Random().doubles(1000);
		System.out.println(DoubleStream.of(tabDouble).map(Math::sqrt).sum());

		randomNums(10).limit(5).forEach(System.out::println);
		
		randomNumsD(10).limit(5).forEach(System.out::println);
		
		List<Double> randomNums1D = randomNumsD(10).limit(10).collect(Collectors.toList());
		System.out.println("List of random nums: " + randomNums1D);
		
		List<Double> randomNums1 = randomNums(10).limit(10).boxed().collect(Collectors.toList());
		System.out.println("List of random nums: " + randomNums1);
		
		double[] randomNums2 = randomNums(10).limit(20).toArray();
		System.out.println("Array of random nums: " + Arrays.asList(randomNums2));
	
		Double[] randomNumsD = randomNumsD(10).limit(20).toArray(Double[]::new);
	}

	public static DoubleStream randomNums(double maxValue) {
		return (DoubleStream.generate(() -> Math.random() * maxValue));
	}
	public static Stream<Double> randomNumsD(double maxValue) {
		return (Stream.generate(() -> Math.random() * maxValue));
	}
}
