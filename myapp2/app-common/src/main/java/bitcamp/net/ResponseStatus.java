package bitcamp.net;

/**
 * packageName    : bitcamp.net
 * fileName       : ResponseStatus
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 응답 상태를 나타내는 상수들을 정의하는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
public class ResponseStatus {

  /**
   * 작업이 성공적으로 완료되었음을 나타냅니다.
   */
  public static final String SUCCESS = "success";
  /**
   * 작업이 실패했음을 나타냅니다.
   */
  public static final String FAILURE = "failure";
  /**
   * 작업 중 오류가 발생했음을 나타냅니다.
   */
  public static final String ERROR = "error";
}
