package casUtilisation;

import java.util.ArrayList;
import java.util.List;

import domaine.*;
import exceptions.NoCommandeEnCoursException;

import static casUtilisation.Ingredients.*;

public final class GestionPizzeria {
	
	private static class GestionPizzeriaLoader {
		private static final GestionPizzeria INSTANCE = new GestionPizzeria();
	}
	
	public PizzaComposee p_4saisons, p_4fromages, p_margarita, p_duchef, p_mariniere;
	private List<Client> clients;
	
	private GestionPizzeria() {
		if(GestionPizzeriaLoader.INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(artichaut);
		ingredients.add(jambon);
		ingredients.add(olives);
		ingredients.add(parmesan);
		ingredients.add(mozza);
		p_4saisons = new PizzaComposee("4 saisons", "4 goûts qui defilent selon les saisons", ingredients);
		ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(parmesan);
		ingredients.add(gogonzola);
		ingredients.add(pecorino);
		ingredients.add(mozza);
		p_4fromages = new PizzaComposee("4 fromages", "le mélange généreux des fromages italiens", ingredients);
		ingredients = new ArrayList<>();
		ingredients.add(tomate);
		ingredients.add(mozza);
		p_margarita = new PizzaComposee("margarita", "la simplicité culinaire", ingredients);
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
		this.clients = new ArrayList<Client>();
	}
	
	public static GestionPizzeria getInstance(){
		return GestionPizzeriaLoader.INSTANCE;
	}
	
	public Client enregistrerClient(String nom, String prenom, String telephone){
		Client client = new Client(clients.size() + 1, nom, prenom, telephone);
		clients.add(client);
		return client;
	}
	
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
	
	public void ajouterALaCommande(Client client, Pizza pizza, int quantite) throws NoCommandeEnCoursException{
		client = rechercherBonClient(client);
		obtenirCommandeEnCours(client);
		client.getCommandeEnCours().ajouterLigneCommande(pizza, quantite);
	}
	
	public void creerPizzaComposable(Client client, int quantite, Ingredient... ingredients) throws NoCommandeEnCoursException{
		client = rechercherBonClient(client);
		obtenirCommandeEnCours(client);
		PizzaComposable pizza = new PizzaComposable(client);
		for(Ingredient ingredient : ingredients) {
			pizza.ajouterIngredient(ingredient);
		}
		client.getCommandeEnCours().ajouterLigneCommande(pizza, quantite);
	}
	
	private void obtenirCommandeEnCours(Client client) throws NoCommandeEnCoursException {
		client = rechercherBonClient(client);
		Commande commande = client.getCommandeEnCours();
		if(commande == null)
			throw new NoCommandeEnCoursException("le client n'a pas de commande en cours");
	}
	
	public void creerPizzaComposable(Client client, Ingredient...ingredients ) throws NoCommandeEnCoursException{
		client = rechercherBonClient(client);
		this.creerPizzaComposable(client, 1 ,ingredients);
	}
	
	public void validerCommande(Client client) throws NoCommandeEnCoursException {
		client = rechercherBonClient(client);
		client.cloturerCommandeEnCours();
	}
	
}
