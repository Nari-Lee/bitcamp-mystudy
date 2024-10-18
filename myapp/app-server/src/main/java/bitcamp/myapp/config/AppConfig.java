package bitcamp.myapp.config;

import bitcamp.myapp.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
@ComponentScan("bitcamp.myapp.controller")
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

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

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new AdminInterceptor())
        .addPathPatterns("/users*");
  }
}
