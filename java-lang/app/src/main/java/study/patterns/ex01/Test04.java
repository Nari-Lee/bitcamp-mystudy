package study.patterns.ex01;

import study.oop.patterns.ex01.step4.Car;
import study.oop.patterns.ex01.step4.CarFactory;
import study.oop.patterns.ex01.step4.K7Factory;
import study.oop.patterns.ex01.step4.SonataFactory;

public class Test04 {
  public static void main(String[] args) {

    SonataFactory sonataFactory = new SonataFactory();
    K7Factory k7Factory = new K7Factory();

    printCar(sonataFactory);
    printCar(k7Factory);
  }

  static void printCar(CarFactory carFactory) {
    Car car = carFactory.createCar();
    System.out.println(car);
  }
}
