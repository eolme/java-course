import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public class PaperCollectionManipulation {
  private static PaperCollectionFactory factory = new StoryCollectionFactory();

  public static void setFactory(PaperCollectionFactory fac) {
    factory = fac;
  }

  public static PaperCollection createInstance() {
    return factory.createInstance();
  }

  public static PaperCollection createInstance(String name, int revision, List<String> list) throws Exception {
    return factory.createInstance(name, revision, list);
  }

  public static void output(PaperCollection inst, OutputStream out) {
    inst.output(out);
  }

  public static PaperCollection input(InputStream in, PaperCollection inst) {
    inst.input(in);
    return inst;
  }

  public static void write(PaperCollection inst, Writer out) {
    inst.write(out);
  }

  public static PaperCollection read(Reader in, PaperCollection inst) {
    inst.read(in);
    return inst;
  }

  public static void serialize(PaperCollection o, OutputStream out) throws IOException {
    new ObjectOutputStream(out).writeObject(o);
  }

  public static PaperCollection deserialize(InputStream in) throws IOException, ClassNotFoundException {
    return (PaperCollection) new ObjectInputStream(in).readObject();
  }
}
