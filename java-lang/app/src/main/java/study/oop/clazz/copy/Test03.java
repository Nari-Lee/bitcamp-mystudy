package study.oop.clazz.copy;

public class Test03 {


  class B {} // non-static nested class
  static class C {} // static nested class
  Object obj1 = new Object() {}; //anonymous class


  void m1() {
    class D {}// local class

    Object obj2 = new Object() {}; //anonymous class
  }

}

//package member class
class A {}