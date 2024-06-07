package study.lang.operator;

// 실습
// - 산술 연산자를 사용하여 연산을 수항핸 후 결과를 확인하라

// - 연산자 우선순위
// 0) ()
// 1) *, /, %
// 2) +, -

public class Test04 {
  public static void main(String[] args) {

    System.out.println(3 + 5 * 2);
    System.out.println(5 * 2 + 3);

    System.out.println((3 + 5) * 2);
    System.out.println(5 * (2 + 3));

    // 암시적 형변환 + 연산자 우선순위
    System.out.println(3.2f + 5 / 2L);
    // 5를 long으로 암시적 변환후 5/2를 계산하고 floa로 암시적 변환후 계산이 단계적으로 진행

    // 명시적 형변환 + 연산자 우선순위
    System.out.println(3.2f + (float) 5 / 2L);
  }
}
