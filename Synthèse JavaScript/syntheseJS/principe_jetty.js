// lie le server a un port
Server server = new Server(8080);
// instancie un WebAppContext pour configurer le server
WebAppContext context = new WebAppContext();
// Ou se trouvent les fichiers (ils seront servis par un DefaultServlet)
context.setResourceBase("c://web");
// MaServlet repondra aux requetes commencant par /chemin/
context.addServlet(new ServletHolder(new
MaServlet()), "/chemin/*");
// Le DefaultServlet sert des fichiers (html, js, css, images, ...). Il est en general ajoute en dernier pour que les autres servlets soient prioritaires sur l'interpretation des URLs.
context.addServlet(new ServletHolder(new DefaultServlet()),
"/");
// ce server utilise ce context
server.setHandler(context);
// allons-y
server.start();