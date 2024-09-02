package bitcamp.myapp.servlet.user;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : bitcamp.myapp.servlet.user
 * fileName       : UserFormServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 */
@WebServlet("/user/form")
public class UserFormServlet extends GenericServlet {

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.getRequestDispatcher("/user/form.jsp").include(req, res);
  }
}
