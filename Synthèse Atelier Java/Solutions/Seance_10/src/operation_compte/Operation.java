package operation_compte;

import java.util.ArrayList;
import java.util.List;

public class Operation extends Thread {
	private Compte compte;

	public Operation(Compte compte) {
		this.compte = compte;
	}

	@Override
	public void run() {
		int amount = (int) (Math.random() * 1000);
		compte.depot(amount);
		compte.retrait(amount);
		System.out.println("Solde est : " + compte.getSolde());
	}

	public static void main(String[] args) {
		List<Thread> liste = new ArrayList<Thread>();
		Compte compte=new Compte();
		for(int i=0; i<1000; i++) {
			Thread t = new Operation(compte);
			t.start();
			liste.add(t);
		}
		liste.forEach((t)->{try {
			((Thread)t).join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
		System.out.println(compte.getSolde());
	}
}
