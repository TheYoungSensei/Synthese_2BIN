package fichier;


public class TestFichierBis {

	public static void main(String[] args) {
		FichierBis f = new FichierBis();
		Thread[] threads = new Thread[100];
		for (byte i = 0;i <= 99;i++ ){
			threads[i] = new MonThread(i,f);
			threads[i].start();
		}
		
		for (Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				//Ignore
			}
		}

		for (int i = 0; i < 100; i++){
			System.out.println(f.get(i));
		}
		
	}
	
	private static class MonThread extends Thread{
		private byte numero;
		private FichierBis fichier;
		
		public  MonThread(byte numero,FichierBis fichier) {
			this.numero = numero;
			this.fichier = fichier;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < 25; i++){
				fichier.set(numero, numero);
			}
		}
		
	}
}
