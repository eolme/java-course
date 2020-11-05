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

class SecondClass {
  private int num;
  private int ord;

  public int getNum() {
    return num;
  }

  public int getOrd() {
    return ord;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public void setOrd(int ord) {
    this.ord = ord;
  }

  public int getGrid() {
    return this.num * this.ord;
  }

  public SecondClass(int num, int ord) {
    this.num = num;
    this.ord = ord;
  }
}
