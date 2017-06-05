public class Tree {
  
  private int value;
  
  private Tree parent;
  
  private Tree[] children;
  
  //*******************************************************
  //CONSTRUCTEURS
  //*******************************************************
  public Tree(int v, Tree... chd) {
    value = v;
    children = chd;
   
    int i = 0;
    while(i != chd.length) {
      chd[i].parent = this;
      i++;
    }
  }
  
  //*******************************************************
  //GETTERS
  //*******************************************************

  public int getValue() {
    return value;
  }

  public Tree getParent() {
    return parent;
  }

  public Tree[] getChildren() {
    return children;
  }
}
