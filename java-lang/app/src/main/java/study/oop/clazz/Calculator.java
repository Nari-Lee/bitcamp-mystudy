package study.oop.clazz;

public class Calculator {

  private int result = 0;

  public void plus(int a) {
    result += a;
  }

  public void minus(int a) {
    result -= a;
  }

  public void multiple(int a) {
    result *= a;
  }

  public  void divide(int a) {
    result /= a;
  }
  public int getResult() {
    return result;
  }
  public void clear() {
    result = 0;
  }
}
