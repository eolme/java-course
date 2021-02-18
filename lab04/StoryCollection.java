import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoryCollection implements PaperCollection {
  private static final long serialVersionUID = 3234407779241329483L;

  private String name;

  private int revision;

  @Serial
  private List<String> stories;

  public StoryCollection() {
    name = "StoryCollection";
    revision = 0;
    stories = new ArrayList<String>(0);
  }

  public StoryCollection(String name, int revision, List<String> stories)
      throws StoryCollection.StoryCollectionException {

    if (Objects.isNull(name)) {
      throw new StoryCollectionException("Name is null");
    }

    if (Objects.isNull(stories)) {
      throw new StoryCollectionException("stories is null");
    }

    this.name = name;
    this.revision = revision;
    this.stories = stories;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getRevision() {
    return revision;
  }

  @Override
  public List<String> getList() {
    return stories;
  }

  @Override
  public String getItem(int index) {
    return stories.get(index);
  }

  @Override
  public double averagePaperLength() {
    if (stories.size() == 0) {
      throw new StoryCollectionRuntimeException("Collection has no stories");
    }

    int total = 0;
    for (String p : stories) {
      total += p.length();
    }
    return total / ((double) stories.size());
  }

  @Override
  public boolean equals(Object obj) {
    return Objects.nonNull(obj) && obj instanceof StoryCollection && obj.hashCode() == hashCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, revision, stories);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    String NL = System.getProperty("line.separator");

    result.append(this.getClass().getName() + " Object {" + NL);
    result.append("Name: " + name + NL);
    result.append("Revision: " + revision + NL);
    result.append("Stories: [" + NL);
    for (String p : stories) {
      result.append('"');
      result.append(p);
      result.append('"');
      result.append(NL);
    }
    result.append("]" + NL);
    result.append("}");

    return result.toString();
  }

  public class StoryCollectionException extends Exception {
    private static final long serialVersionUID = 1L;

    public StoryCollectionException(String message) {
      super(message);
    }
  }

  public class StoryCollectionRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StoryCollectionRuntimeException(String message) {
      super(message);
    }
  }
}
