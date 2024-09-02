package bitcamp.myapp.servlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.servlet.user
 * fileName       : UserListServlet
 * author         : narilee date           : 24. 8. 27.
 * description    : 사용자 목록을 표시하는 서블릿 클래스입니다.
 *                  이 서블릿은 '/user/list' URL 패턴에 매핑되어 있으며,
 *                  데이터베이스에서 사용자 목록을 조회하여 HTML 형식으로 출력합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 8. 29.        narilee       HeaderServlet으로 공통부분 옮김
 * 24. 8. 30.        narilee       list.jsp 적용
 */
@WebServlet("/user/list")
public class UserListServlet extends GenericServlet {

  private UserDao userDao;

  /**
   * 서블릿 객체를 생성한 후 바로 호출합니다.
   * 서블릿이 작업할 사용할 의존 객체를 준비하는 일을 이 메서드에서 수행합니다.
   */
  @Override
  public void init() {
    userDao = (UserDao) this.getServletContext().getAttribute("userDao");
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
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    try {
      List<User> list = userDao.list();

      // 콘텐트 출력은 JSP에게 맡긴다.
      // JSP를 실행하기 전에 JSP가 사용할 객체를 ServletRequest 보관소에 보관한다.
      req.setAttribute("list", list);

      // 출력한 콘텐트의 타입을 먼저 지정한 후 출력 스트림을 얻는다.
      // text/plain -> 텍스트 대로 출력
      // text/html -> html 렌더링
      // 기타 -> 다운로드창 출력
      // 콘텐트 타입은 include() 호출 전에 실행해야 한다.
      res.setContentType("text/html;charset=UTF-8");
      req.getRequestDispatcher("/user/list.jsp").include(req, res);

    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
