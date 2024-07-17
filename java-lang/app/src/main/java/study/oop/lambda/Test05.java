package study.oop.lambda;

public class Test05 {

  static class MyCalculator {
    public static int plus(int a, int b) {return a + b;}
    public static int minus(int a, int b) {return a - b;}
    public static int multiple(int a, int b) {return a * b;}
    public static int divide(int a, int b) {return a / b;}
  }

  interface Calculator {
    int compute(int x, int y);
  }
  public static void main(String[] args) {

    //일반 클래스
    class My implements Calculator {
      @Override
      public int compute(int x, int y) {
        // TODO Auto-generated method stub
        return MyCalculator.plus(x, y);
      }
    }

    My obj = new My();
    int result = obj.compute(100, 200);
    System.out.println(result);

    //로컬 클래스
    Calculator c1 = new Calculator() {
      @Override
      public int compute(int x, int y) {
        // TODO Auto-generated method stub
        return MyCalculator.plus(x, y);
      }
    };
    System.out.println(c1.compute(100, 200));


    //로컬 클래스
    new Calculator() {
      @Override
      public int compute(int x, int y) {
        // TODO Auto-generated method stub
        return MyCalculator.plus(x, y);
      }
    }.compute(100, 200);

    //람다
    Calculator c2 = (int x,int y) -> {return MyCalculator.plus(x, y);};
    Calculator c3 = (x, y) -> {return MyCalculator.plus(x, y);};
    Calculator c4 = (x, y) -> MyCalculator.plus(x, y);

    Calculator c4 = MyCalculator::plus;

  }
}
