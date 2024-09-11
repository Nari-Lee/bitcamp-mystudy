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
 * fileName       : BoardFileDeleteServlet
 * author         : narilee
 * date           : 24. 9. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 9.         narilee       최초 생성
 * 24. 9. 11.        narilee       BoardService 적용
 */
@WebServlet("/board/file/delete")
  public class BoardFileDeleteServlet extends HttpServlet {

  private BoardService boardService;
  private String uploadDir;

  @Override
  public void init() throws ServletException {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
    uploadDir = getServletContext().getRealPath("/upload/board");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    try {
      User loginUser = (User) req.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인 하지 않았습니다.");
      }

      int fileNo = Integer.parseInt(req.getParameter("fileNo"));
      AttachedFile attachedFile = boardService.getAttachedFile(fileNo);
      if (attachedFile == null) {
        throw new Exception("없는 첨부파일입니다.");
      }

      Board board = boardService.get(attachedFile.getBoardNo());
      if (loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      File file = new File(uploadDir + "/" + attachedFile.getFilename());
      if (file.exists()) {
        file.delete();
      }
      boardService.deleteAttachedFile(fileNo);
      res.sendRedirect("/board/view?no=" + req.getParameter("boardNo"));

    } catch (Exception e) {
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
