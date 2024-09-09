package bitcamp.myapp.servlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

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
 * 24. 9. 05         narilee       HttpServlet으로 변경
 */
@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {

  private UserDao userDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    userDao = (UserDao) getServletContext().getAttribute("userDao");
    sqlSessionFactory = (SqlSessionFactory) getServletContext().getAttribute("sqlSessionFactory");
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

      if (userDao.update(user)) {
        sqlSessionFactory.openSession(false).commit();
        res.sendRedirect("/user/list");
      } else {
        throw new Exception("없는 회원입니다!");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
