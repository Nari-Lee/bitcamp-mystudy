package study.lang.variable;

public class Test02 {
  public static void main(String[] args) {
    // 실습
    char c1 = 44032; // 변수에 '가' 문자의 코드를 정수 값으로 저장하라.
    char c2 = '\uac00'; // 변수에 '가' 문자의 코드를 \u0000 형태의 유니코드 표현법으로 저장하라.
    char c3 = '가'; // 변수에 '가' 문자의 코드를 가장 쉬운 방법으로 저장하라.
    char c4 = 0xac00;

    System.out.println(c1);
    System.out.println(c2);
    System.out.println(c3);
    System.out.println(c4);

  }
}


