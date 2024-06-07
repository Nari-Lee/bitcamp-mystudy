package study.lang.operator;

// 실습
// - 산술 연산자를 사용하여 연산을 수항핸 후 결과를 확인하라
// - (byte,char,short) --> int -> long --> float --. double
// 타입이 다르면 컴파일러는 다른 타입으로 자동변환 한다.(임시적으로 메모리에 복사)

public class Test03 {
  public static void main(String[] args) {

    byte b = 1;
    char c = 2;
    short s = 3;
    int i = 4;
    long l = 5;
    float f = 6.0f;
    double d = 7.0;

    int r = b + c + s;

    long r2 = i + l;

    // long r3 = f;
    float r4 = l; // 값이 짤릴 수 있다.
  }
}
