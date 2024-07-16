package study.oop.lambda;

import study.oop.lambda.Test04.InterestCalculator;

public class Test {
  public static void main(String[] args) {
    InterestCalculator c1 = Factory.create(0.035);
    InterestCalculator c2 = Factory.create(0.015);

    System.out.printf("%.2f\n",c1.compute(1000));
    System.out.printf("%.2f\n",c2.compute(1000));
  }

}
