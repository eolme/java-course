public class PaperCollectionManipulation {
  public static PaperCollection synchronizedPaperCollection(PaperCollection collection) {
    return new SynchronizedPaperCollection(collection);
  }
}
