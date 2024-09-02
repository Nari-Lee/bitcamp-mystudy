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
 * date           : 24. 8. 29.
 * description    : 애플리케이션의 컨텍스트 초기화를 담당하는 리스너 클래스입니다.
 *                  서블릿 컨테이너가 시작될 때 필요한 객체들을 생성하고 설정합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 */
@WebListener // 서블릿 컨테이너에 이 클래스를 리스너로 등록하는 애노테이션
public class ContextLoaderListener implements ServletContextListener {

  /**
   * 서블릿 컨텍스트가 초기화될 때 호출되는 메서드입니다.
   * MyBatis 설정, DAO 객체 생성 그리고 ServletContext에 객체들을 초기화하고 바인딩합니다.
   *
   * @param sce 초기화된 ServletContext에 대한 정보를 포함한 ServletContextEvent 객체
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 서블릿 컨테이너가 실행될 때 호출한다.
    try {
      // MyBatis 설정 파일을 로드합니다.
      InputStream inputStream = Resources.getResourceAsStream("config/mybatis-config.xml");

      // SqlSessionFactory를 생성합니다.
      SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
      SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

      /*
       * SqlSessionFactoryProxy 객체를 생성합니다.
       * 이 객체는 SqlSessionFactory를 대신해서 사용됩니다.
       * 대부분의 기능은 원래의 SqlSessionFactory가 처리하지만
       * 일부 기능은 SqlSession 객체를 리턴할 때 스레드 별로 사용되는 객체를 만들어 리턴합니다.
       */
      SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

      // DaoFactory 객체를 생성합니다.
      DaoFactory daoFactory = new DaoFactory(sqlSessionFactoryProxy);

      // DAO 객체들을 생성합니다.
      UserDao userDao = daoFactory.createObject(UserDao.class);
      BoardDao boardDao = daoFactory.createObject(BoardDao.class);
      ProjectDao projectDao = daoFactory.createObject(ProjectDao.class);

      // ServletContext 객체를 가져옵니다.
      ServletContext ctx = sce.getServletContext();
      
      // 생성한 객체들을 ServletContext에 속성으로 바인딩합니다.
      ctx.setAttribute("sqlSessionFactory", sqlSessionFactoryProxy);
      ctx.setAttribute("userDao", userDao);
      ctx.setAttribute("boardDao", boardDao);
      ctx.setAttribute("projectDao", projectDao);

    }catch (Exception e) {
      System.out.println("서비스 객체 준비 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
