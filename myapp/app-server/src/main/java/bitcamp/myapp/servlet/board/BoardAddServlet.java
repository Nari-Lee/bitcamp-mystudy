package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : bitcamp.myapp.servlet.board
 * fileName       : BoardAddServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 게시글 등록을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자가 입력한 게시글 정보를 받아 데이터베이스에 저장하고,
 *                  처리 결과를 HTML 형식으로 응답합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 */
@WebServlet("/board/add")
  public class BoardAddServlet extends GenericServlet {

  /** Board 엔티티에 대한 대이터 액세스 객체입니다. */
  private BoardDao boardDao;

  /** 데이터베이스 세션을 관리하는 SqlSessionFactory 객체입니다. */
  private SqlSessionFactory sqlSessionFactory;

  /**
   * 서블릿 객체를 초기화합니다.
   * 이 메서드는 서블릿이 배치될 떄 서블릿 컨테이너에 의해 호출됩니다.
   * BoardDao와 SqlSessionFactory 객체를 ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    ServletContext ctx = this.getServletContext();
    this.boardDao = (BoardDao) ctx.getAttribute("boardDao");
    this.sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSessionFactory");
  }

  /**
   * 클라이언트의 게시글 등록 요청을 처리하고 응답을 생성합니다.
   * 이 메서드는 요청 파라미터로 받은 게시글 정보를 데이터베이스에 저장하고,
   * 처리 결과를 HTML 형식으로 출력합니다.
   * 게시글 등록 성공시 자동으로 게시글 목록 페이지로 리다이렉트됩니다.
   *
   * @param req 클라이언트의 요청이 포함된 ServletRequest 객체
   * @param res 서블릿의 응답을 포함하는 ServletResponse 객체
   *
   * @throws ServletException 서블릿 처리 중 오류가 발생한 경우
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
    out.println("    <meta http-equiv='refresh' content='1;url=/board/list'>");
    out.println("    <title>Title</title>");
    out.println("    <link rel='stylesheet' href='/css/common.css'>");
    out.println("</head>");
    out.println("<body>");

    try {
      out.println("<header>");
      out.println("  <a href='/'><img src='/images/home.png'></a>");
      out.println("        프로젝트 관리 시스템");
      out.println("</header>");
      out.println("<h1>게시글 등록 결과</h1>");

      Board board = new Board();
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));

      // 클라이언트 전용 보관소애서 로그인 사용자 정보를 꺼냅니다.
      User loginUser = (User) ((HttpServletRequest)req).getSession().getAttribute("loginUser");
      board.setWriter(loginUser);

      boardDao.insert(board);
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다.</p>");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 중 오류 발생!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}
