private Map<String, Sommet> titres = new HashMap<String, Sommet>();
private Map<Sommet, Sommet> antecedents = new HashMap<Sommet, Sommet>();
private Map<Integer, Sommet> ids = new HashMap<Integer, Sommet>();

private SortedMap<Sommet, Integer> etiquettesProvisoires;
private SortedMap<Sommet, Integer> etiquettesDefinitives;

private int poidsTotal;

private Comparator<Sommet> comparator = new Comparator<Sommet>() {

	@Override
	public int compare(Sommet s1, Sommet s2) {
		int difference = s1.getPoids() - s2.getPoids();
		if(difference != 0) {
			return difference;
		}
		return s1.getPageWiki().getIdentifiantProjet() - s2.getPageWiki().getIdentifiantProjet();
	}
}

@Override
public List<Sommet> algorithme(Sommet depart, Sommet arrivee) {
	etiquettesProvisoires = new TreeMap<Sommet, Integer>(comparator);
	etiquettesDefinitives = new TreeMap<Sommet, Integer>(comparator);
	this.poidsTotal = 0;
	Sommet courant = depart;
	courant.setPoids(0);
	this.etiquettesDefinitives.put(courant, 0);

	while(!courant.equals(arrivee)) {
		for(Sommet s : courant.getArcs()) {
			if(s == null) {
				return new LinkdedList<Sommet>();
			}
			if(!etiquettesDefinitives.containsKey(s)) {
				if(etiquettesProvisoires.containsKey(s)) {
					if(etiquettesProvisoires.get(s) > etiquettesDefinitives.get(courant) + s.getPageWiki().cout()) {
						s.setCout(etiquettesDefinitives.get(courant) + s.getPageWiki().cout());
						this.etiquettesProvisoires.put(s, etiquettesDefinitives.get(courant) + s.getPageWiki().cout());
						this.antecedents.put(s, courant);
					}
				} else {
					s.setCout(this.etiquettesDefinitives.get(courant) + s.getPageWiki().cout());
					this.etiquettesProvisoires.put(s, (etiquettesDefinitives.get(courant) + s.getPageWiki().cout()));
					this.antecedents.put(s, courant);
				}
			}
		}
		if(etiquettesProvisoires.isEmpty()) {
			return new LinkdedList<Sommet>();
		}
		courant = etiquettesProvisoires.firstKey();
		this.etiquettesDefinitives.put(courant, this.etiquettesProvisoires.get(courant));
		etiquettesProvisoires.remove(courant);
	}
	LinkdedList<Sommet> chemin = new LinkdedList<Sommet>();
	Sommet etape = arrivee;
	while(!etape.equals(depart)) {
		this.poidsTotal += etape.getPageWiki().cout();
		chemin.push(etape);
		etape = antecedents.get(etape);
	}
	chemin.push(depart);
	return chemin;
}



















