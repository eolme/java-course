public class PaperCollectionSynchronizer {
  private PaperCollection collection;
  private volatile int current = -1;

  private volatile boolean sem = false;
  private volatile boolean isEnd;

  public PaperCollectionSynchronizer(PaperCollection col) {
    collection = col;
    isEnd = col.getArray().length == 0;
  }

  public void read() throws InterruptedException {
    while (!isEnd) {
      synchronized (collection) {
        while (!isEnd && !sem) {
          collection.wait();
        }

        if (!isEnd) {
          var arr = collection.getArray();
          System.out.println("Read: " + arr[current] + " from position " + current);
          sem = false;
        }

        collection.notifyAll();
      }
    }

    System.out.println("End read");
  }

  public void write() throws InterruptedException {
    while (!isEnd) {
      synchronized (collection) {
        while (!isEnd && sem) {
          collection.wait();
        }

        current++;

        var arr = collection.getArray();
        isEnd = current >= arr.length;

        if (!isEnd) {
          arr[current] = (int) (Math.random() * 100 + 1);
          System.out.println("Write: " + arr[current] + " to position " + current);
          sem = true;
        }

        collection.notifyAll();
      }
    }

    System.out.println("End write");
  }
}
