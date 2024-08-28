package bitcamp.myapp.listener;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.DaoFactory;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.mybatis.SqlSessionFactoryProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;

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
 */
@WebListener // 서블릿 컨테이너에 이 클래스를 배치하는 태그다.
public class ContextLoaderListener implements ServletContextListener {

  /**
   * 서블릿 컨테이너가 시작될 때 호출되는 메서드입니다.
   * 이 메서드에서 애플리케이션의 초기화 작업을 수행합니다.
   *
   * @param sce 초기화된 ServletContextdㅔ 대한 정보를 포함한 ServletContextEvent 객체
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

      // ServletContext에 객체 저장
      ServletContext ctx = sce.getServletContext();
      ctx.setAttribute("sqlSessionFactory", sqlSessionFactoryProxy);
      ctx.setAttribute("userDao", userDao);
      ctx.setAttribute("boardDao", boardDao);
      ctx.setAttribute("projectDao", projectDao);

    } catch (Exception e) {
      System.out.println("서비스 객체 준비 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
