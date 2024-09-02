package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectListServlet
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 프로젝트 목록을 표시하는 서블릿 클래스입니다.
 *                  이 서블릿은 '/project/list' URL 패턴에 매핑되어 있으며,
 *                  데이터베이스에서 프로젝트 목록을 조회하여 HTML 형식으로 출력합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 8. 30.        narilee       list.jsp 적용
 */
@WebServlet("/project/list")
public class ProjectListServlet extends GenericServlet {

  private ProjectDao projectDao;

  /**
   * 서블릿 객체를 생성한 후 바로 호출됩니다.
   * 서블릿이 작업할 사용할 의존 객체를 준비하는 일을 이 메서드에서 수행합니다.
   */
  @Override
  public void init() {
    projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
  }

  /**
   * 서블릿 객체를 생성한 후 바로 호출합니다.
   * 서블릿이 작업할 사용할 의존 객체를 준비하는 일을 이 메서드에서 수행합니다.
   *
   * @param req 클라이언트의 요청이 포함된 ServletRequest 객체
   * @param res 서블릿의 응답을 포함하는 ServletResponse 객체
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   * @throws IOException 입출력 작업 중 오류가 발생한 경우
   */
  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    try {
      List<Project> list = projectDao.list();

      req.setAttribute("list", list);

      res.setContentType("text/html;charset=UTF-8");
      req.getRequestDispatcher("/project/list.jsp").include(req, res);

    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
