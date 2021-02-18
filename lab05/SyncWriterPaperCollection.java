public class SyncWriterPaperCollection implements Runnable {
  private PaperCollectionSynchronizer sync;

  public SyncWriterPaperCollection(PaperCollectionSynchronizer s) {
    sync = s;
  }

  @Override
  public void run() {
    try {
      sync.write();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
