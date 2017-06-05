public class Main {
  public static void main(String[] args) {
    Tree l1 = new Tree(1);
    Tree l3 = new Tree(3);
    Tree l7 = new Tree(7);

    Tree t2 = new Tree(2, l1, l3);
    Tree t6 = new Tree(6, l7);

    Tree t4 = new Tree(4, t2, t6);

    Tree yolo = new Tree(5);

    System.out.println("Exemple 1 -> #feuilles = " + Trees.nbrLeaves(t4));

    Tree[] ls = Trees.flattenLeaves(t4);
    System.out.println("Exemple 2 -> Les " + ls.length + " feuilles");
    int i = 0;
    while (i != ls.length) {
      System.out.println(ls[i].getValue());
      i++;
    }

    System.out.println("Exemple 1 -> Path V1");
    Trees.pathV1(l7);

    System.out.println("Exemple 2 -> Path V2");
    Trees.pathV2(l7);

    System.out.println("Exercice 1.1 -> Nombre Noeuds");
    System.out.println(Trees.nbrNodes(t4));
    System.out.println("Exercice 1.2 -> Minimum");
    System.out.println(Trees.min(t4));
    System.out.println("Exercice 1.3 -> Somme");
    System.out.println(Trees.sum(t4));
    System.out.println("Exercice 1.4 -> Equals");
    System.out.println(Trees.equals(t4, t4));
    System.out.println(Trees.equals(t4, t6));
    System.out.println("Exercice 1.5 -> Depth");
    System.out.println(Trees.depth(t4));
    System.out.println(Trees.depth(t4.getChildren()[0].getChildren()[0]));
    System.out.println("Exercice 1.6 -> SameOne");
    System.out.println(Trees.sameOne(t6, t6));
    System.out.println(Trees.sameOne(t4, t6));
    System.out.println(Trees.sameOne(yolo, l3));
    System.out.println("Exercice 1.7 -> dfsPrint");
    Trees.dfsPrint(t4);
    System.out.println("Exercice 1.8 -> bfsPrint");
    Trees.bfsPrint(t4);
    System.out.println("Exercice 2.1 -> findPathV1");
    Trees.printPathV1(l3);
    System.out.println("Exercice 2.2 -> findPathV2");
    Trees.printPathV2(l3);
    System.out.println("Exercice 2.3 -> findPathV3");
    Trees.printPathV3(t4, 7);
    System.out.println("Exercice 3.1 -> toArray");
    int[][] array = Trees.toArray(t4);
    for (int[] ligne : array) {
      for (int colonne : ligne) {
        System.out.print(colonne);
      }
      System.out.print("\n");
    }
    System.out.println("Exercice 3.2 -> toTree");
    System.out.println(Trees.sameOne(t4, Trees.toTree(Trees.toArray(t4))));
    System.out.println("Exercice 4 -> Ancestor");
    System.out.println(Trees.lca(l3, l3).getValue());
  }
}
