package bitcamp.myapp.servlet.user;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.servlet.user
 * fileName       : UserListServlet
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 사용자 목록을 표시하는 서블릿 클래스입니다.
 *                  이 서블릿은 '/user/list' URL 패턴에 매핑되어 있으며,
 *                  데이터베이스에서 사용자 목록을 조회하여 HTML 형식으로 출력합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 8. 29.        narilee       HeaderServlet으로 공통부분 옮김
 * 24. 8. 30.        narilee       list.jsp 적용
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narileel      UTF-8 필터 적용
 * 24. 9. 11.        narilee       UserService 적용
 */
@WebServlet("/user/list")
public class UserListServlet extends HttpServlet {

  private UserService userService;

  /**
   * 서블릿 객체를 생성한 후 바로 호출합니다.
   * 서블릿이 작업할 사용할 의존 객체를 준비하는 일을 이 메서드에서 수행합니다.
   */
  @Override
  public void init() {
    userService = (UserService) getServletContext().getAttribute("userService");
  }

  /**
   * 클라이언트의 요청을 처리하고 응답을 생성합니다. 이 메서드는 사용자 목록을 조회하여 HTML 형식으로 출력합니다.
   *
   * @param req 클라이언트의 요청이 포함된 ServletRequest 객체
   * @param res 서블릿의 응답을 포함하는 ServletResponse 객체
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   * @throws IOException      입출력 작업 중 오류가 발생한 경우
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      List<User> list = userService.list();

      // 콘텐트 출력은 JSP에게 맡긴다.
      // JSP를 실행하기 전에 JSP가 사용할 객체를 ServletRequest 보관소에 보관한다.
      req.setAttribute("list", list);
      req.setAttribute("viewName", "/user/list.jsp");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
