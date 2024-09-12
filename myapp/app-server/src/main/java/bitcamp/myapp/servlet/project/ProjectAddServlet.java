package bitcamp.myapp.servlet.project;

import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.vo.Project;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectAddServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 프로젝트 등록을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자가 입력한 프로젝트 정보를 받아 데이터베이스에 저장하고,
 *                  처리 결과를 HTML 형식으로 응답합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narilee       UTF-8 필터 적용, 첨부파일 추가
 * 24. 9. 11.        narilee       projectService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 */
@WebServlet("/project/add")
public class ProjectAddServlet extends HttpServlet {

  /** Project 엔티티에 대한 데이터 액세스 객체입니다. */
  private ProjectService projectService;

  /**
   * 서블릿 객체를 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의헤 호출됩니다.
   * ProjectDao, UserDao와 SqlSessionFactory 객체를 ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    projectService = (ProjectService) getServletContext().getAttribute("projectService");
  }

  /**
   * 클라이언트의 프로젝트 등록 요청을 처리하고 응답을 생성합니다.
   * 이 메서드는 요청 파리미터로 받은 프로젝트 정보(제목, 설명, 시작일, 종료일, 팀원)를
   * 데이터베이스에 저장하고, 처리 결과를 HTML 형식으로 출력합니다.
   * 프로젝트 등록 성공 시 자동으로 프로젝트 목록 페이지로 리다이렉트됩니다.
   *
   * @param req 클라이언트의 요청이 포함된 ServletRequest 객체
   * @param res 서블릿의 응답을 포함하는 ServletResponse 객체
   *
   * @throws ServletException 서블릿 처리중 오류가 발생한 경우
   * @throws IOException 입출력 작업 중 오류가 발생한 경우
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    try {
      Project project = (Project) req.getSession().getAttribute("project");
      projectService.add(project);
      // 세션에 임시 보간했던 Project 객치를 제거한다.
      req.getSession().removeAttribute("project");
      req.setAttribute("viewName", "redirect:list");

    } catch (Exception e) {
      req.setAttribute("exception", e);
    }
  }
}
