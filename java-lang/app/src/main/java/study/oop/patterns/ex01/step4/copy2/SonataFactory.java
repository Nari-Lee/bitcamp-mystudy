package study.oop.patterns.ex01.step4.copy2;

public class SonataFactory implements CarFactory {
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

