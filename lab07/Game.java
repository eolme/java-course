class Game {
  private State state = State.Init;

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  private int min = 0;
  private int max = 0;
  private int current = 0;

  public int getCurrent() {
    return current;
  }

  public void setMin(int min) {
    this.min = min;
  }

  public void setMax(int max) {
    this.max = max;
  }

  public void next() {
    switch (state) {
      case Start:
        current = (min + max) / 2;

        state = State.Callback;
        return;
      case Less:
        if (current <= min) {
          state = State.Scam;
          return;
        }

        max = current - 1;

        current = (min + current) / 2;

        if (current > min) {
          current = current - 1;
        }

        if (current < min) {
          state = State.Scam;
          return;
        }

        state = State.Callback;
        return;
      case More:
        if (current >= max) {
          state = State.Scam;
          return;
        }

        min = current + 1;

        current = (max + current) / 2;

        if (current < max) {
          current = current + 1;
        }

        if (current > max) {
          state = State.Scam;
          return;
        }

        state = State.Callback;
        return;
      case Init:
      case Scam:
      case Win:
      case Callback:
      default:
        return;
    }
  }
}
