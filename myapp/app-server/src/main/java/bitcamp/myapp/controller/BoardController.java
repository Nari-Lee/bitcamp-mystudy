package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
 * 24. 9. 09.        narilee       UTF-8 필터 적용, 첨부파일 적용
 * 24. 9. 11.        narilee       BoardService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 */
public class BoardController {

  private BoardService boardService;
  private String uploadDir;

  public BoardController(BoardService boardService, ServletContext ctx) {
    this.boardService = boardService;
    this.uploadDir = ctx.getRealPath("/upload/board");
  }

  @RequestMapping("/board/form")
  public String form() throws Exception {
      return "/board/form.jsp";
    }


  @RequestMapping("/board/add")
  public String add(Board board,
      @RequestParam("files") Part[] parts,
      HttpSession session) throws Exception {

      User loginUser = (User) session.getAttribute("loginUser");
      if (loginUser == null) {
        throw new Exception("로그인 하지 않았습니다.");
      }

      board.setWriter(loginUser);

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      for (Part part : parts) {
        if (part.getSize() == 0) {
          continue;
        }

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFilename(UUID.randomUUID().toString());
        attachedFile.setOriginFilename(part.getSubmittedFileName());
        part.write(this.uploadDir + "/" + attachedFile.getFilename());
        attachedFiles.add(attachedFile);
      }

      board.setAttachedFiles(attachedFiles);

      boardService.add(board);
      return "redirect:list";
    }

  @RequestMapping("/board/list")
  public String list(Map<String, Object> map) throws Exception {
    List<Board> list = boardService.list();
    map.put("boards", list);
    return "/board/list.jsp";
  }

  @RequestMapping("/board/view")
  public String view(@RequestParam("no") int boardNo, Map<String, Object> map) throws Exception {
    Board board = boardService.get(boardNo);
    if (board == null) {
      throw new Exception("게시글이 존재하지 않습니다.");
    }

    boardService.increaseViewCount(board.getNo());
    map.put("board", board);
    return "/board/view.jsp";
  }

  @RequestMapping("/board/update")
  public String update(
      HttpSession session,
      @RequestParam("no") int boardNo,
      @RequestParam("title") String title,
      @RequestParam("content") String content,
      @RequestParam("files") Part[] parts) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");

    Board board = boardService.get(boardNo);
    if (board == null) {
      throw new Exception("없는 게시글입니다.");
    } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다.");
    }

    board.setTitle(title);
    board.setContent(content);

    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

    for (Part part : parts) {
      if (part.getSize() == 0) {
        continue;
      }

      AttachedFile attachedFile = new AttachedFile();
      attachedFile.setFilename(UUID.randomUUID().toString());
      attachedFile.setOriginFilename(part.getSubmittedFileName());

      part.write(this.uploadDir + "/" + attachedFile.getFilename());

      attachedFiles.add(attachedFile);
    }

    board.setAttachedFiles(attachedFiles);

    boardService.update(board);
    return "redirect:list";
  }

  @RequestMapping("/board/delete")
  public String delete(
      HttpSession session,
      @RequestParam("no") int boardNo) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
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
    return "redirect:list";
  }

  @RequestMapping("/board/file/delete")
  public String fileDelete(
      HttpSession session,
      @RequestParam("fileNo") int fileNo,
      @RequestParam("boardNo") int boardNo) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인 하지 않았습니다.");
    }

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
    return "redirect:../view?no=" + "boardNo";
  }

}
