package gimme.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
      String nomClasse = nomFichier.substring(0, nomFichier.length() - 6);
      MyClassLoader cl = new MyClassLoader();
      String fi = chemin + nomClasse + ".class";
      Comparator<Propriete> comparator = new Comparator<Propriete>() {

        @Override
        public int compare(Propriete o1, Propriete o2) {
          return o1.getNom().compareTo(o2.getNom());
        }

      };
      Class<?> cls = cl.findClass(fi);
      Propriete laClasse =
          new Propriete(cls.getSimpleName(), Modifier.isAbstract(cls.getModifiers()));
      List<Propriete> attributs = new ArrayList<Propriete>();
      for (Field field : cls.getDeclaredFields()) {
        Visibilite vis;
        if (Modifier.isPrivate(field.getModifiers())) {
          vis = Visibilite.PRIVATE;
        } else if (Modifier.isPublic(field.getModifiers())) {
          vis = Visibilite.PUBLIC;
        } else if (Modifier.isProtected(field.getModifiers())) {
          vis = Visibilite.PROTECTED;
        } else {
          vis = Visibilite.PACKAGE;
        }
        attributs.add(new Propriete(field.getName() + " : " + field.getType().getSimpleName(),
            Modifier.isStatic(field.getModifiers()), vis));
      }
      attributs.sort(comparator);
      List<Propriete> constructeurs = new ArrayList<Propriete>();
      for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
        Visibilite vis;
        if (Modifier.isPrivate(constructor.getModifiers())) {
          vis = Visibilite.PRIVATE;
        } else if (Modifier.isPublic(constructor.getModifiers())) {
          vis = Visibilite.PUBLIC;
        } else if (Modifier.isProtected(constructor.getModifiers())) {
          vis = Visibilite.PROTECTED;
        } else {
          vis = Visibilite.PACKAGE;
        }
        String params = "";
        for (Parameter param : constructor.getParameters()) {
          params += param.getName() + " : " + param.getType().getSimpleName() + " ";
        }
        constructeurs.add(new Propriete(constructor.getName() + "(" + params + ")",
            Modifier.isStatic(constructor.getModifiers()),
            Modifier.isAbstract(constructor.getModifiers()), vis));
      }
      Set<String> already = new HashSet<String>();

      List<Propriete> getters = new ArrayList<Propriete>();
      for (Method method : cls.getDeclaredMethods()) {
        if (!isGetter(method))
          continue;
        Visibilite vis;
        if (Modifier.isPrivate(method.getModifiers())) {
          vis = Visibilite.PRIVATE;
        } else if (Modifier.isPublic(method.getModifiers())) {
          vis = Visibilite.PUBLIC;
        } else if (Modifier.isProtected(method.getModifiers())) {
          vis = Visibilite.PROTECTED;
        } else {
          vis = Visibilite.PACKAGE;
        }
        String params = "(";
        for (Parameter param : method.getParameters()) {
          params += param.getName() + " : " + param.getType().getSimpleName() + " ";
        }
        params += ")";
        String exceptions = "";
        for (AnnotatedType type : method.getAnnotatedExceptionTypes()) {
          if (exceptions.length() == 0)
            exceptions = " throws ";
          exceptions += type.getType().getTypeName();
        }
        if (already.contains(method.getName() + params))
          continue;
        getters.add(new Propriete(
            method.getName() + " " + params + exceptions + " : "
                + method.getReturnType().getSimpleName(),
            Modifier.isStatic(method.getModifiers()), Modifier.isAbstract(method.getModifiers()),
            vis));
        already.add(method.getName() + params);
      }
      getters.sort(comparator);

      List<Propriete> setters = new ArrayList<Propriete>();
      for (Method method : cls.getDeclaredMethods()) {
        if (!isSetter(method))
          continue;
        Visibilite vis;
        if (Modifier.isPrivate(method.getModifiers())) {
          vis = Visibilite.PRIVATE;
        } else if (Modifier.isPublic(method.getModifiers())) {
          vis = Visibilite.PUBLIC;
        } else if (Modifier.isProtected(method.getModifiers())) {
          vis = Visibilite.PROTECTED;
        } else {
          vis = Visibilite.PACKAGE;
        }
        String params = "(";
        for (Parameter param : method.getParameters()) {
          params += param.getName() + " : " + param.getType().getSimpleName() + " ";
        }
        params += ")";
        if (already.contains(method.getName() + params))
          continue;
        String exceptions = "";
        for (AnnotatedType type : method.getAnnotatedExceptionTypes()) {
          if (exceptions.length() == 0)
            exceptions = " throws ";
          exceptions += type.getType().toString();
        }
        setters.add(new Propriete(
            method.getName() + " " + params + exceptions + " : "
                + method.getReturnType().getSimpleName(),
            Modifier.isStatic(method.getModifiers()), Modifier.isAbstract(method.getModifiers()),
            vis));
        already.add(method.getName() + params);
      }
      setters.sort(comparator);

      List<Propriete> methodes = new ArrayList<Propriete>();
      for (Method method : cls.getDeclaredMethods()) {
        Visibilite vis;
        if (Modifier.isPrivate(method.getModifiers())) {
          vis = Visibilite.PRIVATE;
        } else if (Modifier.isPublic(method.getModifiers())) {
          vis = Visibilite.PUBLIC;
        } else if (Modifier.isProtected(method.getModifiers())) {
          vis = Visibilite.PROTECTED;
        } else {
          vis = Visibilite.PACKAGE;
        }
        String params = "(";
        for (Parameter param : method.getParameters()) {
          params += param.getName() + " : " + param.getType().getSimpleName() + " ";
        }
        params += ")";
        String exceptions = "";
        for (AnnotatedType type : method.getAnnotatedExceptionTypes()) {
          if (exceptions.length() == 0)
            exceptions = " throws ";
          exceptions += type.getType().toString();
        }
        if (already.contains(method.getName() + params))
          continue;
        methodes.add(new Propriete(
            method.getName() + " " + params + exceptions + " : "
                + method.getReturnType().getSimpleName(),
            Modifier.isStatic(method.getModifiers()), Modifier.isAbstract(method.getModifiers()),
            vis));
        already.add(method.getName() + params);
      }



      request.setAttribute("nom", cls.getName());
      request.setAttribute("identite", laClasse);
      request.setAttribute("attributs", attributs);
      request.setAttribute("constructeurs", constructeurs);
      request.setAttribute("getters", getters);
      request.setAttribute("setters", setters);
      request.setAttribute("methodes", methodes);
      RequestDispatcher rd = request.getServletContext().getNamedDispatcher("Index");
      rd.forward(request, response);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return;
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

  public static boolean isGetter(Method method) {
    if (method.getParameterTypes().length == 0) {
      if (method.getName().matches("^get[A-Z].*") && !method.getReturnType().equals(void.class))
        return true;
      if (method.getName().matches("^is[A-Z].*") && method.getReturnType().equals(boolean.class))
        return true;
    }
    return false;
  }

  public static boolean isSetter(Method method) {
    return method.getReturnType().equals(void.class) && method.getParameterTypes().length == 1
        && method.getName().matches("^set[A-Z].*");
  }
}
