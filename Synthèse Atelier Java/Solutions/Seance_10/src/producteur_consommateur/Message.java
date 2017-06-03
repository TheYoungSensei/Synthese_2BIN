package producteur_consommateur;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.print.attribute.UnmodifiableSetException;


public class Message {
	private AtomicBoolean setEffectue = new AtomicBoolean(false);
	private boolean getEffectue = false;
	private String reponse;
	private String msg;
	private LinkedBlockingQueue<String> response=new LinkedBlockingQueue<String>(1);

	public Message(String msg) {
		this.msg=msg;
	}
	
	public void setResponse(String msgbis) {
		if (!setEffectue.getAndSet(true)) {
			try {
				response.put(msgbis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else
			throw new UnmodifiableSetException();
	}
	
	public synchronized String getResponse() {
		if (!getEffectue)
			try {
				reponse = response.take();
				getEffectue = true;
			} catch (InterruptedException e) {
				return null;
			}
		return reponse;
	}
	
	public String getMessage() {
		return this.msg;
	}
}
