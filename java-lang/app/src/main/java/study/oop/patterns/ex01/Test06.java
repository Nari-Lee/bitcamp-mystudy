package study.oop.patterns.ex01;

import study.oop.patterns.ex01.step6.Car;
import study.oop.patterns.ex01.step6.CarFactory;
import study.oop.patterns.ex01.step6.K7Factory;
import study.oop.patterns.ex01.step6.SonataFactory;

public class Test06 {
  public static void main(String[] args) {

    SonataFactory sonataFactory = SonataFactory.getInstance();
    K7Factory k7Factory = K7Factory.getInstance();

    play(sonataFactory);
    play(k7Factory);
  }

  static void play(CarFactory carFactory) {
    Car car = carFactory.createCar();
    car.play();
  }
}
