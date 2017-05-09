public class MainUF {
  public static void main(String[] args) {
    UnionFind uf = new UnionFind(1000);
    uf.union(0, 1);
    uf.union(0, 2);
    uf.union(0, 3);
    uf.union(3, 5);
    uf.union(2, 4);
    uf.affichage();
    System.out.println(uf.equals(2, 4));
    System.out.println(uf.equals(2, 6));
  }
}
