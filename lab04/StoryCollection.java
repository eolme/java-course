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

public class StoryCollection implements PaperCollection {
  private static final long serialVersionUID = 3234407779241329483L;

  private String name;

  private byte revision;

  @Serial
  private List<String> stories;

  public StoryCollection() {
    name = "StoryCollection";
    revision = 0;
    stories = new ArrayList<String>(0);
  }

  public StoryCollection(String name, byte revision, List<String> stories)
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
  public byte getRevision() {
    return revision;
  }

  @Override
  public List<String> getList() {
    return stories;
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

      stories = list;
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
      for (String story : stories) {
        out.write('\0');
        out.write(story.getBytes());
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

      stories = list;
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
      for (String story : stories) {
        out.append(' ');
        out.append(story);
      }
    } catch (Exception exc) {
      throw new RuntimeException(exc);
    }
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
      result.append(p);
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
