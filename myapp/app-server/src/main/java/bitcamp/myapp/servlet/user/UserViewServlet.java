package bitcamp.myapp.servlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : bitcamp.myapp.servlet.user
 * fileName       : UserViewServlet
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 사용자 상세 정보를 표시하는 서블릿 클래스입니다.
 *                  이 서블릿은 '/user/view' URL 패턴에 매핑되어 있으며,
 *                  요청 파라미터로 받은 사용자 번호에 해당하는 사용자의 상세 정보를
 *                  데이터베이스에서 조회하여 HTML 형식으로 출력합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
@WebServlet("/user/view")
public class UserViewServlet extends GenericServlet {

  /** User 엔티티에 대한 테이터 엑세스 객체입니다. */
  private UserDao userDao;

  /**
   * 서블릿 객체를 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의해 호출됩니다.
   * UserDao 객체를 ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    // 서블릿 컨테이너 ---> init(ServletConfig) ---> init() 호출합니다.
    userDao = (UserDao) this.getServletContext().getAttribute("userDao");
  }

  /**
   * 클라이언트의 요청을 처리하고 응답을 생성합니다.
   * 이 메서드는 요청 파라미터로 받은 사용자 번호에 해당하는 사용자의 상세 정보를 조회하여 HTML 형식으로 출력합니다.
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
      out.println("<h1>회원 조회</h1>");
      int userNo = Integer.parseInt(req.getParameter("no"));

      User user = userDao.findBy(userNo);
      if (user == null) {
        out.println("<p>없는 회원입니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      out.printf("<p>이름: %s</p>\n", user.getName());
      out.printf("<p>이메일: %s</p>\n", user.getEmail());
      out.printf("<p>연락처: %s</p>\n", user.getTel());

    } catch (Exception e) {
      out.println("<p>조회 중 오류 발생!</p>");
    }
  }
}
