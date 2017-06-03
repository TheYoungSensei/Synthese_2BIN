package fichier;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.LinkedBlockingQueue;

public class FichierBis {
  private RandomAccessFile fichier;
  private LinkedBlockingQueue<Message> messages;

  public FichierBis() {
    try {
      fichier = new RandomAccessFile("fichier.dat", "rw");
      if (fichier.length() == 0) {
        for (int i = 0; i < 100; i++) {
          fichier.writeByte(0);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    messages = new LinkedBlockingQueue<Message>();
    new TraiterMessageThread().start();
  }

  private class TraiterMessageThread extends Thread {
    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          Message m = messages.take();
          if (m.typeMessage == TypeMessage.SET) {
            setEffectif(m.position, m.valeur);
          } else {
            m.setResponse(getEffectif(m.position));
          }
        } catch (InterruptedException e) {
          throw new RuntimeException();
        }
      }
    }
  }

  public void set(int i, byte value) {
    messages.add(new Message(i, value));
  }

  private void setEffectif(int i, byte value) {
    try {
      fichier.seek(i);
      fichier.write(value);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public byte get(int i) {
    Message m = new Message(i);
    messages.add(m);
    return m.getResponse();
  }

  private byte getEffectif(int i) {
    try {
      fichier.seek(i);
      return fichier.readByte();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
      return 0;
    }
  }

  public enum TypeMessage {
    GET, SET;
  }

  private class Message {
    private TypeMessage typeMessage;
    private int position;
    private byte valeur;
    private LinkedBlockingQueue<Byte> response = new LinkedBlockingQueue<Byte>(1);

    public Message(int position, byte valeur) {
      this.position = position;
      this.valeur = valeur;
      this.typeMessage = TypeMessage.SET;
    }

    public Message(int position) {
      this.position = position;
      this.typeMessage = TypeMessage.GET;
    }

    public void setResponse(byte value) {
      try {
        response.put(value);
      } catch (InterruptedException e) {
        throw new RuntimeException();
      }
    }

    public byte getResponse() {
      try {
        return response.take();
      } catch (InterruptedException e) {
        throw new RuntimeException();
      }
    }
  }
}
