package bitcamp.myapp.listener;

import bitcamp.myapp.controller.AuthController;
import bitcamp.myapp.controller.BoardController;
import bitcamp.myapp.controller.ProjectController;
import bitcamp.myapp.controller.UserController;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.DaoFactory;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.service.*;
import bitcamp.mybatis.SqlSessionFactoryProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;
import java.util.*;

/**
 * packageName    : bitcamp.myapp.listener
 * fileName       : ContextLoaderListener
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 서블릿 컨테이너의 시작과 종료시 필요한 작업을 수행하는 리스너 클래스입니다.
 *                  이 클래스는 애플리케이션의 초기화 작업을 담당합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 9. 11.        narilee       dao에서 service로 변경
 * 24. 9. 13.        narilee       ControllerMap 추가
 */
@WebListener // 서블릿 컨테이너에 이 클래스를 배치하는 태그입니다.
public class ContextLoaderListener implements ServletContextListener {

  /**
   * 서블릿 컨테이너가 시작될 때 호출되는 메서드입니다.
   * 이 메서드에서 애플리케이션의 초기화 작업을 수행합니다.
   *
   * @param sce 초기화된 ServletContext에 대한 정보를 포함한 ServletContextEvent 객체
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      System.out.println("서비스 관련 객체 준비!");

      // Mybatis SqlSessionFactory 초기화
      InputStream inputStream = Resources.getResourceAsStream("config/mybatis-config.xml");
      SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
      SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

      SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

      // Dao 객체 생성
      DaoFactory daoFactory = new DaoFactory(sqlSessionFactoryProxy);

      UserDao userDao = daoFactory.createObject(UserDao.class);
      BoardDao boardDao = daoFactory.createObject(BoardDao.class);
      ProjectDao projectDao = daoFactory.createObject(ProjectDao.class);

      UserService userService = new DefaultUserService(userDao, sqlSessionFactoryProxy);
      BoardService boardService = new DefaultBoardService(boardDao, sqlSessionFactoryProxy);
      ProjectService projectService = new DefaultProjectService(projectDao, sqlSessionFactoryProxy);

      // ServletContext에 객체 저장
      ServletContext ctx = sce.getServletContext();
      ctx.setAttribute("sqlSessionFactory", sqlSessionFactoryProxy);

      List<Object> controllers = new ArrayList<>();
      controllers.add(new UserController(userService));
      controllers.add(new ProjectController(projectService, userService));
      controllers.add(new AuthController(userService));
      controllers.add(new BoardController(boardService, ctx));
      controllers.add(new DownloadController(boardService, ctx));

      ctx.setAttribute("controllers", controllers);

    } catch (Exception e) {
      System.out.println("서비스 객체 준비 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
