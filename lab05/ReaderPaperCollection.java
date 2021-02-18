public class ReaderPaperCollection extends Thread {
  private PaperCollection collection;

  public ReaderPaperCollection(PaperCollection col) {
    collection = col;
  }

  @Override
  public void run() {
    var arr = collection.getArray();
    for (int i = 0; i < arr.length; ++i) {
      System.out.println("Read: " + arr[i] + " from position " + i);
    }
  }
}
