package study.oop.patterns.ex01;

import study.oop.patterns.ex01.step1.Sedan;

public class Test01 {
  public static void main(String[] args) {
    Sedan s1 = new Sedan();
    s1.maker = "현대자동차";
    s1.model = "소나타";
    s1.cc = 1998;
    s1.auto = false;
    s1.sunloof = true;

    Sedan s2 = new Sedan();
    s2.maker = "기아자동차";
    s2.model = "K7";
    s2.cc = 2500;
    s2.auto = true;
    s2.sunloof = true;

    System.out.println(s1);
    System.out.println(s2);
  }
}
