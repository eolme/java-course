import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class PaperCollectionManipulation<T extends PaperCollection> {
  public void output(T o, OutputStream out) {
    o.output(out);
  }

  public T input(InputStream in, Class<T> clazz) {
    try {
      T inst = clazz.getDeclaredConstructor().newInstance();
      inst.input(in);
      return inst;
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  public void write(T o, Writer out) {
    o.write(out);
  }

  public T read(Reader in, Class<T> clazz) {
    try {
      T inst = clazz.getDeclaredConstructor().newInstance();
      inst.read(in);
      return inst;
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  public void serialize(T o, OutputStream out) {
    try {
      new ObjectOutputStream(out).writeObject(this);
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  @SuppressWarnings("unchecked")
  public T deserialize(InputStream in) {
    try {
      return (T) new ObjectInputStream(in).readObject();
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }
}
