// Cette classe représente une partition de l'intervalle [0..n[.
public class UnionFind {
  // Attributs (à compléter) (cf slide 8)
  int[][] partition;
  private int taille;

  // Hypothèse: n >= 0
  // Instancie la partition initiale de l'intervalle [0..n[,
  // cad la relation identité (cf slide 4).
  public UnionFind(int n) {
    partition = new int[n][2];
    for (int i = 0; i < n; i++) {
      partition[i][0] = 1;
      partition[i][1] = i;
    }
    taille = n;
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.
  // Renvoie le représentant de la partie de e (cf slide 10).
  private int root(int e) {
    int courant = e;
    while (courant != partition[courant][1]) {
      courant = partition[courant][1];
    }
    return courant;
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.
  // 1) Compresse la partie de e, et
  // 2) renvoie le représentant de la partie de e (cf slide 12).
  public int find(int e) {
    int representant = root(e);
    int courant = e;
    int nombreSupp = partition[e][0];
    while (courant != partition[courant][1]) {
      partition[courant][1] = representant;
      courant = partition[courant][1];
      if (courant != e && courant != representant) {
        partition[courant][0] -= nombreSupp;
        nombreSupp++;
      }
    }
    return representant;
  }

  // Hypothèse: e appartient à l'intervalle [0..n[.
  // 1) Compresse la partie de e, et
  // 2) renvoie la taille de la partie de e (cf slide 13).
  public int cardinal(int e) {
    find(e);
    return partition[e][0];
  }

  // Renvoie le nbr de parties de la partition représentée par this.
  public int cardinal() {
    return taille;
  }

  // Hypothèse: e1 et e2 appartiennent à l'intervalle [0..n[.
  // 1) Compresse les parties de e1 et e2, et
  // 2) renvoie la valeur "true" ssi
  // e1 et e2 appartiennent à la meme partie (cf slide 14).
  public boolean equals(int e1, int e2) {
    return find(e1) == find(e2);
  }

  // Hypothèse: e1 et e2 appartiennent à l'intervalle [0..n[.
  // 1) Compresse les parties de e1 et e2, et
  // 2) fussione les parties de e1 et e2 (cf slide 15).
  public void union(int e1, int e2) {
    int representant1 = find(e1);
    int representant2 = find(e2);
    if (cardinal(e1) >= cardinal(e2)) {
      partition[representant2][1] = representant1;
      partition[representant1][0] += partition[representant2][0];
    } else {
      partition[representant1][1] = representant2;
      partition[representant2][0] += partition[representant1][0];
    }
    taille--;
  }

  public void affichage() {
    for (int i = 0; i < partition.length; i++) {
      int representant = partition[i][1];
      if (representant >= 10 && representant < 100) {
        System.out.print("|" + partition[i][0] + " |");
      } else if (representant >= 100 && representant < 1000) {
        System.out.print("| " + partition[i][0] + " |");
      } else if (representant >= 1000) {
        System.out.print("| " + partition[i][0] + "  |");
      } else {
        System.out.print("|" + partition[i][0] + "|");
      }
    }
    System.out.println();
    for (int i = 0; i < partition.length; i++) {
      System.out.print("|" + partition[i][1] + "|");
    }
    System.out.println();
  }

}
