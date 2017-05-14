package exercices;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Utils {

  public static <T> List<T> allMatches(List<T> mesObjets, Predicate<T> predicat) {
    return (List<T>) mesObjets.stream().filter(predicat).collect(Collectors.toList());
  }
  
  public static <T, F> List<F> transformedList(List<T> mesStrings, Function<T , F> fonction) {
    List<F> retour = new ArrayList<>();
    mesStrings.forEach(s -> retour.add(fonction.apply(s)));
    return retour;
  }
}
