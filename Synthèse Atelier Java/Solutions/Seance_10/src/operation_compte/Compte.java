package operation_compte;

import java.util.concurrent.atomic.AtomicInteger;

public class Compte {
	private AtomicInteger solde= new AtomicInteger(0);
	
	public void depot(int amount) {
		change(amount);
	}
	
	private void change(int amount) {
		try {
			Thread.sleep((int)(Math.random()*100));
		} catch (InterruptedException e) {
		}
		solde.addAndGet(amount);
	}

	public void retrait(int amount) {
		change(-amount);
	}

	public int getSolde() {
		return solde.get();
	}

}
