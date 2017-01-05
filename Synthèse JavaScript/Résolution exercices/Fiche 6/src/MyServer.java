import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;


public class MyServer extends HttpServlet{
	
	public static void main(String[] args) throws Exception {
		// lie le server � un port
		 Server server = new Server(8000);
		// instancie un WebAppContext pour configurer le server
		 WebAppContext context = new WebAppContext();
		// O� se trouvent les fichiers (ils seront servis par un DefaultServlet)
		 context.setResourceBase("www");
		// MaServlet r�pondra aux requ�tes commen�ant par /chemin/
		 context.addServlet(new ServletHolder(new CalcServlet()), "/Calc");
		// Le DefaultServlet sert des fichiers (html, js, css, images, ...). Il est en g�n�ral ajout� en dernier pour que les autres servlets soient prioritaires sur l�interpr�tation des URLs.
		 context.addServlet(new ServletHolder(new DefaultServlet()),"/");
		// ce server utilise ce context
		 server.setHandler(context);
		// allons-y
		 server.start();
	}
}
