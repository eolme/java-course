import java.util.List;

public class PoetryCollectionFactory implements PaperCollectionFactory {
  @Override
  public PaperCollection createInstance() {
    return new PoetryCollection();
  }

  @Override
  public PaperCollection createInstance(String name, int revision, List<String> list)
      throws PoetryCollection.PoetryCollectionException {
    return new PoetryCollection(name, revision, list);
  }
}
