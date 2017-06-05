import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trees {
  // *******************************************************
  // Un premier exemple: le nombre de feuilles d'un arbre
  // *******************************************************
  public static int nbrLeaves(Tree t) {
    int r;
    if (t.getChildren().length == 0) {
      r = 1;
    } else {
      r = 0;
      int i = 0;
      while (i != t.getChildren().length) {
        r += nbrLeaves(t.getChildren()[i]);
        i++;
      }
    }
    return r;
  }

  // *******************************************************
  // Un deuxième exemple: aplanir un arbre
  // *******************************************************
  public static Tree[] flattenLeaves(Tree t) {
    int nl = nbrLeaves(t);
    Tree[] r = new Tree[nl];
    flattenLeaves(t, r, 0);
    return r;
  }

  private static int flattenLeaves(Tree t, Tree[] a, int idx) {
    int r;
    if (t.getChildren().length == 0) {
      a[idx] = t;
      r = 1;
    } else {
      r = 0;
      int i = 0;
      while (i != t.getChildren().length) {
        r += flattenLeaves(t.getChildren()[i], a, idx + r);
        i++;
      }
    }
    return r;
  }

  // *******************************************************
  // Un troisième exemple:
  // tous les algorithmes ne sont pas récursifs
  // *******************************************************
  public static void pathV1(Tree t) {
    System.out.println(t.getValue());
    if (t.getParent() != null) {
      pathV1(t.getParent());
    }
  }

  public static void pathV2(Tree t) {
    while (t != null) {
      System.out.println(t.getValue());
      t = t.getParent();
    }
  }

  // *******************************************************
  // Exercices 1
  // *******************************************************

  // 1.1)
  public static int nbrNodes(Tree t) {
    int compteur = 0;
    for (Tree tree : t.getChildren()) {
      compteur += nbrNodes(tree);
    }
    return ++compteur;
  }

  // 1.2)
  public static int min(Tree t) {
    int min = t.getValue();
    for (Tree tree : t.getChildren()) {
      min = Math.min(min(tree), min);
    }
    return min;
  }

  // 1.3)
  public static int sum(Tree t) {
    int sum = t.getValue();
    for (Tree tree : t.getChildren()) {
      sum += sum(tree);
    }
    return sum;
  }

  // 1.4)
  public static boolean equals(Tree t1, Tree t2) {
    if (t1.getValue() != t2.getValue()) {
      return false;
    }
    if (t1.getChildren().length != t2.getChildren().length) {
      return false;
    }
    for (int i = 0; i < t1.getChildren().length; i++) {
      equals(t1.getChildren()[i], t2.getChildren()[i]);
    }
    return true;
  }

  // 1.5)
  public static int depth(Tree n) {
    if (n.getParent() == null)
      return 0;
    return 1 + depth(n.getParent());
  }

  // 1.6)
  public static boolean sameOne(Tree n1, Tree n2) {
    if (n1.getParent() == null && n2.getParent() == null)
      return equals(n1, n2);
    if (n1.getParent() == null && n2.getParent() != null)
      return sameOne(n1, n2.getParent());
    if (n1.getParent() != null && n2.getParent() == null)
      return sameOne(n1.getParent(), n2);
    return sameOne(n1.getParent(), n2.getParent());

  }

  // 1.7)
  public static void dfsPrint(Tree t) {
    if (Trees.nbrNodes(t) == 0)
      System.out.println("Arbre Vide");
    System.out.println(t.getValue());
    for (Tree tree : t.getChildren()) {
      dfsPrint(tree);
    }
  }

  // 1.8)
  public static void bfsPrint(Tree t) {
    if (Trees.nbrNodes(t) == 0)
      System.out.println("Arbre Vide");
    List<Tree> coucheCourante = new ArrayList<Tree>();
    coucheCourante.add(t);
    while (!coucheCourante.isEmpty()) {
      List<Tree> coucheEnfant = new ArrayList<Tree>();
      for (Tree tree : coucheCourante) {
        System.out.println(tree.getValue());
        for (Tree treeEnfant : tree.getChildren()) {
          coucheEnfant.add(treeEnfant);
        }
      }
      coucheCourante = coucheEnfant;
    }
  }

  // *******************************************************
  // Exercices 2
  // *******************************************************

  // 2.1)
  public static void printPathV1(Tree n) {
    if (n.getParent() != null) {
      printPathV1(n.getParent());
    }
    System.out.println(n.getValue());
  }

  // 2.2)
  public static void printPathV2(Tree n) {
    ArrayList<Tree> pile = new ArrayList<Tree>();
    Tree courant = n;
    while (courant != null) {
      pile.add(courant);
      courant = courant.getParent();
    }
    while (!pile.isEmpty()) {
      System.out.println(pile.remove(pile.size() - 1).getValue());
    }
  }

  // 2.3
  public static void printPathV3(Tree t, int v) {
    if (t.getValue() == v)
      printPathV1(t);
    for (Tree tree : t.getChildren()) {
      printPathV3(tree, v);
    }
  }

  // *******************************************************
  // Exercices 3
  // *******************************************************

  private static int index;

  // 3.1
  public static int[][] toArray(Tree t, int[][] array) {
    if (array == null) {
      array = new int[Trees.nbrNodes(t)][];
      index = 0;
    }
    array[index] = new int[3];
    array[index][0] = t.getValue();
    if (t.getChildren().length != 0) {
      array[index][1] = index + 1;
      if (t.getChildren().length > 1) {
        array[index][2] = index + nbrNodes(t.getChildren()[0]) + 1;
      } else {
        array[index][2] = index;
      }
    } else {
      array[index][1] = index;
      array[index][2] = index;
    }
    for (Tree tree : t.getChildren()) {
      index++;
      toArray(tree, array);
    }
    return array;
  }


  // 3.2
  public static Tree toTree(int[][] t) {
    return toTreePrivate(t, 0);
  }

  private static Tree toTreePrivate(int[][] t, int index) {
    Tree tree;
    if (t[index][1] != index) {
      Tree enfant1 = toTreePrivate(t, t[index][1]);
      Tree enfant2 = null;
      if (t[index][2] != index) {
        enfant2 = toTreePrivate(t, t[index][2]);
        tree = new Tree(t[index][0], enfant1, enfant2);
      } else {
        tree = new Tree(t[index][0], enfant1);
      }
    } else {
      tree = new Tree(t[index][0]);
    }
    return tree;
  }

  // *******************************************************
  // Exercices 4
  // *******************************************************

  public static Tree lca(Tree n1, Tree n2) {
    Set<Tree> comparaison = new HashSet<Tree>();
    Tree courant = n2;
    while (courant != null) {
      comparaison.add(courant);
      courant = courant.getParent();
    }
    courant = n1;
    while (courant != null) {
      if (comparaison.contains(courant))
        return courant;
      courant = courant.getParent();
    }
    return null;
  }
}
