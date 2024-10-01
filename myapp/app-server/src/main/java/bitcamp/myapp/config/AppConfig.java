package bitcamp.myapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

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
@PropertySource({
                "classpath:config/jdbc.properties",
                "file:${user.home}/config/ncp.properties"})
@EnableTransactionManagement
@MapperScan("bitcamp.myapp.dao")
public class AppConfig {

  ApplicationContext appCtx;

  public AppConfig(ApplicationContext appCtx) {
    this.appCtx = appCtx;
    //AWS 경고메세지 제거
    System.getProperties().setProperty("aws.java.v1.disableDeprecationAnnouncements", "true");
  }

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
   * 이 메서드는 파일 업로드를 처리하기 위한 MultipartResolver를 설정합니다. StandardServletMultipartResolver를 사용하여 멀티파트
   * 요청(파일 업로드등)을 처리할 수 있게 합니다.
   *
   * @return 멀티파트 요청을 처리하기 위한 StandardServletMultipartResolver
   */
  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Bean
  public DataSource dataSource(
      @Value("${jdbc.driver}") String jdbcDriver,
      @Value("${jdbc.url}") String jdbcUrl,
      @Value("${jdbc.username}") String jdbcUsername,
      @Value("${jdbc.password}") String jdbcPassword) {

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(jdbcDriver);
    ds.setUrl(jdbcUrl);
    ds.setUsername(jdbcUsername);
    ds.setPassword(jdbcPassword);
    return ds;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource ds) {
    return new DataSourceTransactionManager(ds);
  }

  /**
   * MyBatis를 위한 SqlSessionFactory를 구성합니다.
   *
   * @return 구성된 SqlSessionFactoryProxy 를 감싸는 SqlSessionFactoryProxy
   * @throws Exception MyBatis 설정을 로드하는 중 오류가 발생한 경우
   */
  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(ds);
    factoryBean.setTypeAliasesPackage("bitcamp.myapp.vo");
    factoryBean.setMapperLocations(appCtx.getResources("classpath:mappers/*Mapper.xml"));
    return factoryBean.getObject();
  }
}
