import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UnmodifiablePaperCollection implements PaperCollection {
  private static final long serialVersionUID = -3653157800591389397L;

  private PaperCollection collection;

  UnmodifiablePaperCollection(PaperCollection collection) {
    this.collection = collection;
  }

  @Override
  public Iterator<String> iterator() {
    return collection.iterator();
  }

  @Override
  public String getName() {
    return collection.getName();
  }

  @Override
  public int getRevision() {
    return collection.getRevision();
  }

  @Override
  public List<String> getList() {
    return Collections.unmodifiableList(collection.getList());
  }

  @Override
  public String getItem(int index) {
    return collection.getItem(index);
  }

  @Override
  public double averagePaperLength() {
    return collection.averagePaperLength();
  }
}
