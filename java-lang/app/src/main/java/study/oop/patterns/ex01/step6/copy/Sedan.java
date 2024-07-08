package study.oop.patterns.ex01.step6.copy;

public class Sedan extends Car {
  boolean sunloof;
  boolean auto;


  @Override
  public String toString() {
    return "Sedan [sunloof=" + sunloof + ", auto=" + auto + ", maker=" + maker + ", model=" + model
        + ", cc=" + cc + "]";
  }

  @Override
  protected void start() {
    System.out.printf("%s 시동 건다!!\n", this.model);
    // TODO Auto-generated method stub
  }

  @Override
  protected void run() {
    // TODO Auto-generated method stub
    System.out.printf("%s %s 달린다!\n",this.model, this.sunloof ? "썬루프 열고" : "썬루프 닫고" );
  }

  @Override
  protected void stop() {
    // TODO Auto-generated method stub
    System.out.printf("%s 시동 끈다!\n", this.model);
  }

  protected Sedan() {}
}
