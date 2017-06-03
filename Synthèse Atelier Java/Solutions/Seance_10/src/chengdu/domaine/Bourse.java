package chengdu.domaine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import static chengdu.util.Util.*;

public enum Bourse {
	INSTANCE;

	private Set<Lot> lotsEnVente = new HashSet<Lot>();
	
	public synchronized boolean ajouterLot(Lot lot){
		checkObject(lot);
		if (this.contientLot(lot)) return false;
		this.lotsEnVente.add(lot);
		return true;
	}
	
	public synchronized boolean supprimerLot(Lot lot) {
		if (!this.contientLot(lot))
			return false;		
		lotsEnVente.remove(lot);		
		return true;	
	}
	
	public synchronized boolean contientLot(Lot lot) {
		checkObject(lot);
		return lotsEnVente.contains(lot);
	}
	
	public synchronized Iterator<Lot> lotsEnvente(){
		// return Collections.unmodifiableCollection(lotsEnVente).iterator();
		// On va retourner une copie de la collection pour éviter
		// les problèmes en cas de suppression par un autre thread
		return Collections.unmodifiableSet((Set<Lot>) ((HashSet<Lot>)lotsEnVente).clone()).iterator();
		//unmodifiableSet n'est pas vraiment nécessaire mais cela permet de faire 
		//en sorte que l'exception sera lancée en cas d'appel de remove()	
	}
	

	
}
