package bitcamp.myapp.servlet.user;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.user
 * fileName       : UserUpdateServlet
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
@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init() throws ServletException {
    userService = (UserService) getServletContext().getAttribute("userService");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    try {
      User user = new User();
      user.setNo(Integer.parseInt(req.getParameter("no")));
      user.setName(req.getParameter("name"));
      user.setPassword(req.getParameter("password"));
      user.setEmail(req.getParameter("email"));
      user.setTel(req.getParameter("tel"));

      if (userService.update(user)) {
        req.setAttribute("viewName", "redirect:list");
      } else {
        throw new Exception("없는 회원입니다!");
      }
    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
