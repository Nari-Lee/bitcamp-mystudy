package bitcamp.myapp.servlet.project;

import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectViewServlet
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 프로젝트의 상세 정보를 표시하는 서블릿 클래스입니다.
 *                  이 서블릿은 '/project/view' URL 패턴에 매핑되어 있으며,
 *                  요청 파라미터로 받은 프로젝트 번호에 해당하는 프로젝트의 상세 정보를
 *                  데이터베이스에서 조회하여 HTML 형식으로 출력합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 11.        narilee       projectService, userService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 */
@WebServlet("/project/view")
public class ProjectViewServlet extends HttpServlet {

  private ProjectService projectService;
  private UserService userService;
  /**
   * 서블릿 객체르 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의해 호출됩니다.
   * ProjectDao 객체와 UserDao 객체를 ServletContext에서 가져와 초기화힙니다.
   *
   * @throws ServletException 서블릿 초기화중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    projectService = (ProjectService) getServletContext().getAttribute("projectService");
  }

/**
 * 클라이언트의 요청을 처리하고 응답을 생성합니다.
 * 이 메서드는 요청 파라미터로 받은 프로젝트 번호에 해당하는 프로젝트의 상세 정보를 조회하여 HTML 형식으로 출력합니다.
 *
 * @param req 클라이언트의 요청이 포함된 ServletRequest 객체
 * @param res 서블릿의 응답을 포함하는 ServletResponse 객체
 *
 * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
 * @throws IOException 입출력 작업 중 오류가 발생한 경우
 */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    try {
      int projectNo = Integer.parseInt(req.getParameter("no"));
      Project project = projectService.get(projectNo);
      req.setAttribute("project", project);

      List<User> users = userService.list();
      req.setAttribute("users", users);
      req.setAttribute("viewName", "/project/view.jsp");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
