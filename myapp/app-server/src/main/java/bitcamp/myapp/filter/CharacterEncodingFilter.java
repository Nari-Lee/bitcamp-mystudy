package bitcamp.myapp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.filter
 * fileName       : CharacterEncodingFilter
 * author         : narilee
 * date           : 24. 9. 9.
 * description    : 문자 인코딩을 처리하는 필터 클래스입니다.
 *                  모든 URL 패턴에 적용되며, POST 요청에 대해 문자 인코딩을 실행합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 9.        narilee       최초 생성
 */
@WebFilter(
    urlPatterns = "/*",
    initParams = @WebInitParam(name = "encoding", value = "UTF-8")
)
public class CharacterEncodingFilter implements Filter {

  /**
   * 기본 문자 인코딩
   */
  private String encoding = "UTF-8";

  /**
   * 필터를 초기화합니다.
   * 초기화 파라미터에서 인코딩 값을 가져와 설정합니다.
   *
   * @param filterConfig 필터 구성 객체
   *
   * @throws ServletException 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    if (filterConfig.getInitParameter("encoding") != null) {
      encoding = filterConfig.getInitParameter("encoding");
    }
    System.out.println("CharacterEncodingFilter 객체 준비!");
  }

  /**
   * 요청에 대한 필터링을 수행합니다.
   * POST 요청에 대해서만 문자 인코딩을 설정합니다.
   *
   * @param request 서블릿 요청 객체
   * @param response 서블릿 응답 객체
   * @param chain 필터 체인 객체
   *
   * @throws IOException 입출력 예외가 발생한 경우
   * @throws ServletException 서블릿 관련 예외가 발생한 경우
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    if (httpRequest.getMethod().equals("POST")) {
      request.setCharacterEncoding(this.encoding);
    }

    chain.doFilter(request, response); // 다음 필터 또는 서블릿 실행
  }
}
