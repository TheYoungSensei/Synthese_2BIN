package exercices;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Exercices5Etudiants {

	public static void main(String[] args) {
		BiFunction<String, String, Etudiant> biFunct = Etudiant::new;

		Function<String, Etudiant> funct = Etudiant::new;

		// Supplier of streams
		Supplier<Stream<String>> lignesSupplier = () -> {
			try {
				return Files.lines(Paths.get("etudiants.csv"), Charset.forName("Cp1252"));
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new UncheckedIOException(e1);
			}
		};

		// 1
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 1\n====");
			String etudiant1 = lignes.findFirst().get();
			System.out.println(etudiant1);
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}

		// 2a
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 2a\n=====");
			List<Etudiant> lS = lignes.map(s -> s.split(";")).map(tabS -> biFunct.apply(tabS[3], tabS[0])).collect(toList());
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}
		
		// 2b
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 2b\n=====");
			List<Etudiant> lS2 = lignes.map(s -> funct.apply(s)).collect(toList());
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}

		// 3
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 3\n====");
			List<Etudiant> lS3 = lignes.map(s -> funct.apply(s)).collect(toList());
			Predicate<String> pred = w -> w.length() < 6;
			pred = pred.and(w -> w.contains("Y"));
			pred = pred.or(w -> w.contains("I"));
			pred = pred.and(w -> w.length() < 6);
			lS3.stream().map(Etudiant::getNom).filter(pred).forEach(System.out::println);
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}

		// 4
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 4\n====");
			String aeNom = lignes.map(s -> funct.apply(s)).map(Etudiant::getPrenom).filter(Exercices5Etudiants::isAeWord).findAny().orElse("none");
			System.out.println(aeNom);
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}
		
		// 5
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 5\n====");
			Predicate<Etudiant> pre = s -> !s.getEmail().isEmpty();
			System.out.println(lignes.map(s -> funct.apply(s)).filter(pre).allMatch(pre));
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}
		
		// 6
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 6\n====");
			Predicate<Etudiant> pred1 = s -> s.getPrenom().equals("Kevin");
			System.out.println(lignes.map(s -> funct.apply(s)).filter(pred1).map(Etudiant::getMatricule).findAny().orElse("none"));
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}
		
		// 7
		try (Stream<String> lignes = lignesSupplier.get()) {
			System.out.println("\nEx 7\n====");
			System.out.println(lignes.map(s -> funct.apply(s)).count());
		} catch (UncheckedIOException e) {
			e.printStackTrace();
		}
		
		// 8
		try (Stream<Path> paths = Files.walk(Paths.get("."))) {
			System.out.println("\nEx 8\n====");
			paths.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static boolean isAeWord(String word) {
		return (word.contains("ae"));
	}

}
