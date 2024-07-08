package study.oop.patterns.ex01.step2.copy2;

public class Sedan extends Car {
  boolean sunloof;
  boolean auto;


  @Override
  public String toString() {
    return "Sedan [sunloof=" + sunloof + ", auto=" + auto + ", maker=" + maker + ", model=" + model
        + ", cc=" + cc + "]";
  }

  private Sedan() {}

  public static Sedan create(String model) {

    Sedan s = new Sedan();

    switch (model) {
      case "소나타" :
        s.maker = "현대자동차";
        s.model = "소나타";
        s.cc = 1998;
        s.auto = false;
        s.sunloof = true;
        break;
      case "K7":
        s.maker = "기아자동차";
        s.model = "K7";
        s.cc = 2500;
        s.auto = true;
        s.sunloof = true;
    }
    return s;
  }

}
