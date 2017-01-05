import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

public class MachineServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = req.getParameter("type");
		switch(type) {
		case "post":
			effectuerModif(req,resp);
		case "init":
				initialisationPage(req, resp);
				break;
		default :
				resp.setStatus(500);
				resp.getOutputStream().write("Attention type invalide".getBytes());
				break;
		}
	}

	private void effectuerModif(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		String jsonreq = req.getParameter("json");
		if(jsonreq != null) {
			System.out.println(jsonreq);
			System.out.println(id);
			Files.write(Paths.get("machines/" + id), jsonreq.getBytes());
		}
	}

	private void initialisationPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Genson genson=new GensonBuilder()
				.useDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
				.useIndentation(true)
				.useConstructorWithArguments(true)
				.create();
		HashMap<String, String> machines = new HashMap<String, String>();
		DirectoryStream<Path> ds  = Files.newDirectoryStream(Paths.get("./machines"));
		for(Path chemin : ds){
			machines.put(chemin.getFileName().toString(), new String(Files.readAllBytes(chemin)));
		}
		String msg = genson.serialize(machines);
		resp.setStatus(200);
		resp.setContentType("text/html");
		resp.getOutputStream().write(msg.getBytes());
	}
}
