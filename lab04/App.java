import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.Reader;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
  private static Scanner in = new Scanner(System.in);
  private static LinkedList<PaperCollection> collections = new LinkedList<PaperCollection>();

  private static String EXIT = "exit";

  private static String MAIN_MENU = "1=AppendStoryCollection 2=AppendPoetryCollection 3=Process 4=Test 0=Exit";

  private static void menu() {
    char ch;
    do {
      try {
        System.out.println(MAIN_MENU);
        ch = in.nextLine().charAt(0);

        switch (ch) {
          case '1': {
            appendStoryCollection();
            break;
          }
          case '2': {
            appendPoetryCollection();
            break;
          }
          case '3': {
            process();
            break;
          }
          case '4': {
            test();
            break;
          }
          case '0': {
            System.out.println("Done");
            System.exit(0);
            return;
          }
        }
      } catch (Exception exc) {
        exc.printStackTrace();
        System.exit(1);
        return;
      }
    } while (ch != '0');
  }

  private static String APPEND_STORY_COLLECTION = "Name Revision Story line by line | type 'exit'";

  private static void appendStoryCollection() throws StoryCollection.StoryCollectionException {
    System.out.println(APPEND_STORY_COLLECTION);

    String name;
    int revision;
    List<String> stories = new LinkedList<String>();

    String line = in.nextLine();

    if (!EXIT.equals(line)) {
      name = line;
    } else {
      System.out.println("Aborted");
      return;
    }

    line = in.nextLine();
    if (!EXIT.equals(line)) {
      revision = Integer.parseInt(line);
    } else {
      System.out.println("Aborted");
      return;
    }

    line = in.nextLine();
    while (!EXIT.equals(line)) {
      stories.add(line);
      line = in.nextLine();
    }

    collections.add(new StoryCollection(name, revision, stories));
    System.out.println("Added");
    return;
  }

  private static String APPEND_POETRY_COLLECTION = "Name Revision Poetry line by line | type 'exit'";

  private static void appendPoetryCollection() throws PoetryCollection.PoetryCollectionException {
    System.out.println(APPEND_POETRY_COLLECTION);

    String name;
    int revision;
    List<String> poetries = new LinkedList<String>();

    String line = in.nextLine();
    if (!EXIT.equals(line)) {
      name = line;
    } else {
      System.out.println("Aborted");
      return;
    }

    line = in.nextLine();
    if (!EXIT.equals(line)) {
      revision = Integer.parseInt(line);
    } else {
      System.out.println("Aborted");
      return;
    }

    line = in.nextLine();
    while (!EXIT.equals(line)) {
      poetries.add(line);
      line = in.nextLine();
    }

    collections.add(new PoetryCollection(name, revision, poetries));
    System.out.println("Added");
    return;
  }

  private static void process() {
    for (PaperCollection col : collections) {
      System.out.println(col);
    }

    var map = new HashMap<Double, LinkedList<PaperCollection>>();
    Double key;
    for (PaperCollection col : collections) {
      key = col.averagePaperLength();
      if (map.containsKey(key)) {
        map.get(key).add(col);
      } else {
        var list = new LinkedList<PaperCollection>();
        list.add(col);
        map.put(key, list);
      }
    }

    var storyCollections = new LinkedList<StoryCollection>();
    var poetryCollections = new LinkedList<PoetryCollection>();
    for (PaperCollection col : collections) {
      if (col instanceof StoryCollection) {
        storyCollections.add((StoryCollection) col);
        continue;
      }

      if (col instanceof PoetryCollection) {
        poetryCollections.add((PoetryCollection) col);
        continue;
      }
    }

    System.out.println("Processed");
  }

  private static void test() throws IOException, ClassNotFoundException {
    if (collections.isEmpty()) {
      throw new RuntimeException("Collection is empty");
    }

    PaperCollection fromInst;
    PaperCollection toInst;

    OutputStream outputStream;
    InputStream inputStream;

    Writer writer;
    Reader reader;

    fromInst = collections.get(0);

    // test read/write
    System.out.println("read/write");
    writer = new CharArrayWriter();
    PaperCollectionManipulation.write(fromInst, writer);
    reader = new CharArrayReader(((CharArrayWriter) writer).toCharArray());
    toInst = PaperCollectionManipulation.read(reader);
    System.out.println(fromInst.equals(toInst));

    // test input/output
    System.out.println("input/output");
    outputStream = new ByteArrayOutputStream();
    PaperCollectionManipulation.output(fromInst, outputStream);
    inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
    toInst = PaperCollectionManipulation.input(inputStream);
    System.out.println(fromInst.equals(toInst));

    // test serialize/deserialize
    System.out.println("serialize/deserialize");
    outputStream = new ByteArrayOutputStream();
    PaperCollectionManipulation.serialize(fromInst, outputStream);
    inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
    toInst = PaperCollectionManipulation.deserialize(inputStream);
    System.out.println(fromInst.equals(toInst));

    System.out.println("Tested");
  }

  public static void main(String[] args) {
    menu();
  }
}
