private Map<Integer, Sommet> sommets = new HashMap<Integer, Sommet>();
private Map<String, Sommet> titres = new HashMap<String, Sommet>();
private int nombreNoeud;

public List<Sommet> algorithme(Sommet sommetDepart, Sommet sommetArrivee) {
	this.nombreNoeud = 0;
	LinkedList<Sommet> mesSommets = new LinkedList<Sommet>(); //Sommets à parcourir
	Set<Sommet> tousMesSommets = new HashSet<Sommet>(); // Sommets parcourus + à parcourir
	Map<Sommet, Sommet> ancetres = new HashMap<Sommet, Sommet>(); //Clé = Sommet, Valeur = ancêtre
	Sommet courant = sommetDepart;
	tousMesSommets.add(courant);
	while(!courant.equals(sommetArrivee)) {
		for(Sommet sommet : courant.getArcs()) {
			if(!tousMesSommets.contains(sommet)) {
				ancetres.put(sommet, courant);
				mesSommets.add(sommet);
				tousMesSommets.add(sommet);
			}
		}
		if(mesSommets.isEmpty())
			return new LinkedList<Sommet>();
		courant = mesSommets.pollFirst();
		if(courant == null)
			return new LinkedList<Sommet>();//Normalement on ne devrait pas être ici
	}
	LinkedList chemin = new LinkedList<Sommet>();
	Sommet sommet = sommetArrivee;
	while(sommet != null) {
		this.nombreNoeud++;
		chemin.push(sommet);
		sommet.ancetres.get(sommet);
	}
	return chemin;
}