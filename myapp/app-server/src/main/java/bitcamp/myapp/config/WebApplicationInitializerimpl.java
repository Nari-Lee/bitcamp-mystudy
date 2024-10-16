package bitcamp.myapp.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

/**
 * packageName    : bitcamp.myapp.config
 * fileName       : WebApplicationInitializerimpl
 * author         : narilee
 * date           : 24. 10. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 14.        narilee       최초 생성
 */
  public class WebApplicationInitializerimpl extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    // ContextLoaderListener의 IoC 컨테이너 설정
    // - DB 관련 객체 준비
    // - 서비스 관련 객체 준비
    return new Class[] {RootConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    // DispatcherServlet의 IoC 컨테이너 설정
    // - 웹 관련 객체 준비
    return new Class[] {AppConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }

  @Override
  protected void customizeRegistration(ServletRegistration.Dynamic registration) {

    // 프론트 컨트롤러 역할을 수행할 서블릿 객체 생성
    registration.setMultipartConfig(new MultipartConfigElement(
        // 멀티파트 설정
        new File("./temp/").getAbsolutePath(), // 업로드 파일을 임시 보관할 폴더
        1024 * 1024 * 20,
        1024 * 1024 * 100,
        1024 * 1024 * 1));

  }

  @Override
  protected Filter[] getServletFilters() {
    // 필터 객체 생성
    return new Filter[] {new CharacterEncodingFilter("UTF-8")};
  }
}
