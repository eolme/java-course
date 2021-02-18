import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;

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
    try {
      out.write(inst.getName().getBytes());
      out.write('\0');
      out.write(ByteBuffer.allocate(4).putInt(inst.getRevision()).array());
      out.write('\0');
      for (String str : inst.getList()) {
        out.write(str.getBytes());
        out.write('\0');
      }
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  public static PaperCollection input(InputStream in) {
    try {
      var arr = new ByteArrayOutputStream();
      var list = new LinkedList<String>();

      int tmp;
      while ((tmp = in.read()) != -1) {
        if (tmp != '\0') {
          arr.write(tmp);
        } else {
          break;
        }
      }

      var name = arr.toString();
      arr.reset();

      // read 4 byte
      var revision = ByteBuffer
          .wrap(new byte[] { (byte) in.read(), (byte) in.read(), (byte) in.read(), (byte) in.read() }).getInt();

      // flush separator
      in.read();

      while ((tmp = in.read()) != -1) {
        if (tmp != '\0') {
          arr.write(tmp);
        } else {
          list.add(arr.toString());
          arr.reset();
        }
      }

      if (arr.size() != 0) {
        list.add(arr.toString());
      }

      return createInstance(name, revision, list);
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  public static void write(PaperCollection inst, Writer out) {
    try {
      out.append(inst.getName());
      out.append(' ');
      out.append(String.valueOf(inst.getRevision()));
      for (String str : inst.getList()) {
        out.append(' ');
        out.append(str);
      }
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  public static PaperCollection read(Reader in) {
    try {
      var buf = new BufferedReader(in);
      var arr = new StringBuilder();
      var list = new LinkedList<String>();

      int tmp;
      while ((tmp = buf.read()) != -1) {
        if (tmp != ' ') {
          arr.append((char) tmp);
        } else {
          break;
        }
      }

      var name = arr.toString();
      arr.setLength(0);

      while ((tmp = buf.read()) != -1) {
        if (tmp != ' ') {
          arr.append((char) tmp);
        } else {
          break;
        }
      }

      var revision = Integer.parseInt(arr.toString());
      arr.setLength(0);

      while ((tmp = buf.read()) != -1) {
        if (tmp != ' ') {
          arr.append((char) tmp);
        } else {
          list.push(arr.toString());
          arr.setLength(0);
        }
      }

      if (arr.length() != 0) {
        list.push(arr.toString());
      }

      return createInstance(name, revision, list);
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  public static void serialize(PaperCollection o, OutputStream out) throws IOException {
    new ObjectOutputStream(out).writeObject(o);
  }

  public static PaperCollection deserialize(InputStream in) throws IOException, ClassNotFoundException {
    return (PaperCollection) new ObjectInputStream(in).readObject();
  }
}
