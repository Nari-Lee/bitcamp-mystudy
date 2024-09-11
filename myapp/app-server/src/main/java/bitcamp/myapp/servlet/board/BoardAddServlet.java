package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narileel      UTF-8 필터 적용, 첨부파일 적용
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 60, maxRequestSize = 1024 * 1024 * 100)
@WebServlet("/board/add")
  public class BoardAddServlet extends HttpServlet {

  /** Board 엔티티에 대한 대이터 액세스 객체입니다. */
  private BoardDao boardDao;

  /** 데이터베이스 세션을 관리하는 SqlSessionFactory 객체입니다. */
  private SqlSessionFactory sqlSessionFactory;

  private String uploadDir;
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
    this.uploadDir = ctx.getRealPath("/upload/board");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.getRequestDispatcher("/board/form.jsp").include(req, res);
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
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      User loginUser = (User) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인 하지 않았습니다.");
      }

      Board board = new Board();
      board.setWriter(loginUser);
      board.setTitle(req.getParameter("title"));
      board.setContent(req.getParameter("content"));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

      Collection<Part> parts = req.getParts();
      for (Part part : parts) {
        if (!part.getName().equals("files") || part.getSize() == 0) {
          continue;
        }

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFilename(UUID.randomUUID().toString());
        attachedFile.setOriginFilename(part.getSubmittedFileName());

        part.write(this.uploadDir + "/" + attachedFile.getFilename());

        attachedFiles.add(attachedFile);
      }

      board.setAttachedFiles(attachedFiles);

      boardDao.insert(board);
      if (board.getAttachedFiles().size() > 0) {
        boardDao.insertFiles(board);
      }

      sqlSessionFactory.openSession(false).commit();
      res.sendRedirect("/board/list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }

}
