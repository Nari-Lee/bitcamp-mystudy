package bitcamp.myapp.servlet.project;

import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectUpdateServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narileel      UTF-8 필터 적용
 * 24. 9. 11.        narilee       projectService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 */
@WebServlet("/project/update")
  public class ProjectUpdateServlet extends HttpServlet {

  private ProjectService projectService;
  /**
   * 서블릿 객체를 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의헤 호출됩니다.
   * ProjectDao와 SqlSessionFactory 객체를 ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    projectService = (ProjectService) getServletContext().getAttribute("projectService");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    try {
      Project project = new Project();
      project.setNo(Integer.parseInt(req.getParameter("no")));
      project.setTitle(req.getParameter("title"));
      project.setDescription(req.getParameter("description"));
      project.setStartDate(Date.valueOf(req.getParameter("startDate")));
      project.setEndDate(Date.valueOf(req.getParameter("endDate")));

      String[] memberNos = req.getParameterValues("member");
      if (memberNos != null) {
        ArrayList<User> members = new ArrayList<>();
        for (String memberNo : memberNos) {
          members.add(new User(Integer.parseInt(memberNo)));
        }
        project.setMembers(members);
      }

      if (!projectService.update(project)) {
        throw new Exception("없는 프로젝트입니다!");
      }
      req.setAttribute("viewName", "redirect:list");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
