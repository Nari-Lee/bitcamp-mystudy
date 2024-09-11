package bitcamp.myapp.servlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.board
 * fileName       : BoardDeleteServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narilee       첨부파일 삭제 추가
 * 24. 9. 11.        narilee       BoardService 적용
 */
@WebServlet("/board/delete")
  public class BoardDeleteServlet extends HttpServlet {

  private BoardService boardService;
  private String uploadDir;

  @Override
  public void init() throws ServletException {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
    uploadDir = getServletContext().getInitParameter("uploadDir");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      User loginUser = (User) req.getSession().getAttribute("loginUser");
      int boardNo = Integer.parseInt(req.getParameter("no"));
      Board board = boardService.get(boardNo);

      if (board == null) {
        throw new Exception("없는 게시글입니다.");
      } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      for (AttachedFile attachedFile : board.getAttachedFiles()) {
        File file = new File(uploadDir + "/" + attachedFile.getFilename());
        if (file.exists()) {
          file.delete();
        }
      }

      boardService.delete(boardNo);
      res.sendRedirect("/board/list");

    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
