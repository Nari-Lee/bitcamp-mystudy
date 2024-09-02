package bitcamp.myapp;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import java.io.File;


/**
 * packageName    : bitcamp.myapp
 * fileName       : ServerApp
 * author         : narilee
 * date           : 24. 8. 29.
 * description    : ServerApp 클래스는 내장형 Tomcat 서버를 설정하고 실행하느 메인 클래스입니다.
 *                  이 클래스는 웹 어플리케이션을 독립 실행형 서버로 구동하는데 사용됩니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 */
public class ServerApp {

  /**
   * 프로그램의 진입점입니다.
   * ServerApp 인스턴스를 생성하고 실행합니다.
   * 예외 발생시 오류 메시지를 출력하고 스택 트레이스를 표시합니다.
   *
   * @param args 명령줄 인자
   */
  public static void main(String[] args) {
    try {
      ServerApp app = new ServerApp();
      app.execute();
    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  /**
   * Tocat 서버를 설정하고 시작하는 메인 메서드입니다.
   * 포트, 인코딩, 웹 애플리케이션 컨텍스트 등을 구성합니다.
   *
   * @throws Exception 서버 설정 또는 시작 중 발생할 수 있는 예외
   */
  private void execute() throws Exception {
    System.out.println("서버 실행 중...");

    // Tomcat 서버 인스턴스를 생성
    Tomcat tomcat = new Tomcat();

    // 서버 포트 설정 (8080)
    tomcat.setPort(8080);

    // 임시 작업 디렉토리 설정
    tomcat.setBaseDir("temp");

    // HTTP 커넥터 설정 및 URI 인코딩을 UTF-8로 지정
    Connector connector = tomcat.getConnector();
    connector.setURIEncoding("UTF-8");

    // 웹 애플리케이션 컨텍스트 설정
    StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());
    // 런타임 중 클래스 변경 감지 및 리로드 활성화
    ctx.setReloadable(true);

    // 웹 리소스 설정
    WebResourceRoot resources = new StandardRoot(ctx);

    // 웹 애플리케이션의 서블릿 클래스 파일 위치 지정
    resources.addPreResources(new DirResourceSet(
        resources,
        "/WEB-INF/classes",
        new File("build/classes/java/main").getAbsolutePath(),
        "/"
    ));

    // 웹 애플리케이션 설정 정보를 웹 애플리케이션 환경 정보에 등록
    ctx.setResources(resources);

    // 톰캣 서버 구동
    tomcat.start();

    // 톰캣 서버를 구동한 후 종료될 때까지 JVM을 끝내지 말고 대기
    tomcat.getServer().await();

    System.out.println("서버 종료!");
  }
}
