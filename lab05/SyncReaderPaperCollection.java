public class SyncReaderPaperCollection implements Runnable {
  private PaperCollectionSynchronizer sync;

  public SyncReaderPaperCollection(PaperCollectionSynchronizer s) {
    sync = s;
  }

  @Override
  public void run() {
    try {
      sync.read();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
