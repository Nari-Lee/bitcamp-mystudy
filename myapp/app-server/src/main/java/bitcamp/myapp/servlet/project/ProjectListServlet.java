package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

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
 */
@WebServlet("/project/list")
public class ProjectListServlet implements Servlet {

  private ServletConfig config;
  private ProjectDao projectDao;

  /**
   * 서블릿 객체를 생성한 후 바로 호출됩니다.
   * 서블릿이 작업할 사용할 의존 객체를 준비하는 일을 이 메서드에서 수행합니다.
   *
   * @param config 서블릿의 구성 및 초기화를 포함하는 ServletConfig 객체
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    this.config = config;

    ServletContext ctx = config.getServletContext();
    projectDao = (ProjectDao) ctx.getAttribute("projectDao");
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

    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();

    // 출력 콘텐츠를 어떤 문자 집합으로 인코딩 할 지 지정하지 않고 출력 스트림을 꺼내면 출력 문자열(UTF-16BE)dms ISO-8859-1 문자집합에 맞춰 인코딩 됩니다.
    // 만약 UTF-16BE에 있는 문자가 ISO-8859-1에 정의되어 있지 않다면, '?' 문자로 변환됩니다.

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("    <meta charset='UTF-8'>");
    out.println("    <title>Title</title>");
    out.println("    <link rel='stylesheet' href='/css/common.css'>");
    out.println("</head>");
    out.println("<body>");

    try {
      out.println("<header>");
      out.println("  <a href='/'><img src='/images/home.png'></a>");
      out.println("        프로젝트 관리 시스템");
      out.println("</header>");
      out.println("<h1>프로젝트 목록</h1>");
      out.println("<p><a href='/project/form'><th>새 프로젝트</th></p>");
      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr><th>번호</th><th>프로젝트</th><th>기간</th></tr>");
      out.println("</thead>");
      out.println("<tbody>");

      for (Project project : projectDao.list()) {
        out.printf("      <tr><td>%d</td><td><a href='/project/view?no=%1$d'>%s</td><td>%s ~ %s</td></tr>\n",
                project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
      }
      out.println("</tbody>");
      out.println("</table>");

    } catch (Exception e) {
      out.println("<p>목록 조회 중 오류 발생!</p>");
    }
  }

  /**
   * 서블릿 컨테이너가 종료되기 전에 해제할 자원이 있다면 이 메서드에서 수행됩니다.
   */
  @Override
  public void destroy() {
  }

  /**
   * 서블릿의 정보를 조회할 때 사용할 ServletConfig 객체를 리턴해 줍니다.
   * 이 메서드가 리턴할 ServletConfig 객체는 init() 메서드가 호출될때 파라미터로 넘어온 객체입니다.
   * 따라서 init() 메서드가 호출될 때 ServletConfig 객체를 보관해 둬야 합니다.
   *
   * @return ServletConfig 객체
   */
  @Override
  public ServletConfig getServletConfig() {
    return this.config;
  }

  /**
   * 서블릿 컨테이너 관리 화면에서 서블릿의 정보를 출력할 때 이 메서드가 호출됩니다.
   *
   * @return 서블릿 정보 문자열
   */
  @Override
  public String getServletInfo() {
    return "프로젝트 목록 조회";
  }
}
