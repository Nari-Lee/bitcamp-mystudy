package bitcamp.myapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * packageName    : bitcamp.myapp.config
 * fileName       : RootConfig
 * author         : narilee
 * date           : 24. 10. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 14.        narilee       최초 생성
 */
@ComponentScan(
    value = "bitcamp.myapp",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        value = {Controller.class}
    )
)
@PropertySource({
    "classpath:config/jdbc.properties",
    "file:${user.home}/config/ncp.properties"})
@EnableTransactionManagement
@MapperScan("bitcamp.myapp.dao")
public class RootConfig {

  ApplicationContext appCtx;

  public RootConfig(ApplicationContext appCtx) {
    this.appCtx = appCtx;

    //AWS 경고메세지 제거
    System.getProperties().setProperty("aws.java.v1.disableDeprecationAnnouncements", "true");
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
