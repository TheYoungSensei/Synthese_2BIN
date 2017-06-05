package exercices;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Exercice4Nombres {

  public static void main(String[] args) {
    double[] tabDouble = new Random().doubles(10, 0, 100).toArray();

    DoubleStream sDouble = new Random().doubles(1000);
    System.out.println(DoubleStream.of(tabDouble).map(Math::sqrt).sum());

    randomNums(10).limit(5).forEach(System.out::println);

    randomNumsD(10).limit(5).forEach(System.out::println);

    List<Double> randomNums1D = randomNumsD(10).limit(10).collect(Collectors.toList());
    System.out.println(randomNums1D);

    List<Double> randomsNums1 = randomNums(10).limit(20).boxed().collect(Collectors.toList());
    System.out.println(randomsNums1);

    double[] randomNums2 = randomNums(10).limit(20).toArray();
    System.out.println(randomNums2);

    Double[] randomsNumsD = randomNumsD(10).limit(20).toArray(Double[]::new);
  }

  private static DoubleStream randomNums(double maxValue) {
    return (DoubleStream.generate(() -> Math.random() * maxValue));
  }

  private static Stream<Double> randomNumsD(double maxValue) {
    return (Stream.generate(() -> Math.random() * maxValue));
  }
}
