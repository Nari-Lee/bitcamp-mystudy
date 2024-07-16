package study.oop.nested;

public class Test01 {

  interface Printer {
    void print();
  }

  public static void main(String[] args) {


    Printer obj;
    obj = new Printer() {
      @Override
      public void print() {
        System.out.println("Hello");
      }
    };
  }
}

//new Printer() {
//  @Override
//  public void print() {
//    System.out.println("Hello!");
//  }
//}.print():

// Printer obj = new Printer() {
//  @Override
// void print() {
//    System.out.println("Hello!");
//   }
// };

// new Printer() {
//  @Override
//  public void print() {
//    System.out.println("Hello!");
//  }
//  }.print();
//}
//}

//Printer obj;
//obj = () -> System.out.println("Hello!");};

// () -> System.out.println("Hello!");};