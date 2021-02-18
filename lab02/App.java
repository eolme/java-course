import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Input vector length:");
    int length = Integer.parseInt(reader.readLine());

    var vector = new Vector(length);

    System.out.println("Input values line by line:");
    for (int i = 0; i < length; ++i) {
      vector.set(i, Double.parseDouble(reader.readLine()));
    }

    System.out.println("Min: " + vector.getMin());
    System.out.println("Max: " + vector.getMax());
    System.out.println("Norm: " + vector.norm());

    System.out.println("Before sort:");
    System.out.println(vector);
    vector.sort();
    System.out.println("After sort:");
    System.out.println(vector);

    System.out.println("Multiple by:");
    vector.multiply(Double.parseDouble(reader.readLine()));
    System.out.println("After multiple:");
    System.out.println(vector);

    System.out.println("Sum by self:");
    vector.sum(vector);
    System.out.println(vector);

    System.out.println("Scalar by self:");
    vector.scalar(vector);
    System.out.println(vector);
  }
}
