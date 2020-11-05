public class Vector {
  private double[] arr;

  public Vector(int length) {
    arr = new double[length];
  }

  public double get(int index) {
    return arr[index];
  }

  public int getLength() {
    return arr.length;
  }

  public double getMin() {
    var min = arr[0];

    for (var item : arr) {
      if (item < min) {
        min = item;
      }
    }

    return min;
  }

  public double getMax() {
    var max = arr[0];

    for (var item : arr) {
      if (item > max) {
        max = item;
      }
    }

    return max;
  }

  public void sort() {
    for (var i = 0; i < arr.length - 1; ++i) {
      for (var j = 0; j < arr.length - i - 1; ++j) {
        if (arr[j] > arr[j + 1]) {
          var temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
  }

  public double norm() {
    var norm = 0;
    for (var item : arr) {
      norm += item * item;
    }
    return Math.sqrt(norm);
  }

  public void multiply(double k) {
    for (var i = 0; i < arr.length; ++i) {
      arr[i] *= k;
    }
  }

  public void sum(Vector v) {
    for (var i = 0; i < arr.length; ++i) {
      arr[i] += v.get(i);
    }
  }

  public void scalar(Vector v) {
    for (var i = 0; i < arr.length; ++i) {
      arr[i] *= v.get(i);
    }
  }
}
