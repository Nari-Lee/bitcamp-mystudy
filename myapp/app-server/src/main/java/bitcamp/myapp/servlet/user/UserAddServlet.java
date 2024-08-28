package bitcamp.myapp.servlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import bitcamp.net.Prompt;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

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
 */
@WebServlet("/user/add")
  public class UserAddServlet extends GenericServlet {

  private UserDao userDao;
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void init() throws ServletException {
   ServletContext servletContext = getServletContext();
   this.userDao = (UserDao) servletContext.getAttribute("userDao");
   this.sqlSessionFactory = (SqlSessionFactory) servletContext.getAttribute("sqlSessionFactory");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("    <meta charset='UTF-8'>");
    out.println("    <meta http-equiv='refresh' content='1;url=/user/list'>");
    out.println("    <title>Title</title>");
    out.println("    <link rel='stylesheet' href='/css/common.css'>");
    out.println("</head>");
    out.println("<body>");

    try {
      out.println("<header>");
      out.println("  <a href='/'><img src='/images/home.png'></a>");
      out.println("        프로젝트 관리 시스템");
      out.println("</header>");
      out.println("<h1>회원 등록 결과</h1>");

      User user = new User();
      user.setName(req.getParameter("name"));
      user.setEmail(req.getParameter("email"));
      user.setPassword(req.getParameter("password"));
      user.setTel(req.getParameter("tel"));

      userDao.insert(user);
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다.</p>");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 중 오류 발생!</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
