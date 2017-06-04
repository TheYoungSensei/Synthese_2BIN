package gimme.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import gimme.domaine.Propriete;
import gimme.domaine.Visibilite;

@SuppressWarnings("serial")
public class Controleur extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
  public static final int TAILLE_TAMPON = 10240; // 10 ko

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // Lecture init param 'chemin'
    String chemin = this.getServletConfig().getInitParameter("chemin");

    // Les données reçues sont multipart

    Part part = request.getPart("fichier");
    if (part.getSize() == 0) {
      RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
      rd.forward(request, response);
      return;
    }
    /*
     * Il faut déterminer s'il s'agit d'un champ classique ou d'un champ de type fichier : on
     * délègue cette opération à la méthode utilitaire getNomFichier().
     */
    String nomFichier = getNomFichier(part);

    if (nomFichier != null && !nomFichier.isEmpty()) {
      String nomChamp = part.getName();
      /*
       * Antibug pour Internet Explorer, qui transmet pour une raison mystique le chemin du fichier
       * local à la machine du client...
       * 
       * Ex : C:/dossier/sous-dossier/fichier.ext
       * 
       * On doit donc faire en sorte de ne sélectionner que le nom et l'extension du fichier, et de
       * se débarrasser du superflu.
       */
      nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
          .substring(nomFichier.lastIndexOf('\\') + 1);
      nomFichier = nomFichier.substring(1, nomFichier.length() - 1);

      /* Écriture du fichier sur le disque */
      ecrireFichier(part, nomFichier, chemin);
      request.setAttribute(nomChamp, nomFichier);
    }

    try {
      String nomClasse = nomFichier.substring(0, nomFichier.length() - 5);
      MyClassLoader cl = new MyClassLoader();
      String fi = chemin + nomClasse + ".class";

      Class cls = cl.findClass(fi);
      Propriete laClasse = new Propriete(nomClasse, Modifier.isAbstract(cls.getModifiers()));
      List<Propriete> attributs = new ArrayList<Propriete>();

      for (Field field : cls.getDeclaredFields()) {
        Visibilite visibilite = chercherVisibilite(field.getModifiers());
        boolean estStatique = false;
        if (Modifier.isStatic(field.getModifiers()))
          estStatique = true;
        String valDefaut = "";
        Propriete prop =
            new Propriete(field.getName() + " : " + field.getType().getSimpleName() + valDefaut,
                estStatique, visibilite);
        attributs.add(prop);
      }

      List<Propriete> methodes = chercherMethodes(cls);

      List<Propriete> constructeurs = chercherConstructeurs(cls);

      request.setAttribute("nom", nomClasse);
      request.setAttribute("identite", laClasse);
      request.setAttribute("attributs", attributs);
      request.setAttribute("methodes", methodes);
      request.setAttribute("constructeurs", constructeurs);

      RequestDispatcher rd = request.getServletContext().getNamedDispatcher("Index");
      rd.forward(request, response);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return;
  }

  private List<Propriete> chercherConstructeurs(Class cls) {
    List<Propriete> constructeurs = new ArrayList<Propriete>();

    for (Constructor constructeur : cls.getDeclaredConstructors()) {
      Visibilite visibilite = chercherVisibilite(constructeur.getModifiers());
      boolean estStatique = false;
      if (Modifier.isStatic(constructeur.getModifiers()))
        estStatique = true;
      String nom = constructeur.getName();
      String ex = chercherExceptions(constructeur.getExceptionTypes());
      Parameter[] parameters = constructeur.getParameters();
      nom += " (";
      nom += chercherParametres(parameters);
      nom += ") " + ex;

      Propriete prop = new Propriete(nom, estStatique,
          Modifier.isAbstract(constructeur.getModifiers()), visibilite);
      constructeurs.add(prop);
    }
    return constructeurs;
  }

  private List<Propriete> chercherMethodes(Class cls) {
    List<Propriete> methodes = new ArrayList<Propriete>();
    for (Method method : cls.getDeclaredMethods()) {
      Visibilite visibilite = chercherVisibilite(method.getModifiers());
      boolean estStatique = false;
      if (Modifier.isStatic(method.getModifiers()))
        estStatique = true;
      String nom = method.getName();
      String ex = chercherExceptions(method.getExceptionTypes());
      Parameter[] parameters = method.getParameters();

      nom += " (";
      nom += chercherParametres(parameters);
      nom += ") " + ex;

      Propriete prop = new Propriete(nom + " : " + method.getReturnType().getSimpleName(),
          estStatique, Modifier.isAbstract(method.getModifiers()), visibilite);
      methodes.add(prop);
    }
    return methodes;
  }

  private String chercherParametres(Parameter[] parameters) {
    String nom = "";
    for (Parameter param : parameters) {
      nom += param.getName() + " : " + param.getType().getSimpleName() + ", ";
    }
    if (parameters.length > 0)
      nom = nom.substring(0, nom.length() - 2);
    return nom;
  }

  private Visibilite chercherVisibilite(int modifier) {
    Visibilite visibilite = Visibilite.PACKAGE;
    if (Modifier.isPrivate(modifier))
      visibilite = visibilite.PRIVATE;
    if (Modifier.isPublic(modifier))
      visibilite = Visibilite.PUBLIC;
    if (Modifier.isProtected(modifier))
      visibilite = Visibilite.PROTECTED;
    return visibilite;
  }

  private String chercherExceptions(Class[] exceptions) {
    String ex = "";
    if (exceptions.length > 0)
      ex += " throws";
    for (Class class1 : exceptions) {
      ex += class1.getSimpleName() + ", ";
    }
    if (exceptions.length > 0)
      ex = ex.substring(0, ex.length() - 2);
    return ex;

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

  /*
   * Méthode utilitaire qui a analyse l'en-tête "content-disposition" et vérifie si le paramètre
   * "filename" y est présent. Si oui, alors le champ traité est de type File et la méthode retourne
   * son nom, sinon il s'agit d'un champ de formulaire classique et la méthode retourne null.
   */
  private static String getNomFichier(Part part) {
    // Boucle sur chacun des paramètres de l'en-tête "content-disposition"
    for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
      // Recherche du paramètre "filename"
      if (contentDisposition.trim().startsWith("filename")) {
        // return nom de fichier.
        return contentDisposition.substring(contentDisposition.indexOf('=') + 1);
      }
    }
    return null;
  }

  /*
   * Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre sur le disque, dans le
   * répertoire donné et avec le nom donné.
   */
  private void ecrireFichier(Part part, String nomFichier, String chemin) throws IOException {
    try (BufferedInputStream entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
        BufferedOutputStream sortie = new BufferedOutputStream(
            new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON)) {
      byte[] tampon = new byte[TAILLE_TAMPON];
      int longueur;
      while ((longueur = entree.read(tampon)) > 0) {
        sortie.write(tampon, 0, longueur);
      }
    }
  }
}
