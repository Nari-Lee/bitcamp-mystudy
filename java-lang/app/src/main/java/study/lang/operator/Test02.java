package study.lang.operator;

// 실습
// - 산술 연산자를 사용하여 연산을 수항핸 후 결과를 확인하라

public class Test02 {
  public static void main(String[] args) {
    byte b1 = 100;
    byte b2 = 20;
    byte b3 = (byte) (b1 + b2);

    // 자바에서는 byte, short, char는 산술후 자동으로 int로 변경하므로 b1 + b2가 int값이 되어 실행된다.
    // 같은 타입끼리만 연산할 수 있다.
    // byte b3 = b1 + b2;는 사용할 수 없다.
    System.out.println(b3);

    /*
     * char c = 20; short s = 30; short r2 = c + s; 컴파일 에러
     */

    int i1 = 21_0000_0000;
    int i2 = 21_0000_0000;
    int i3 = i1 + i2;
    System.out.println(i3); // -94967296

    long r3 = i1 + i2;
    System.out.println(r3); // -94967296 int인 i1 + i2를 계산하면 int로 오버플로우로 나온 수가 long에 들어간다.

    long r4 = (long) i1 + i2; // 덧셈 전에 int를 long으로 변환
    System.out.println(r4);

  }
}
