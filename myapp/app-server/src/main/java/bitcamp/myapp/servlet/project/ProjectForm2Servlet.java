package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectForm2Servlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 프로젝트 등록을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자가 입력한 프로젝트 정보를 받아 데이터베이스에 저장하고,
 *                  처리 결과를 HTML 형식으로 응답합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 06.        narilee       최초 생성
 */
@WebServlet("/project/form2")
public class ProjectForm2Servlet extends HttpServlet {

  private UserDao userDao;

  @Override
  public void init() throws ServletException {
    this.userDao = (UserDao) this.getServletContext().getAttribute("userDao");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    try {
    Project project = new Project();
    project.setTitle(req.getParameter("title"));
    project.setDescription(req.getParameter("description"));
    project.setStartDate(Date.valueOf(req.getParameter("startDate")));
    project.setEndDate(Date.valueOf(req.getParameter("endDate")));

    HttpSession session = req.getSession();
    session.setAttribute("project", project);

    List<User> users = userDao.list();
    req.setAttribute("users", users);

    res.setContentType("text/html;charset=UTF-8");
    req.getRequestDispatcher("/project/form2.jsp").include(req, res);

    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
