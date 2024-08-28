package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectFormServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 프로젝트 등록 폼을 생성하는 서블릿 클래스입니다.
 *                  이 서블릿은 프로젝트 등록에 필요한 입력 폼을 HTML 형식으로 생성하여 응답합니다.
 *                  폼에는 프로젝트 제목, 설명, 시작일, 종료일 그리고 팀원 선택 목록이 포함됩니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 */
@WebServlet("/project/form")
public class ProjectFormServlet extends GenericServlet{

  /** User 엔티티에 대한 데이터 액세스 객체입니다. */
  private UserDao userDao;

  /**
   * 서블릿 객체르 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의해 호출됩니다.
   * UserDao 객체를 ServletContext에서 가져와 초기화힙니다.
   *
   * @throws ServletException 서블릿 초기화중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    userDao = (UserDao) getServletContext().getAttribute("userDao");
  }

  /**
   * 클라이언트의 프로젝트 등록 폼 요청을 처리하고 응답을 생성합니다.
   * 이 메서드는 프로젝트 등록에 필요한 입력 필드와 사용자 목록을 포함한 HTML 폼을 생성합니다.
   * 생성된 폼은 '/project/add' 경로로 데이터를 전송하도록 설정됩니다.
   *
   * @param req 클라이언트의 요청이 포함된 ServletRequest 객체
   * @param res 서블릿의 응답을 포함하는 ServletResponse 객체
   *
   * @throws ServletException 서블릿 초기화중 오류가 발생하는 경우
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
      out.println("<h1>프로젝트 등록</h1>");
      out.println("<form action='/project/add'>");
      out.println("프로젝트: <input name='title' type='text'><br>");
      out.println("설명: <textarea name='description' type='text'></textarea><br>");
      out.println("시작일: <input name='startDate' type='date'><br>");
      out.println("종료일: <input name='endDate'' type='date'><br>");
      out.println("팀원: <br>");

      out.println("<ul>");
      List<User> users = userDao.list();
      for (User user : users) {
        out.printf("<li><input name='member' value='%d' type='checkbox'>%s</li>\n", user.getNo(),user.getName());
      }
      out.println("</ul>");
      out.println("<input type='submit' value='등록'>");
      out.println("</form>");

    } catch (Exception e) {
      out.println("<p>조회 중 오류 발생!</p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
