import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PoetryCollection implements PaperCollection {
  private static final long serialVersionUID = 7227182806651695679L;

  private String name;

  private int revision;

  @Serial
  private List<String> poetries;

  public PoetryCollection() {
    name = "PoetryCollection";
    revision = 0;
    poetries = new ArrayList<String>(0);
  }

  public PoetryCollection(String name, int revision, List<String> poetries)
      throws PoetryCollection.PoetryCollectionException {

    if (Objects.isNull(name)) {
      throw new PoetryCollectionException("Name is null");
    }

    if (Objects.isNull(poetries)) {
      throw new PoetryCollectionException("Poetries is null");
    }

    this.name = name;
    this.revision = revision;
    this.poetries = poetries;
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
    return poetries;
  }

  @Override
  public String getItem(int index) {
    return poetries.get(index);
  }

  @Override
  public double averagePaperLength() {
    if (poetries.size() == 0) {
      throw new PoetryCollectionRuntimeException("Collection has no poetries");
    }

    int total = 0;
    for (String p : poetries) {
      total += p.length();
    }
    return total / ((double) poetries.size());
  }

  @Override
  public boolean equals(Object obj) {
    return Objects.nonNull(obj) && obj instanceof PoetryCollection && obj.hashCode() == hashCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, revision, poetries);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    String NL = System.getProperty("line.separator");

    result.append(this.getClass().getName() + " Object {" + NL);
    result.append("Name: " + name + NL);
    result.append("Revision: " + revision + NL);
    result.append("Poetries: [" + NL);
    for (String p : poetries) {
      result.append('"');
      result.append(p);
      result.append('"');
      result.append(NL);
    }
    result.append("]" + NL);
    result.append("}");

    return result.toString();
  }

  public class PoetryCollectionException extends Exception {
    private static final long serialVersionUID = 1L;

    public PoetryCollectionException(String message) {
      super(message);
    }
  }

  public class PoetryCollectionRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PoetryCollectionRuntimeException(String message) {
      super(message);
    }
  }

  @Override
  public Iterator<String> iterator() {
    return new Itr();
  }

  private class Itr implements Iterator<String> {
    int cursor;
    int lastRet = -1;

    Itr() {
    }

    @Override
    public boolean hasNext() {
      return cursor != PoetryCollection.this.poetries.size();
    }

    @Override
    public String next() {
      int i = cursor;
      if (i >= PoetryCollection.this.poetries.size()) {
        throw new NoSuchElementException();
      }
      cursor = i + 1;
      return PoetryCollection.this.poetries.get(lastRet = i);
    }

    @Override
    public void remove() {
      if (lastRet < 0) {
        throw new IllegalStateException();
      }
      PoetryCollection.this.poetries.remove(lastRet);
      cursor = lastRet;
      lastRet = -1;
    }
  }
}
