package study.oop.patterns.ex01.step3.copy;

public class K7Factory extends CarFactory {
  @Override
  public Sedan createCar() {
    Sedan s = new Sedan();
    s.maker = "기아자동차";
    s.model = "K7";
    s.cc = 2500;
    s.auto = true;
    s.sunloof = true;
    return s;
  }
}