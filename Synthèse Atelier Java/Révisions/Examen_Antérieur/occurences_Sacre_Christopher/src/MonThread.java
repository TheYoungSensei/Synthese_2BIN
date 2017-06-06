import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MonThread implements Runnable {

  List<Future<Integer>> queue;
  Path file;
  String motif;

  public MonThread(List<Future<Integer>> queue, Path file, String motif) {
    super();
    synchronized (queue) {
      this.queue = queue;
    }
    this.file = file;
    this.motif = motif;
  }

  @Override
  public void run() {
    synchronized (queue) {
      ScruterRepertoire rep = new ScruterRepertoire(file, motif);
      FutureTask<Integer> future = new FutureTask<>(rep);
      future.run();
      queue.add(future);
    }
  }

}
