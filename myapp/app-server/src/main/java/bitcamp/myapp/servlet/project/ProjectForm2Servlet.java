package bitcamp.myapp.servlet.project;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

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
 * 24. 9. 11.        narilee       userService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 */
@WebServlet("/project/form2")
public class ProjectForm2Servlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init() throws ServletException {
    userService = (UserService) getServletContext().getAttribute("userService");
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

    List<User> users = userService.list();
    req.setAttribute("users", users);
    req.setAttribute("viewName", "/project/form2.jsp");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
