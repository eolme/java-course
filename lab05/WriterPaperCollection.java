public class WriterPaperCollection extends Thread {
  private PaperCollection collection;

  public WriterPaperCollection(PaperCollection col) {
    collection = col;
  }

  @Override
  public void run() {
    var arr = collection.getArray();
    for (int i = 0; i < arr.length; ++i) {
      arr[i] = (int) (Math.random() * 100 + 1);
      System.out.println("Write: " + arr[i] + " from position " + i);
    }
  }
}
