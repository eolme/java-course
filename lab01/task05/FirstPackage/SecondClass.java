package FirstPackage;

public class SecondClass {
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
