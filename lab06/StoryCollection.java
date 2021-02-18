import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serial;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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

      // read 4 byte
      revision = ByteBuffer.wrap(new byte[] { (byte) in.read(), (byte) in.read(), (byte) in.read(), (byte) in.read() })
          .getInt();

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
      out.write(ByteBuffer.allocate(4).putInt(revision).array());
      out.write('\0');
      for (String story : stories) {
        out.write(story.getBytes());
        out.write('\0');
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
          arr.append((char) tmp);
        } else {
          break;
        }
      }

      name = arr.toString();
      arr.setLength(0);

      while ((tmp = buf.read()) != -1) {
        if (tmp != ' ') {
          arr.append((char) tmp);
        } else {
          break;
        }
      }

      revision = Integer.parseInt(arr.toString());
      arr.setLength(0);

      while ((tmp = buf.read()) != -1) {
        if (tmp != ' ') {
          arr.append((char) tmp);
        } else {
          list.add(arr.toString());
          arr.setLength(0);
        }
      }

      if (arr.length() != 0) {
        list.add(arr.toString());
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
      return cursor != StoryCollection.this.stories.size();
    }

    @Override
    public String next() {
      int i = cursor;
      if (i >= StoryCollection.this.stories.size()) {
        throw new NoSuchElementException();
      }
      cursor = i + 1;
      return StoryCollection.this.stories.get(lastRet = i);
    }

    @Override
    public void remove() {
      if (lastRet < 0) {
        throw new IllegalStateException();
      }
      StoryCollection.this.stories.remove(lastRet);
      cursor = lastRet;
      lastRet = -1;
    }
  }
}
