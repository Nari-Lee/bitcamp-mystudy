package bitcamp.myapp.servlet.auth;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : bitcamp.myapp.servlet.auth
 * fileName       : HomeServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 */
@WebServlet("/auth/form")
  public class LoginFormServlet extends GenericServlet {

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.getRequestDispatcher("/auth/form.jsp").forward(req, res);
  }
}
