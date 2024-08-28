package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.myapp.vo.User;
import bitcamp.net.Prompt;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {

  List<ApplicationListener> listeners = new ArrayList<>();
  ApplicationContext appCtx = new ApplicationContext();

  public static void main(String[] args) {
    try {
      ServerApp app = new ServerApp();
      app.execute();
    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  private void execute() throws Exception {
    System.out.println("서버 실행 중...");

    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.setBaseDir("temp");

    Connector connector = tomcat.getConnector();
    connector.setURIEncoding("UTF-8");

    StandardContext ctx = (StandardContext) tomcat.addContext("/", new File("src/main/webapp").getAbsolutePath());
    ctx.setReloadable(true);

    WebResourceRoot resources = new StandardRoot(ctx);

    resources.addPreResources(new DirResourceSet(
        resources, // 루트 웹 애플리케이션 정보
        "/WEB-INF/classes", // 서블릿 클래스 파일의 위치 정보
        new File("build/classes/java/main/").getAbsolutePath(), // 서블릿 클래스 파일이 있는 실제 경로
        "/" // 웹 애플리케이션 내부 경로
    ));

    ctx.setResources(resources);
    tomcat.start();
    tomcat.getServer().await();

    System.out.println("서버 종료!");
  }
}
