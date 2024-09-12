package bitcamp.myapp.servlet.project;

import bitcamp.myapp.service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectDeleteServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 11.        narilee       projectService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 *
 */
@WebServlet("/project/delete")
public class ProjectDeleteServlet extends HttpServlet {

  /** Project 엔티티에 대한 데이터 액세스 객체입니다. */
  private ProjectService projectService;

  /**
   * 서블릿 객체를 초기화합니다. 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의헤 호출됩니다. ProjectDao와 SqlSessionFactory 객체를
   * ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    projectService = (ProjectService) getServletContext().getAttribute("projectService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      int projectNo = Integer.parseInt(req.getParameter("no"));

      if (projectService.delete(projectNo)) {
        req.setAttribute("viewName", "redirect:list");
      } else {
        throw new Exception("없는 프로젝트입니다.");
      }

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
