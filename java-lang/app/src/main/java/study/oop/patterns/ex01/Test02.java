package study.oop.patterns.ex01;

import study.oop.patterns.ex01.step2.Sedan;

public class Test02 {
  public static void main(String[] args) {
    Sedan sonata = Sedan.create("소나타");
    Sedan K7 = Sedan.create("K7");

    System.out.println(sonata);
    System.out.println(K7);

  }
}
