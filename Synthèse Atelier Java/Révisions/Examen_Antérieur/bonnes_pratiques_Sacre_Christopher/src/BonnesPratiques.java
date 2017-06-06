import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

public class BonnesPratiques {

  private Class<?> classe;


  @Before
  public void setUp() {
    classe = Etudiant.class;
  }

  @Test
  public void testChamps() {
    for (Field field : classe.getDeclaredFields()) {
      int modifiers = field.getModifiers();
      if (Modifier.isPublic(modifiers)) {
        if (!Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers))
          fail("Attributs non private ne repondant pas aux conditions");
      }
    }
  }

  @Test
  public void testGetters() {
    for (Method method : classe.getDeclaredMethods()) {
      String methodName = method.getName();
      if (methodName.substring(0, 3).equals("get")) {
        if (method.getParameterTypes().length != 0)
          fail("Getter contenant des paramètres");
        String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4); // Test
                                                                                               // sur
                                                                                               // la
                                                                                               // syntaxe
                                                                                               // également
        System.out.println(fieldName);
        try {
          // Une autre solution serait de parcourir chaque field et de faire un equalsIgnoreCase :
          // moins performant ne teste pas les erreurs de syntaxe
          classe.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
          fail("Le getter ne contient pas de champs correspondant");
        }
      }
    }
  }

}

