package bitcamp.myapp.config;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.DaoFactory;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.mybatis.SqlSessionFactoryProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.InputStream;

/**
 * packageName    : bitcamp.myapp.config
 * fileName       : AppConfig
 * author         : narilee
 * date           : 24. 9. 23.
 * description    : 애플리케이션의 설정 클래스입니다. 이 클래스는 Spring 컨텍스트를 설정하고 다양한 빈을 정의합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 23.        narilee       최초 생성
 * 24. 9. 24.        narilee       Service 제거
 * 24. 9. 25.        narilee       Spring 도입
 */
@ComponentScan("bitcamp.myapp")
@EnableWebMvc
public class AppConfig {

  /**
   * JSP 페이지를 위한 뷰 리졸버를 구성합니다.
   *
   * @return JSP 페이지용으로 구성된 InternationalResourceViewResolver
   */
  @Bean
  public ViewResolver viewResolver() {
    // InternalResourceViewResolver 를 사용하여 JSP 위치를 지정합니다.
    InternalResourceViewResolver vr = new InternalResourceViewResolver();
    // Prefix를 설정하여 JSP 파일들이 해당 경로에 있음을 지정합니다.
    vr.setPrefix("/WEB-INF/jsp/");
    // sufix를 설정하여 모둔 뷰파일이 .jsp확장자를 가짐을 지정합니다.
    vr.setSuffix(".jsp");
    return vr;
  }

  /**
   * 이 메서드는 파일 업로드를 처리하기 위한 MultipartResolver를 설정합니다.
   * StandardServletMultipartResolver를 사용하여 멀티파트 요청(파일 업로드등)을 처리할 수 있게 합니다.
   *
   * @return 멀티파트 요청을 처리하기 위한 StandardServletMultipartResolver
   */
  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  /**
   * MyBatis를 위한 SqlSessionFactory를 구성합니다.
   *
   * @return 구성된 SqlSessionFactoryProxy 를 감싸는 SqlSessionFactoryProxy
   * @throws Exception MyBatis 설정을 로드하는 중 오류가 발생한 경우
   */
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    InputStream inputStream = Resources.getResourceAsStream("config/mybatis-config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

    return new SqlSessionFactoryProxy(sqlSessionFactory);
  }

  /**
   * sqlSessionFactory를 사용하여 DaoFactory를 생성합니다.
   *
   * @param sqlSessionFactory 사용할 sqlSessionFactory
   * @return DaoFactory 인스턴스
   * @throws Exception DaoFactory 생성 중 오류가 발생한 경우
   */
  @Bean
  public DaoFactory daoFactory(SqlSessionFactory sqlSessionFactory) throws Exception {
    return new DaoFactory(sqlSessionFactory);
  }

  /**
   * DaoFactory를 사용하여 UserDao를 생성합니다.
   *
   * @param daoFactory UserDao 생성에 필요한 DaoFactory
   * @return UserDao 인스턴스
   * @throws Exception UserDao 생성 중 오류가 발생한 경우
   */
  @Bean
  public UserDao userDao(DaoFactory daoFactory) throws Exception {
    return daoFactory.createObject(UserDao.class);
  }

  /**
   * DaoFactory를 사용하여 BoardDao를 생성합니다.
   *
   * @param daoFactory BoardDao 생성에 필요한 DaoFactory
   * @return BoardDao 인스턴스
   * @throws Exception Board 생성 중 오류가 발생한 경우
   */
  @Bean
  public BoardDao boardDao(DaoFactory daoFactory) throws Exception {
    return daoFactory.createObject(BoardDao.class);
  }

  /**
   * DaoFactory를 사용하여 ProjectDao를 생성합니다.
   *
   * @param daoFactory ProjectDao 생성에 필요한 DaoFactory
   * @return ProjectDao 인스턴스
   * @throws Exception ProjectDao 생성 중 오류가 발생한 경우
   */
  @Bean
  public ProjectDao projectDao(DaoFactory daoFactory) throws Exception {
    return daoFactory.createObject(ProjectDao.class);
  }
}
