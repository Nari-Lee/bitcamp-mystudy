package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 */
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/user/form")
  public String form() throws Exception {
    return "user/form.jsp";
  }

  @RequestMapping("/user/add")
  public String add(User user) throws Exception {
    userService.add(user);
    return "redirect:list";
  }

  @RequestMapping("/user/list")
  public String list(Map<String, Object> map) throws Exception {
    List<User> list = userService.list();
    map.put("list", list);
    return "/user/list.jsp";
  }

  @RequestMapping("/user/view")
  public String view(@RequestParam("no") int no, Map<String, Object> map) throws Exception {
    User user = userService.get(no);
    map.put("user", user);
    return "/user/view.jsp";
  }

  @RequestMapping("/user/update")
  public String update(User user) throws Exception {
    if (userService.update(user)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다!");
    }
  }

  @RequestMapping("/user/delete")
  public String delete(@RequestParam("no") int no) throws Exception {
    if (userService.delete(no)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다.");
    }
  }
}
