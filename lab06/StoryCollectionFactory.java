import java.util.List;

public class StoryCollectionFactory implements PaperCollectionFactory {
  @Override
  public PaperCollection createInstance() {
    return new StoryCollection();
  }

  @Override
  public PaperCollection createInstance(String name, int revision, List<String> list)
      throws StoryCollection.StoryCollectionException {
    return new StoryCollection(name, revision, list);
  }
}
