package study.oop.clazz;

import study.oop.clazz.vo.Score;

public class Test02 {
  static Score[] scores = {
      new Score("홍길동", 100, 90, 85),
      new Score("임꺽정", 90, 80, 75),
      new Score("유관순", 80, 70, 65)
  };

  public static void main(String[] args) {
    printScore();
  }

  static void printScore() {
    for(Score score : scores) {
      System.out.printf("%s: %d, %d, %d, %d, %.1f\n",
          score.getName(), score.getKor(), score.getEng(), score.getMath(), score.getSum(), score.getAver());
    }
  }
}
