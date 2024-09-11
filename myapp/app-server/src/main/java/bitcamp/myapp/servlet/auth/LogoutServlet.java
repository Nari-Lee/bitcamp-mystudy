package bitcamp.myapp.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.auth
 * fileName       : LogoutServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    : 사용자의 로그인을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자의 이메일과 비밀번호를 검증하고, 로그인 성공시 세션에 사용자 정보를 저장합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 */
@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    req.getSession().invalidate();
    res.sendRedirect("/");
  }
}
