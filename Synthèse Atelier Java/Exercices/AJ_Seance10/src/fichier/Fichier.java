package fichier;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Fichier {
  private RandomAccessFile fichier;

  public Fichier() {
    try {
      fichier = new RandomAccessFile("fichier.dat", "rw");
      /*
       * if (fichier.length() == 0) { for (int i = 0; i < 100; i++) { fichier.writeByte(0); } }
       */
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public int set(int i, byte value) {
    try {
      fichier.seek(i);
      fichier.writeByte(value);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return i;
  }

  public byte get(int i) {
    try {
      fichier.seek(i);
      byte byt = fichier.readByte();
      return byt;
    } catch (IOException e) {
      System.exit(1);
      return 0;
    }
  }
}
