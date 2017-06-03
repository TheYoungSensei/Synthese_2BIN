package casUtilisation;

import java.util.ArrayList;
import java.util.List;

import domaine.Client;
import domaine.Commande;
import domaine.Ingredient;
import domaine.Pizza;
import domaine.PizzaComposable;
import domaine.PizzaComposee;
import exceptions.NoCommandeEnCoursException;

import static casUtilisation.Ingredients.*;

public final class GestionPizzeria {
	
	private static class GestionPizzeriaLoader{
		private static final GestionPizzeria INSTANCE = new GestionPizzeria();

	}
	public PizzaComposee p_4saisons, p_4fromages, p_margarita, p_duchef, p_mariniere;
	private List<Client> clients = new ArrayList<Client>();

	private GestionPizzeria() {
		if (GestionPizzeriaLoader.INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(artichaut);
		ingredients.add(jambon);
		ingredients.add(olives);
		ingredients.add(parmesan);
		ingredients.add(mozza);
		p_4saisons = new PizzaComposee("4 saisons", "4 goûts qui défilent selon les saisons", ingredients);
		ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(parmesan);
		ingredients.add(gogonzola);
		ingredients.add(PECORINO);
		ingredients.add(mozza);
		p_4fromages = new PizzaComposee("4 fromages", "le mélange généreux des fromages italiens", ingredients);
		ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(mozza);
		p_margarita = new PizzaComposee("margarita", "la simplissité culinaire", ingredients);
		ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(aubergines);
		ingredients.add(jambon);
		ingredients.add(epinards);
		ingredients.add(mozza);
		ingredients.add(gogonzola);
		p_duchef = new PizzaComposee("du chef", "l'équilibre des saveurs du chef", ingredients);
		ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(scampis);
		ingredients.add(mozza);
		p_mariniere = new PizzaComposee("marinière", "les saveurs de l'océan", ingredients);

	}

	public static GestionPizzeria getInstance() {
		return GestionPizzeriaLoader.INSTANCE;
	}

	public Client enregistrerClient(String nom, String prenom, String telephone) {
		Client client = new Client(clients.size() + 1, nom, prenom, telephone);
		clients.add(client);
		return client;
	}

	/**
	 * Crée une commande
	 * 
	 * @param client
	 *            le client pour qui la commande est créée; ce client appartient
	 *            à la liste des clients
	 * @return la commande créée pour le client sinon null (pas d'exception)
	 */
	public Commande creerCommande(Client client) {
		client = rechercherBonClient(client);
		Commande commande = null;
		try {
			commande = new Commande(client);
		} catch (IllegalArgumentException e) {
			return null;
		}
		return commande;
	}

	private Client rechercherBonClient(Client client) {
		int index = this.clients.indexOf(client);
		client = this.clients.get(index);
		return client;
	}

	/**
	 * Ajoute à la commande en cours du client la pizza en quantité indiquée
	 * 
	 * @param client
	 *            le client qui a une commande en cours à qui on ajoute la
	 *            pizza; ce client appartient à la liste des clients
	 * @param pizza
	 *            la pizza à rajouter à la commande en cours du client ; la
	 *            pizza appartient à la carte des pizzas
	 * @param quantite
	 *            le nombre de fois qu'on ajouter la pizza à la commande en
	 *            cours du client
	 * @throws NoCommandeEnCoursException
	 *             si le client n'a pas de commande en cours
	 * @throws IllegalArgumentException
	 *             si la quantité est négative ou nulle
	 */
	public void ajouterALaCommande(Client client, Pizza pizza, int quantite) throws NoCommandeEnCoursException {
		client = rechercherBonClient(client);
		obtenirCommandeEnCours(client);
		client.getCommandeEnCours().ajouter(pizza, quantite);
	}

	/**
	 * Crée une pizza composable pour un client à partir des ingrédients. Ajoute
	 * ensuite cette pizza à la commande en cours du client en quantité passée
	 * en paramètre.
	 * 
	 * @param client
	 *            le client qui a une commande en cours qui crée un pizza
	 *            composable ; ce client appartient à la liste des clients
	 * @param quantite
	 *            le nombre de fois qu'on ajoute la pizza composabe à la
	 *            commande en cours du client
	 * @param ingredients
	 *            les ingrédients qui composent la pizza composable du client ;
	 *            la liste contient au moins un ingrédient et tous les
	 *            ingrédients de la liste sont dans Ingrédients
	 * @throws NoCommandeEnCoursException
	 *             si le client n'a pas de commande en cours
	 * @throws IllegalArgumentException
	 *             si la quantité est négative ou nulle
	 */
	public void creerPizzaComposable(Client client, int quantite, Ingredient... ingredients)
			throws NoCommandeEnCoursException {
		client = rechercherBonClient(client);
		obtenirCommandeEnCours(client);
		PizzaComposable pizza = new PizzaComposable(client);
		for (Ingredient ingredient : ingredients) {
			pizza.ajouter(ingredient);
		}
		client.getCommandeEnCours().ajouter(pizza, quantite);

	}

	private void obtenirCommandeEnCours(Client client) throws NoCommandeEnCoursException {
		client = rechercherBonClient(client);
		Commande commande = client.getCommandeEnCours();
		if (commande == null)
			throw new NoCommandeEnCoursException("le client n'a pas de commande en cours");
	}

	/**
	 * Crée une pizza composable pour un client à partir des ingrédients. Ajoute
	 * ensuite cette pizza à la commande en cours du client.
	 * 
	 * @param client
	 *            le client qui a une commande en cours qui crée un pizza
	 *            composable ; ce client appartient à la liste des clients
	 * @param ingredients
	 *            les ingrédients qui composent la pizza composable du client ;
	 *            la liste contient au moins un ingrédient et tous les
	 *            ingrédients de la liste sont dans Ingrédients
	 * @throws NoCommandeEnCoursException
	 *             si le client n'a pas de commande en cours
	 */
	public void creerPizzaComposable(Client client, Ingredient... ingredients) throws NoCommandeEnCoursException {
		client = rechercherBonClient(client);
		this.creerPizzaComposable(client, 1, ingredients);
	}

	/**
	 * Valide la commande en cours du client; la commande devient passée et
	 * n'est donc plus en cours.
	 * 
	 * @param client
	 *            le client qui valide sa commande ; ce client appartient à la
	 *            liste des clients
	 * @throws NoCommandeEnCoursException
	 *             si le client n'a pas de commande en cours
	 */
	public void validerCommande(Client client) throws NoCommandeEnCoursException {
		client = rechercherBonClient(client);
		client.cloturer();
	}
}
