import java.util.Scanner;

public class App {
  private static Scanner in = new Scanner(System.in);

  private static String MAIN_MENU = "1=Process1 2=Process2 3=Process3 0=Exit";

  private static void menu() {
    char ch;
    do {
      try {
        System.out.println(MAIN_MENU);
        ch = in.nextLine().charAt(0);

        switch (ch) {
          case '1': {
            process1();
            break;
          }
          case '2': {
            process2();
            break;
          }
          case '3': {
            process3();
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

  private static void process1() throws InterruptedException {
    var shared = new StepPaperCollection();
    var writer = new WriterPaperCollection(shared);
    var reader = new ReaderPaperCollection(shared);

    writer.setPriority(Thread.MAX_PRIORITY);
    writer.start();

    reader.setPriority(Thread.MIN_PRIORITY);
    reader.start();
  }

  private static void process2() {
    var shared = new StepPaperCollection();
    var sync = new PaperCollectionSynchronizer(shared);

    var writer = new SyncWriterPaperCollection(sync);
    var reader = new SyncReaderPaperCollection(sync);

    var writerThread = new Thread(writer);
    var readerThread = new Thread(reader);

    writerThread.start();
    readerThread.start();
  }

  private static void process3() {
    var shared = new StepPaperCollection();
    var sync = PaperCollectionManipulation.synchronizedPaperCollection(shared);

    var setOneThread = new Thread(() -> {
      var arr = sync.getArray();
      arr[0] = 1;
      System.out.println("setOneThread");
    });

    var setTwoThread = new Thread(() -> {
      var arr = sync.getArray();
      arr[0] = 2;
      System.out.println("setTwoThread");
    });

    var readThread = new Thread(() -> {
      var arr = sync.getArray();
      System.out.println("Read: " + arr[0]);
    });

    setOneThread.start();
    readThread.start();
    setTwoThread.start();
  }

  public static void main(String[] args) {
    menu();
  }
}
