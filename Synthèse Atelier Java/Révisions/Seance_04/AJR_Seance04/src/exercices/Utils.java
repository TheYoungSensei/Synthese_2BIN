package exercices;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utils {

  public static <T> List<T> allMatches(List<T> candidates, Predicate<T> matchFunction) {
    List<T> results = new ArrayList<T>();
    candidates.forEach(possibleMatch -> {
      if (matchFunction.test(possibleMatch))
        results.add(possibleMatch);
    });
    return results;
  }

  public static <T, R> List<R> transformedList(List<T> originals, Function<T, R> transformer) {
    List<R> results = new ArrayList<R>();
    originals.forEach(original -> results.add(transformer.apply(original)));
    return results;

  }
}
