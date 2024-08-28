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
 * date           : 24. 8. 27.
 * description    : 내장 Tomcat 서버를 설정하고 실행하는 클래스입니다.
 *                  이 클래스는 웹 애플리케이션을 위한 서버 환경을 구성하고 실행합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
public class ServerApp {

  /**
   * 애플리케이션의 main 메서드입니다.
   * ServerApp의 인스턴스를 생성하고 excute를 호출합니다.
   *
   * @param args
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
   * 내장 Tomcat 서버를 설정하고 실행합니다.
   * 이 메서드는 다음과 같은 작업을 수행합니다:
   * <ul>
   *   <li>Tomcat 서버 인스턴스를 생성하고 기본 설정을 합니다.</li>
   *   <li>서버의 포트를 8888로 설정합니다.</li>
   *   <li>임시 디렉토리를 'temp'로 설정합니다.</li>
   *   <li>URI 인코딩을 UTF-8로 설정합니다.</li>
   *   <li>웹 애플리케이션 컨텍스트를 설정하고 정적 및 동적 리소스 위치를 지정합니다.</li>
   *   <li>서버를 시작하고 종료 신호를 기다립니다.</li>
   * </ul>
   *
   * @throws Exception
   */
  private void execute() throws Exception {
    System.out.println("서버 실행 중...");

    // 톰캣 서버를 구동시키는 객체 준비
    Tomcat tomcat = new Tomcat();

    // 서버의 포트 번호 설정
    tomcat.setPort(8888);

    // 톰캣 서버를 실행하는 동안 사용할 임시 폴더 지정
    tomcat.setBaseDir("temp");

    // 톰캣 서버의 연결 정보를 설정
    Connector connector = tomcat.getConnector();
    connector.setURIEncoding("UTF-8");

    // 톰캣 서버에 배포할 웹 애플리케이션의 환경 정보 준비
    // => 정적 웹 자원의 경로
    StandardContext ctx = (StandardContext) tomcat.addWebapp(
        "/", // 컨텍스트 경로(웹 애플리케이션 경로)
        new File("src/main/webapp").getAbsolutePath() // 웹 애플리케이션 파일이 있는 실제 경로
    );
    ctx.setReloadable(true);

    // 웹 애플리케이션 기타 정보 설정
    WebResourceRoot resources = new StandardRoot(ctx);

    // 웹 애플리케이션의 서블릿 클래스 등록
    // => 동적 웹 자원의 경로
    resources.addPreResources(new DirResourceSet(
        resources, // 루트 웹 애플리케이션 정보
        "/WEB-INF/classes", // 서블릿 클래스 파일의 위치 정보
        new File("build/classes/java/main/").getAbsolutePath(), // 서블릿 클래스 파일이 있는 실제 경로
        "/" // 웹 애플리케이션 내부 경로
    ));

    // 웹 애플리케이션 설정 정보를 웹 애플리케이션 환경 정보에 등록
    ctx.setResources(resources);

    // 톰캣 서버 구동
    tomcat.start();

    // 톰캣 서버를 구동한 후 종료될 때까지 JVM을 끝내지 말고 기다린다.
    tomcat.getServer().await();

    System.out.println("서버 종료!");
  }
}
