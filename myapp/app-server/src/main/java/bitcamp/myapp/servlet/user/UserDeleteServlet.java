package bitcamp.myapp.servlet.user;

import bitcamp.myapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.user
 * fileName       : UserDeleteServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 11.        narilee       UserService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 */
@WebServlet("/user/delete")
public class UserDeleteServlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init() throws ServletException {
    userService = (UserService) getServletContext().getAttribute("userService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      int userNo = Integer.parseInt(req.getParameter("no"));

      if (userService.delete(userNo)) {
        req.setAttribute("viewName", "redirect:list");
      } else {
        throw new Exception("없는 회원입니다.");
      }

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
