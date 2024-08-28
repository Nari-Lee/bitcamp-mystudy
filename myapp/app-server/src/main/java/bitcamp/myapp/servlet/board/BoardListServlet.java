package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : bitcamp.myapp.servlet.board
 * fileName       : BoardListServlet
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 게시판 목록을 표시하는 서블릿 클래스입니다.
 *                  이 서블릿은 '/board/list' URL 패턴에 매핑되어 있으며,
 *                  데이터베이스에서 게시판 목록을 조회하여 HTML 형식으로 출력합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
@WebServlet("/board/list")
public class BoardListServlet implements Servlet {

  private ServletConfig config;
  private BoardDao boardDao;

  /**
   * 서블릿 객체를 생성한 후 바로 호출합니다.
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
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
  }

  /**
   * 클라이언트의 요청을 처리하고 응답을 생성합니다.
   * 이 메서드는 사용자 목록을 조회하여 HTML 형식으로 출력합니다.
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
      out.println("<h1>게시글 목록</h1>");
      out.println("<p><a href='/board/form.html'><th>새 게사글</th></p>");
      out.println("<table border='1'>");
      out.println("<thead>");
      out.println("<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회수</th></tr>");
      out.println("</thead>");
      out.println("<tbody>");;

      for (Board board : boardDao.list()) {
        out.printf("    <tr><td>%d</td><td><a href='/board/view?no=%1$d'>%s</td><td>%s</td><td>%tY-%4$tm-%4$td</td><td>%d</td></tr>\n", board.getNo(), board.getTitle(),
            board.getWriter().getName(), board.getCreatedDate(), board.getViewCount());
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
    return "게시글 목록 조회";
  }
}
