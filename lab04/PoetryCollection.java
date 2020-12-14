import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serial;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PoetryCollection implements PaperCollection {
  private static final long serialVersionUID = 7227182806651695679L;

  private String name;

  private byte revision;

  @Serial
  private List<String> poetries;

  public PoetryCollection() {
    name = "PoetryCollection";
    revision = 0;
    poetries = new ArrayList<String>(0);
  }

  public PoetryCollection(String name, byte revision, List<String> poetries)
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
  public byte getRevision() {
    return revision;
  }

  @Override
  public List<String> getList() {
    return poetries;
  }

  @Override
  public void input(InputStream in) {
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

      name = arr.toString();
      arr.reset();

      revision = (byte) in.read();

      while ((tmp = in.read()) != -1) {
        if (tmp != '\0') {
          arr.write(tmp);
        } else {
          list.push(arr.toString());
          arr.reset();
        }
      }

      poetries = list;
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  @Override
  public void output(OutputStream out) {
    try {
      out.write(name.getBytes());
      out.write('\0');
      out.write(revision);
      for (String poetry : poetries) {
        out.write('\0');
        out.write(poetry.getBytes());
      }
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  @Override
  public void read(Reader in) {
    try {
      var buf = new BufferedReader(in);
      var arr = new StringBuilder();
      var list = new LinkedList<String>();

      int tmp;
      while ((tmp = buf.read()) != -1) {
        if (tmp != ' ') {
          arr.append(tmp);
        } else {
          break;
        }
      }

      name = arr.toString();
      arr.setLength(0);

      while ((tmp = buf.read()) != -1) {
        if (tmp != ' ') {
          arr.append(tmp);
        } else {
          break;
        }
      }

      revision = (byte) Byte.parseByte(arr.toString());
      arr.setLength(0);

      while ((tmp = buf.read()) != -1) {
        if (tmp != '\0') {
          arr.append(tmp);
        } else {
          list.push(arr.toString());
          arr.setLength(0);
        }
      }

      poetries = list;
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
  }

  @Override
  public void write(Writer out) {
    try {
      out.append(name);
      out.append(' ');
      out.append(String.valueOf(revision));
      for (String poetry : poetries) {
        out.append(' ');
        out.append(poetry);
      }
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
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
      result.append(p);
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
}