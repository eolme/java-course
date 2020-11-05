import FirstPackage.*;

class FirstClass {
  public static void main(String[] s) {
    var o = new SecondClass(0, 0);
    int i, j;
    for (i = 1; i <= 8; i++) {
      for (j = 1; j <= 8; j++) {
        o.setNum(i);
        o.setOrd(j);
        System.out.print(o.getGrid());
        System.out.print(" ");
      }
      System.out.println();
    }
  }
}
