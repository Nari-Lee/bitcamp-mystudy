package bitcamp.myapp.servlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.user
 * fileName       : UserAddServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 8. 30         narilee       수정
 * 24. 9. 05         narilee       HttpServlet으로 변경
 */
@WebServlet("/user/add")
public class UserAddServlet extends HttpServlet {

  private UserDao userDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
    ServletContext servletContext = getServletContext();
    this.userDao = (UserDao) servletContext.getAttribute("userDao");
    this.sqlSessionFactory = (SqlSessionFactory) servletContext.getAttribute("sqlSessionFactory");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.getRequestDispatcher("/user/form.jsp").include(req, res);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      User user = new User();
      user.setName(req.getParameter("name"));
      user.setEmail(req.getParameter("email"));
      user.setPassword(req.getParameter("password"));
      user.setTel(req.getParameter("tel"));

      userDao.insert(user);
      sqlSessionFactory.openSession(false).commit();
      res.sendRedirect("/user/list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
