package exercices;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Exercice4 {

  public static void main(String[] args) {
    double[] mesDoubles = new Random().doubles(10, 0, 100).toArray();
    DoubleStream.of(mesDoubles).forEach(System.out::println);
    System.out.println("Fin exo 1");
    DoubleStream.of(mesDoubles).map(d -> Math.sqrt(d)).forEach(System.out::println);
    System.out.println("Fin exo 2");
    randomDoubleStream(100).limit(5).forEach(System.out::println);
    randomStreamDouble(100).limit(5).forEach(System.out::println);
    System.out.println("Fin exo 3");
    List<Double> mesDoubles2 = randomDoubleStream(100).limit(10).boxed().collect(Collectors.toList());
    mesDoubles2.stream().forEach(System.out::println);
    List<Double> mesDoubles3 = randomStreamDouble(100).limit(10).collect(Collectors.toList());
    mesDoubles3.stream().forEach(System.out::println);
    System.out.println("Fin exo 4");
    double[] mesDoubles4 = randomDoubleStream(100).limit(20).toArray();
    Double[] mesDoubles5 = randomStreamDouble(100).limit(20).toArray(Double[]::new);
  }
  
  private static DoubleStream randomDoubleStream(int valMax) {
    return new Random().doubles(0, valMax);
  }
  
  private static Stream<Double> randomStreamDouble(int valMax) {
    return Stream.generate(Math::random).map(d -> d * valMax);
  }
}
