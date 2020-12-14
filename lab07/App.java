import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.NumberFormatter;

import java.awt.FlowLayout;
import java.awt.Dimension;

class App {
  private static Map<String, String> PLaF;
  static {
    PLaF = new HashMap<>();

    PLaF.put("System", UIManager.getSystemLookAndFeelClassName());
    PLaF.put("Cross-platform", UIManager.getCrossPlatformLookAndFeelClassName());
  }

  private static void setPLaF(String clazz) {
    try {
      // JFrame.setDefaultLookAndFeelDecorated(true);
      UIManager.setLookAndFeel(clazz);
      SwingUtilities.updateComponentTreeUI(frame);
    } catch (Exception ex) {
      System.err.println("PLaF fail");
      ex.printStackTrace();
    }
  }

  private static JFrame frame;

  private static JPanel content;
  private static JPanel theme;

  private static void initGUI() {
    // Create window
    frame = new JFrame("More/Less");

    // Set exit on close
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set default PLaF
    setPLaF(PLaF.get("System"));

    // Disable manual resize
    frame.setResizable(false);

    // Default layout
    frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    frame.setSize(400, 200);

    theme = new JPanel();
    theme.setLayout(new BoxLayout(theme, BoxLayout.PAGE_AXIS));

    String currentValue = UIManager.getLookAndFeel().getClass().getName();
    ButtonGroup radioGroup = new ButtonGroup();
    PLaF.forEach((key, value) -> {
      JRadioButton radio = new JRadioButton(key);
      radio.setSelected(value.equals(currentValue));
      radio.addActionListener(e -> {
        setPLaF(value);
      });
      radioGroup.add(radio);
      theme.add(radio);
    });

    content = new JPanel();
    content.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    frame.add(content);
    frame.add(theme);

    // Show window
    frame.setVisible(true);
  }

  private static JPanel initScreenInit(Runnable callback) {
    JPanel screen = new JPanel();
    screen.setLayout(new BoxLayout(screen, BoxLayout.PAGE_AXIS));
    screen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format);
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(0);
    formatter.setMaximum(Integer.MAX_VALUE);

    JFormattedTextField minField = new JFormattedTextField(formatter);
    JFormattedTextField maxField = new JFormattedTextField(formatter);

    minField.setValue(0);
    maxField.setValue(0);

    minField.setColumns(10);
    maxField.setColumns(10);

    JPanel minGroup = new JPanel();
    JPanel maxGroup = new JPanel();

    minGroup.setLayout(new BoxLayout(minGroup, BoxLayout.LINE_AXIS));
    maxGroup.setLayout(new BoxLayout(maxGroup, BoxLayout.LINE_AXIS));

    minGroup.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    maxGroup.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

    JLabel minLabel = new JLabel("Min");
    JLabel maxLabel = new JLabel("Max");

    minLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    maxLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

    minLabel.setPreferredSize(new Dimension(40, 20));
    maxLabel.setPreferredSize(new Dimension(40, 20));

    minGroup.add(minLabel);
    minGroup.add(minField);

    maxGroup.add(maxLabel);
    maxGroup.add(maxField);

    JPanel labelGroup = new JPanel();
    labelGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    JLabel errorLabel = new JLabel();
    labelGroup.add(errorLabel);

    JPanel buttonGroup = new JPanel();
    buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    JButton button = new JButton("Start");
    button.addActionListener(e -> {
      Integer min = (Integer) minField.getValue();
      Integer max = (Integer) maxField.getValue();

      if (min == max) {
        errorLabel.setText("Min equals max");
        return;
      }

      if (min > max) {
        errorLabel.setText("Min greater then max");
        return;
      }

      game.setMin(min);
      game.setMax(max);

      game.setState(State.Start);

      callback.run();
    });
    buttonGroup.add(button);

    screen.add(minGroup);
    screen.add(maxGroup);

    screen.add(labelGroup);
    screen.add(buttonGroup);

    return screen;
  }

  private static JPanel initScreenPlay(Runnable callback) {
    JPanel screen = new JPanel();
    screen.setLayout(new BoxLayout(screen, BoxLayout.PAGE_AXIS));
    screen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel labelGroup = new JPanel();
    labelGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    JLabel currentLabel = new JLabel("Your number is " + game.getCurrent() + "?");
    labelGroup.add(currentLabel);

    JPanel buttonGroup = new JPanel();

    JButton buttonLess = new JButton("Less");
    buttonLess.addActionListener(e -> {
      game.setState(State.Less);

      callback.run();
    });
    buttonGroup.add(buttonLess);

    JButton buttonEquals = new JButton("Equals");
    buttonEquals.addActionListener(e -> {
      game.setState(State.Win);

      callback.run();
    });
    buttonGroup.add(buttonEquals);

    JButton buttonMore = new JButton("More");
    buttonMore.addActionListener(e -> {
      game.setState(State.More);

      callback.run();
    });
    buttonGroup.add(buttonMore);

    screen.add(labelGroup);
    screen.add(buttonGroup);

    return screen;
  }

  private static JPanel initScreenWin(Runnable callback) {
    JPanel screen = new JPanel();
    screen.setLayout(new BoxLayout(screen, BoxLayout.PAGE_AXIS));
    screen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel labelGroup = new JPanel();
    labelGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    JLabel label = new JLabel("Win!");
    labelGroup.add(label);

    JPanel buttonGroup = new JPanel();
    buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    JButton button = new JButton("New game");
    button.addActionListener(e -> {
      game.setState(State.Init);

      callback.run();
    });
    buttonGroup.add(button);

    screen.add(labelGroup);
    screen.add(buttonGroup);

    return screen;
  }

  private static JPanel initScreenScam(Runnable callback) {
    JPanel screen = new JPanel();
    screen.setLayout(new BoxLayout(screen, BoxLayout.PAGE_AXIS));
    screen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel labelGroup = new JPanel();
    labelGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    JLabel label = new JLabel("Scam!");
    labelGroup.add(label);

    JPanel buttonGroup = new JPanel();
    buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    JButton button = new JButton("New game");
    button.addActionListener(e -> {
      game.setState(State.Init);

      callback.run();
    });
    buttonGroup.add(button);

    screen.add(labelGroup);
    screen.add(buttonGroup);

    return screen;
  }

  private static Game game;

  private static void initGame() {
    game = new Game();
  }

  public static void main(String[] s) {
    // Wait for UI Thread
    SwingUtilities.invokeLater(() -> {
      initGame();
      initGUI();

      Flow flow = new Flow();
      flow.run();
    });
  }

  private static class Flow implements Runnable {
    @Override
    public void run() {
      game.next();

      switch (game.getState()) {
        case Init:
          content.removeAll();
          content.add(initScreenInit(this));
          content.getParent().revalidate();
          return;
        case Callback:
          content.removeAll();
          content.add(initScreenPlay(this));
          content.getParent().revalidate();
          return;
        case Win:
          content.removeAll();
          content.add(initScreenWin(this));
          content.getParent().revalidate();
          return;
        case Scam:
          content.removeAll();
          content.add(initScreenScam(this));
          content.getParent().revalidate();
          return;
        case Less:
        case More:
        default:
          this.run();
          return;
      }
    }
  }
}
