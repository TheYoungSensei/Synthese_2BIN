package producteur_consommateur;

import java.util.concurrent.LinkedBlockingQueue;


public class Producteur extends Thread {
	
	
	private LinkedBlockingQueue<Message> queue;
	private int index;

	public Producteur(LinkedBlockingQueue<Message> queue, int i) {
		this.queue=queue;
		this.index=i;
	}

	@Override
	public void run() {
		try {
			Thread.sleep((int)(Math.random()*100));
		} catch (InterruptedException e) {}
		Message m=new Message("sender "+index);
		queue.add(m);
		System.out.println(m.getResponse());
	}
	
	public static void main(String[] args) {
		LinkedBlockingQueue<Message> queue=new LinkedBlockingQueue<Message>();
		new Consommateur(queue).start(); // starts receiver
		for(int i=0; i<100; i++) {
			new Producteur(queue,i).start();
		}
		
	}
	
}
