import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
  private static Scanner in = new Scanner(System.in);
  private static LinkedList<PaperCollection> collections = new LinkedList<PaperCollection>();

  private static String EXIT = "exit";

  private static String MAIN_MENU = "1=AppendStoryCollection 2=AppendPoetryCollection 3=Process 0=Exit";

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

  private static void appendStoryCollection() {
    try {
      System.out.println(APPEND_STORY_COLLECTION);

      String name;
      byte revision;
      List<String> stories = new LinkedList<String>();

      String line = in.nextLine();

      System.out.println("line \"" + line + "\"");

      if (!EXIT.equals(line)) {
        name = line;
      } else {
        System.out.println("Aborted");
        return;
      }

      line = in.nextLine();
      if (!EXIT.equals(line)) {
        revision = Byte.parseByte(line);
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
    } catch (Exception exc) {
      exc.printStackTrace();
      System.out.println("Aborted");
      return;
    }
  }

  private static String APPEND_POETRY_COLLECTION = "Name Revision Poetry line by line | type 'exit'";

  private static void appendPoetryCollection() {
    try {
      System.out.println(APPEND_POETRY_COLLECTION);

      String name;
      byte revision;
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
        revision = Byte.parseByte(line);
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
    } catch (Exception exc) {
      exc.printStackTrace();
      System.out.println("Aborted");
      return;
    }
  }

  private static void process() {
    try {
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
    } catch (Exception exc) {
      exc.printStackTrace();
      return;
    }
  }

  public static void main(String[] args) {
    menu();
  }
}
