import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;


public class Huffman {

  private static class Node implements Comparable<Node> {
    private char ch;
    private int freq;
    private final Node left, right;

    public Node(char ch, int freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
    }

    public boolean isLeaf() {
      return left == null && right == null;
    }

    @Override
    public int compareTo(Node that) {
      return this.freq - that.freq;
    }
  }

  // renvoie une map qui a comme clé les lettres de la chaine de
  // caractère donnée en paramètre et comme valeur la fréquence de
  // ces lettres
  public static Map<Character, Integer> computeFreq(String s) {
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (map.containsKey(c)) {
        map.put(c, map.get(c) + 1);
      } else {
        map.put(c, 1);
      }
    }
    return map;
  }

  // renvoie l'arbre de Huffman obtenu à partir de la map des fréquences des lettres
  public static Node buildTree(Map<Character, Integer> freq) {
    PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
      @Override
      public int compare(Node o1, Node o2) {
        return o1.freq - o2.freq;
      }
    });
    for (Entry<Character, Integer> entry : freq.entrySet()) {
      queue.add(new Node(entry.getKey(), entry.getValue(), null, null));
    }
    while (queue.size() != 1) {
      Node filsGauche = queue.poll();
      Node filsDroit = queue.poll();
      Node parent = new Node('\u0000', filsGauche.freq + filsDroit.freq, filsGauche, filsDroit);
      queue.add(parent);
    }
    return queue.poll();
  }

  // renvoie une map qui associe chaque lettre à son code (suite de 0 et de 1).
  // Ce code est obtenu en parcourant l'arbre de Huffman donné en paramètre
  public static Map<Character, String> buildCode(Node root) {
    Map<Character, String> map = new HashMap<Character, String>();
    buildCodeAuxi(root, "", map);
    return map;
  }

  private static void buildCodeAuxi(Node root, String path, Map<Character, String> map) {
    if (root.isLeaf()) {
      map.put(root.ch, path);
    } else {
      buildCodeAuxi(root.left, path + "0", map);
      buildCodeAuxi(root.right, path + "1", map);
    }

  }


  // encode la chaine de caractère prise en paramètre en une chaine de
  // bit (0 et 1) en utilisant la map des codes codeMap
  public static String compress(String s, Map<Character, String> codeMap) {
    StringBuilder stringB = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      stringB.append(codeMap.get(s.charAt(i)));
    }
    return stringB.toString();
  }

  // Cette méthode décode une chaine de 0 et de 1 codé à l'aide de l'algorithme de Huffman
  // En paramètre, en plus de la chaine à décoder, est spécifié la racine de l'arbre de
  // Huffman
  public static String expand(Node root, String t) {
    StringBuilder decoded = new StringBuilder();
    Node current = root;
    int position = 0;
    while (position < t.length()) {
      if (current.isLeaf()) {
        decoded.append(current.ch);
        current = root;
      }
      char c = t.charAt(position);
      if (c == '0') {
        current = current.left;
        position++;
      } else {
        current = current.right;
        position++;
      }
    }
    if (root != current)
      decoded.append(current.ch); // To encode last carac
    return decoded.toString();
  }

  public static void main(String[] args) throws IOException {
    String s = HuffmanReadFile.loadFile(new File("11-0.txt"));
    Map<Character, Integer> freq = computeFreq(s);
    Node root = buildTree(freq);
    Map<Character, String> code = buildCode(root);
    String compress = compress(s, code);
    HuffmanWriteFile.write("fichier_compresse", compress);
    String texteOriginal = expand(root, HuffmanReadFile.read("fichier_compresse"));
    System.out.println(texteOriginal);
  }


}
