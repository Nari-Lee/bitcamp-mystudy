package study.oop.patterns.ex01.step3.copy2;

public class Sedan extends Car {
  boolean sunloof;
  boolean auto;


  @Override
  public String toString() {
    return "Sedan [sunloof=" + sunloof + ", auto=" + auto + ", maker=" + maker + ", model=" + model
        + ", cc=" + cc + "]";
  }

  protected Sedan() {}
}
