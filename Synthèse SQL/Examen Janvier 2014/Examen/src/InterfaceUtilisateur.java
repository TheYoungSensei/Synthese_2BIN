import java.util.Scanner;

public class InterfaceUtilisateur {

	private static final Scanner scanner = new Scanner(System.in);
	private static Connexion connDb = new Connexion();

	public static void main(String[] args) {
		do {
			System.out.println("------------------------------------");
			System.out.println("Bienvenue dans le Toquemon");
			System.out.println("------------------------------------");
			System.out.println("1. Enregistrer un repérage");
			System.out.println("2. Lister l'occupation des zones");
			System.out.println("3. Historique Aventurier");
			int choix = scanner.nextInt();
			switch(choix){
				case  1 :
					enregistrerRep();
					break;
				case 2 :
					listerOccupationZones();
					break;
				case 3 :
					historiqueAventurier();
					break;
			}
		} while(lireCharOouN("Voulez vous continuer ?"));
	}

	private static void enregistrerRep() {
		System.out.println("Dans quelle zone avez vous vu l'aventurier ? ");
		String zone = scanner.next();
		System.out.println("Quel est le nom de l'aventurier que vous avez vu ?");
		String nomAventurier = scanner.next();
		System.out.println("A quelle date l'avez vous vu ? (JJ-MM-AAAA)");
		String date = scanner.next();
		System.out.println("A quelle heure l'avez vous vu ?");
		int heure = scanner.nextInt();
		System.out.println(connDb.enregistrerRep(zone, nomAventurier, date, heure));
	}

	private static void listerOccupationZones() {
		System.out.println("Pour quelle date souhaitez vous effectuer un listing ?");
		String date = scanner.next();
		System.out.println("Pour quelle heure souhaitez vous effectuer un listing ?");
		int heure = scanner.nextInt();
		System.out.println(connDb.listerZones(date, heure));
	}

	private static void historiqueAventurier() {
		System.out.println("Pour quel aventurier souhaitez vous effectuer la requête ? ");
		String nom = scanner.next();
		System.out.println(connDb.historiqueAventurier(nom));
		System.out.println(connDb.nbreReperages(nom));
	}
	
	private static boolean lireCharOouN(String msg) {
		char carac;
		do {
			System.out.println(msg + " (O/N)");
			carac = scanner.next().charAt(0);
		} while(carac != 'o' && carac != 'O' && carac != 'n' && carac != 'N');
		return carac == 'o' || carac == 'O';
	}
}
