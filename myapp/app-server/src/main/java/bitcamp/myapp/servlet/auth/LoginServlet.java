package bitcamp.myapp.servlet.auth;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.auth
 * fileName       : LoginServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 사용자의 로그인을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자의 이메일과 비밀번호를 검증하고, 로그인 성공시 세션에 사용자 정보를 저장합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

  /** User 엔티티에 대한 테이터 엑세스 객체입니다. */
  private UserDao userDao;

  /**
   * 서블릿 객체를 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의해 호출됩니다.
   * UserDao 객체를 ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    // 서블릿 컨테이너 ---> init(ServletConfig) ---> init() 호출합니다.
    userDao = (UserDao) this.getServletContext().getAttribute("userDao");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.getRequestDispatcher("/auth/form.jsp").include(req, res);
  }

  /**
   * 클라이언트의 요청을 처리하고 응답을 생성합니다.
   * 이 메서드는 요청 파라미터로 받은 이메일과 비밀번호를 검증하고,
   * 로그인 성공시 세션에 사용자 정보를 저장합니다.
   * 처리 결과는 HTML 형식으로 출력됩니다.
   *
   * @param req 클라이언트의 요청이 포함된 ServletRequest 객체
   * @param res 서블릿의 응답을 포함하는 ServletResponse 객체
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   * @throws IOException 입출력 작업 중 오류가 발생한 경우
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      String email = req.getParameter("email");
      String password = req.getParameter("password");

      User user = userDao.findByEmailAndPassword(email, password);
      if (user == null) {
        res.setHeader("Refresh", "1;url=/auth/form");
       res.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/auth/fail.jsp").include(req, res);
        return;
      }

      if (req.getParameter("saveEmail") != null) {
        Cookie cookie = new Cookie("email", email);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        res.addCookie(cookie);
      } else {
        Cookie cookie = new Cookie("email", "test@test.com");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
      }

      /**
       * HTTP 프로토콜 관련 기능을 사용하려면
       * 파라미터로 ServletRequest 받은 객체를 원래 타입으로 형변환 해야 합니다.
       * 즉 req 레퍼런스는 실제 HttpServletRequest 객체를 가리키고 있습니다.
      */
      HttpServletRequest httpReq = (HttpServletRequest) req;
      /**
       * 클라이언트 전용 보관소를 알아냅니다.
      */
      HttpSession session = httpReq.getSession();
      /**
       * 클라이언트 전용 보관소에 로그인 사용자 정보를 보관합니다.
       */
      session.setAttribute("loginUser", user);
      res.sendRedirect("/");
    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
