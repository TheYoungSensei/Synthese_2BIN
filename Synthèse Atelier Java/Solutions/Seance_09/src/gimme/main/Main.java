package gimme.main;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

public class Main {

	public static void main(String[] args) throws Exception {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File("./web").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(ctx);
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
	}

}
