package bitcamp.myapp.controller;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.User;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : bitcamp.myapp.servlet
 * fileName       : DownloadServlet
 * author         : narilee
 * date           : 24. 9. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 9.        narilee       최초 생성
 * 24. 9. 23.        narilee       @Controller 적용
 * 24. 9. 25.        narilee       Spring 도입
 */
@Controller
public class DownloadController {

  private BoardService boardService;
  private Map<String, String> downloadPathMap = new HashMap<>();

  public DownloadController(BoardService boardService, ServletContext ctx) {
    this.boardService = boardService;
    this.downloadPathMap.put("board", ctx.getRealPath("/upload/board"));
    this.downloadPathMap.put("user", ctx.getRealPath("/upload/user"));
    this.downloadPathMap.put("project", ctx.getRealPath("/upload/project"));
  }

  @GetMapping("/download")
  public HttpHeaders download(
      String path,
      int fileNo,
      HttpSession session,
      OutputStream out) throws Exception {

    HttpHeaders headers = new HttpHeaders();

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인 하지 않았습니다.");
    }

    String downloadDir = downloadPathMap.get(path);

    if (path.equals("board")) {
      AttachedFile attachedFile = boardService.getAttachedFile(fileNo);

      headers.add("Content-Disposition",
          String.format("attachment; filename=\"%s\"", attachedFile.getOriginFilename()));

      BufferedInputStream downloadFileIn = new BufferedInputStream(
          new FileInputStream(downloadDir + "/" + attachedFile.getFilename()));

      int b;
      while ((b = downloadFileIn.read()) != -1) {
        out.write(b);
      }

      downloadFileIn.close();


    } else if (path.equals("user")) {

    } else {

    }

    return headers;
  }
}
