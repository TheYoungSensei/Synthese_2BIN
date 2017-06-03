package producteur_consommateur;
import java.util.concurrent.LinkedBlockingQueue;


public class Consommateur extends Thread {

	private LinkedBlockingQueue<Message> messages;
	private int count=0;

	public Consommateur(LinkedBlockingQueue<Message> messages) {
		this.messages=messages;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Message m=this.messages.take();
				m.setResponse(count+" done processing "+m.getMessage());
				count++; // ici je n'ai plus de concurrence donc c'est ok
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
