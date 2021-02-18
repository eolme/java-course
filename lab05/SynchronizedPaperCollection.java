public class SynchronizedPaperCollection implements PaperCollection {
  private PaperCollection collection;

  public SynchronizedPaperCollection(PaperCollection col) {
    collection = col;
  }

  @Override
  public synchronized int[] getArray() {
    return collection.getArray();
  }
}
