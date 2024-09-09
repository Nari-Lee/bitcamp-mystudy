package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.board
 * fileName       : BoardViewServlet
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 게시판 상세 정보를 표시하는 서블릿 클래스입니다.
 *                  이 서블릿은 '/board/view' URL 패턴에 매핑되어 있으며,
 *                  요청 파라미터로 받은 게시글 번호에 해당하는 게시글의 상세 정보를
 *                  데이터베이스에서 조회하여 HTML 형식으로 출력합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 8. 29.        narilee       Update, Delete 추가
 * 24. 8. 30.        narilee       list.jsp 적용
 * 24. 9. 05         narilee       HttpServlet으로 변경
 */
@WebServlet("/board/view")
  public class BoardViewServlet extends HttpServlet {

  /** User 엔티티에 대한 테이터 엑세스 객체입니다. */
  private BoardDao boardDao;
  private SqlSessionFactory sqlSessionFactory;

  /**
   * 서블릿 객체를 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의해 호출됩니다.
   * BoardDao 객체와 SqlSessionFactory 객체를 ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    // 서블릿 컨테이너 ---> init(ServletConfig) ---> init() 호출합니다.
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
  }

  /**
   * 클라이언트의 요청을 처리하고 응답을 생성합니다.
   * 이 메서드는 요청 파라미터로 받은 게시글 번호에 해당하는 게시글의 상세 정보를 조회하여 HTML 형식으로 출력합니다.
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
      int boardNo = Integer.parseInt(req.getParameter("no"));
      Board board = boardDao.findBy(boardNo);
      req.setAttribute("board", board);

      if (board != null) {
        board.setViewCount(board.getViewCount() + 1);
        boardDao.updateViewCount(board.getNo(), board.getViewCount());
        sqlSessionFactory.openSession(false).commit();
      }
      res.setContentType("text/html;charset=UTF-8");
      req.getRequestDispatcher("/board/view.jsp").include(req, res);

    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
