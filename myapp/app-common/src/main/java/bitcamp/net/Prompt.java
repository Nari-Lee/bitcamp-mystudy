package bitcamp.net;

import bitcamp.context.ApplicationContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : bitcamp.net
 * fileName       : Prompt
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 클라이언트와 서버 간의 통신을 관리하고 사용자 입력을 처리하는 클래스입니다.
 *                  이 클래스는 소켓 통신, 데이터 입출력 그리고 애플리케이션 컨텍스트 관리 기능을 제공합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
public class Prompt {

  /** 모든 클라이언트가 공유하는 애플리케이션 컨텍스트 */
  ApplicationContext ctx;

  /** 각 클라이언트 세션에 대한 속성 저장소 */
  private Map<String, Object> attributes = new HashMap<>();

  private Socket socket;
  private DataInputStream in;
  private DataOutputStream out;

  /** 서버의 출력을 임시 저장하는 StringWriter */
  private StringWriter strWriter = new StringWriter();

  /**
   * 문자열을 다양한 형식으로 출력할 있도록 도와주는 데코레이터
   * 이 객체를 사용하여 출력한 내용은 모두 위의 StringWriter에 보관됩니다.
   */
  private PrintWriter printWriter = new PrintWriter(strWriter);

  /**
   * Prompt 객체를 생성합니다.
   *
   * @param socket 클라이언트 소켓
   * @param ctx 애플리케이션 컨텍스트
   * @throws Exception 소켓 스트림 생성중 오류 발생 시
   */
  public Prompt(Socket socket, ApplicationContext ctx) throws Exception {
    this.socket = socket;
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
    this.ctx = ctx;
  }

  /**
   * 애플리케이션 컨텍스트를 반환합니다.
   *
   * @return 애플리케이션 컨텍스트 객체
   */
  public ApplicationContext getApplicationContext() {
    return this.ctx;
  }

  /**
   * 클라이언트 세션에 속성을 설정합니다.
   *
   * @param name 속성 이름
   * @param value 속성 값
   */
  public void setAttribute(String name, Object value) {
    attributes.put(name, value);
  }

  /**
   * 클라이언트 세션에서 속성을 조회합니다.
   *
   * @param name 속성 이름
   * @return 속성 값
   */
  public Object getAttribute(String name) {
    return attributes.get(name);
  }

  /**
   * 사용자로부터 문자열을 입력받습니다.
   *
   * @param format 프롬프트 형식
   * @param args 형식에 맞는 인자들
   * @return 시용자 입력 문자열
   * @throws Exception 입력 처리중 오류 발생시
   */
  public String input(String format, Object... args) throws Exception {
    String promptTitle = String.format(format + " ", args);
    print(promptTitle);
    end();

    return in.readUTF();
  }

  /**
   * 사용자로부터 정수 입력을 받습니다.
   *
   * @param format 프롬프트 형식
   * @param args 형식에 맞는 인자들
   * @return 사용자 입력 정수
   * @throws Exception 입력 처리중 오류 발생시
   */
  public int inputInt(String format, Object... args) throws Exception {
    return Integer.parseInt(input(format, args));
  }

  /**
   * 사용자로부터 날짜 입력을 받습니다.
   *
   * @param format 프롬프트 형식
   * @param args 형식에 맞는 인자들
   * @return 사용자 입력 날짜
   * @throws Exception 입력 처리중 오류 발생시
   */
  public Date inputDate(String format, Object... args) throws Exception {
    return Date.valueOf(input(format, args));
  }

  /**
   * 문자열을 출력 버퍼에 추가합니다.
   *
   * @param str 출력할 문자열
   */
  public void print(String str) {
    printWriter.print(str);
  }

  /**
   * 형식화된 문자열을 출력 버퍼에 추가합니다.
   *
   * @param format 문자열 형식
   * @param args 형식에 맞는 인자들
   */
  public void printf(String format, Object... args) {
    printWriter.printf(format, args);
  }

  /**
   * 문자열을 출력 버퍼에 추가하고 줄바꿈합니다.
   *
   * @param str 출력할 문자열
   */
  public void println(String str) {
    printWriter.println(str);
  }

  /**
   * 버퍼의 내용을 클라이언트로 전송하고 버퍼를 초기화합니다.
   *
   * @throws Exception 출력 처리중 오류 발생시
   */
  public void end() throws Exception {
    // 현재까지 출력한 내용을 꺼낸다.
    String message = strWriter.toString();

    // 클라이언트로 전송한다.
    out.writeUTF(message);
    out.flush();

    // StringWriter의 버퍼를 초기화한다.
    strWriter.getBuffer().setLength(0);
  }

  /**
   * 모든 리소스를 해제합니다
   */
  public void close() {
    try {
      in.close();
    } catch (Exception e) {
    }
    try {
      out.close();
    } catch (Exception e) {
    }
    try {
      socket.close();
    } catch (Exception e) {
    }
  }
}
