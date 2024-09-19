package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.controller
 * fileName       : UserController
 * author         : narilee
 * date           : 24. 8. 28.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 8. 30.        narilee       수정
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narilee       UTF-8 필터 적용
 * 24. 9. 11.        narilee       UserService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 * 24. 9. 13.        narilee       Servlet을 Controller로 전환후 통합
 */
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/user/add")
  public String add(HttpServletRequest req, HttpServletResponse res) throws Exception {
    if (req.getMethod().equals("GET")) {
      return "/user/form.jsp";

    } else {
      User user = new User();
      user.setName(req.getParameter("name"));
      user.setEmail(req.getParameter("email"));
      user.setPassword(req.getParameter("password"));
      user.setTel(req.getParameter("tel"));

      userService.add(user);
      return "redirect:list";
    }
  }

  @RequestMapping("/user/list")
  public String list(HttpServletRequest req, HttpServletResponse res) throws Exception {
    List<User> list = userService.list();
    req.setAttribute("list", list);
    return "/user/list.jsp";
  }

  @RequestMapping("/user/view")
  public String view(HttpServletRequest req, HttpServletResponse res) throws Exception {
    int userNo = Integer.parseInt(req.getParameter("no"));
    User user = userService.get(userNo);
    req.setAttribute("user", user);
    return "/user/view.jsp";
  }

  @RequestMapping("/user/update")
  public String update(HttpServletRequest req, HttpServletResponse res) throws Exception {
    User user = new User();
    user.setNo(Integer.parseInt(req.getParameter("no")));
    user.setName(req.getParameter("name"));
    user.setEmail(req.getParameter("email"));
    user.setPassword(req.getParameter("password"));
    user.setTel(req.getParameter("tel"));

    if (userService.update(user)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다!");
    }
  }

  @RequestMapping("/user/delete")
  public String delete(HttpServletRequest req, HttpServletResponse res) throws Exception {
    int userNo = Integer.parseInt(req.getParameter("no"));
    if (userService.delete(userNo)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다.");
    }
  }
}
