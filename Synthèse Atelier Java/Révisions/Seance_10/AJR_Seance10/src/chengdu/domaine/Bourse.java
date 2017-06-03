package chengdu.domaine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static chengdu.util.Util.*;

public enum Bourse {
	INSTANCE;

	private Set<Lot> lotsEnVente = new HashSet<Lot>();
	
	public boolean ajouterLot(Lot lot){
		checkObject(lot);
		if (this.contientLot(lot)) return false;
		this.lotsEnVente.add(lot);
		return true;
	}
	
	public boolean supprimerLot(Lot lot) {
		if (!this.contientLot(lot))
			return false;		
		lotsEnVente.remove(lot);		
		return true;	
	}
	
	public boolean contientLot(Lot lot) {
		checkObject(lot);
		return lotsEnVente.contains(lot);
	}
	
	public Iterator<Lot> lotsEnvente(){
		return Collections.unmodifiableCollection(lotsEnVente).iterator();		
	}
	

	
}
