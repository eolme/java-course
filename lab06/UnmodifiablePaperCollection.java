import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
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
  public double averagePaperLength() {
    return collection.averagePaperLength();
  }

  @Override
  public void input(InputStream in) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void output(OutputStream out) {
    collection.output(out);
  }

  @Override
  public void read(Reader in) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void write(Writer out) {
    collection.write(out);
  }
}
