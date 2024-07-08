package study.oop.patterns.ex01.step3.copy;

public class SonataFactory extends CarFactory {
  @Override
  public Sedan createCar() {
    Sedan s = new Sedan();
    s.maker = "현대자동차";
    s.model = "소나타";
    s.cc = 1998;
    s.auto = false;
    s.sunloof = true;
    return s;
  }
}

