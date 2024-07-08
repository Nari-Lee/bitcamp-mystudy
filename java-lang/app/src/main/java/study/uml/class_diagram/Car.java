package study.uml.class_diagram;

public class Car {

  Engine engine;
  Insurance incurance;
  Navigation navigation;


  public Car(Engine engine) {
    this.engine = engine;
  }

  public Navigation getNavigation() {
    return navigation;
  }

  public void setNavigation(Navigation navigation) {
    this.navigation = navigation;
  }

  public Insurance getIncurance() {
    return incurance;
  }

  public void setIncurance(Insurance incurance) {
    this.incurance = incurance;
  }

  public Engine getEngine() {
    return engine;
  }

  public void fuelUp(GasStation gasStation) {
    gasStation.inject();
  }
}
