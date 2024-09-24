package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.Controller;
import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.auth
 * fileName       : LoginServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 사용자의 로그인을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자의 이메일과 비밀번호를 검증하고,
 *                  로그인 성공시 세션에 사용자 정보를 저장합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 9. 11.        narilee       service 적용
 * 24. 9. 13.        narilee       cookie 처리 수정
 * 24. 9. 13.        narilee       Servlet을 Controller로 전환후 통합
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 * 24. 9. 23.        narilee       @Controller 적용
 */
@Controller
public class AuthController {

  /** User 엔티티에 대한 테이터 엑세스 객체입니다. */
  private UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/auth/form")
  public String form() throws Exception {
    return "auth/form.jsp";
  }

  @RequestMapping("/auth/login")
  public String login(
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("saveEmail") boolean saveEmail,
      HttpServletResponse res,
      HttpSession session
  ) throws Exception {

    User user = userService.exists(email, password);
    if (user == null) {
      res.setHeader("Refresh", "2; url=login");
      return "/auth/fail.jsp";
    }

    if (saveEmail) {
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 7);
      res.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "test@test.com");
      cookie.setMaxAge(0);
      res.addCookie(cookie);
    }

    session.setAttribute("loginUser", user);
    return "redirect:/";
  }


  @RequestMapping("/auth/logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/";
  }
}
