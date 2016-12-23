context.setContextPath("/");
// Chemin de l'URL pour lequel ce contexte s'applique.
context.setWelcomeFiles(new String[] { "index.html" });
//Quel est le fichier a servir si l'utilisateur va a l'URL racine sans
plus de precision. www.monsite.com affichera www.monsite.com/index.html
context.setInitParameter("cacheControl","no-store,nocache, must-revalidate");
//Dans le protocole HTTP, le serveur dicte le comportement du cache du
navigateur. Ici on dit que par defaut, il ne faut pas stocker ni
retenir en cache les reponses aux requetes.
context.setInitParameter("redirectWelcome", "true");
//Quand c'est 'true', la page de bienvenue est affichee par redirection plutot que par suivi. En d'autres termes, l'URL change a la page de bienvenue. Dans tous les cas le contenu de cette page sera affichee.
context.setClassLoader(Thread.currentThread().getCont
extClassLoader());
//Pour des raisons de securite, il faut preciser sous quelle autorite doit s'effectuer le chargement des classes Java. Ici on utilise simplement l'autorita du Thread en cours.
context.setMaxFormContentSize(50000000);
//Specifie la taille limite des donnees qu'un frontend peut soumettre a ce back-end.
context.addServlet(new ServletHolder(new MyServlet()), "/url1");
//Enregistre une servlet pour repondre a l'url /url1 exactement.
context.addServlet(new ServletHolder(new MyOtherServlet ()), "/url2/*");
//Enregistre une servlet pour repondre a toute url commencant par
/url2, Si plusieurs servlets sont ajoutees, chaque requete est traitee dans l'ordre de cet ajout, jusqu'a trouver une servlet qui accepte de la traiter.
context.addServlet(new ServletHolder(new DefaultServlet()), "/");
// La DefaultServlet sert des fichiers, Ici toutes les URLs seront traitees comme des fichiers a trouver puisque cela commence a / directement, C'est pour cela qu'on ajoute la DefaultServlet en dernier en
general.