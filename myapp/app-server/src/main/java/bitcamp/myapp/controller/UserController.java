package bitcamp.myapp.controller;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 * 24. 9. 23.        narilee       @Controller 적용
 * 24. 9. 25.        narilee       Spring 도입
 */
@Controller
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user/form")
  public String form() {
    return "user/form";
  }

  @PostMapping("/user/add")
  public String add(User user) throws Exception {
    userService.add(user);
    return "redirect:list";
  }

  @GetMapping("/user/list")
  public String list(Model model) throws Exception {
    List<User> list = userService.list();
    model.addAttribute("list", list);
    return "user/list";
  }

  @GetMapping("/user/view")
  public String view(int no, Model model) throws Exception {
    User user = userService.get(no);
    model.addAttribute("user", user);
    return "user/view";
  }

  @PostMapping("/user/update")
  public String update(User user) throws Exception {
    if (userService.update(user)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다!");
    }
  }

  @GetMapping("/user/delete")
  public String delete(int no) throws Exception {
    if (userService.delete(no)) {
      return "redirect:list";
    } else {
      throw new Exception("없는 회원입니다.");
    }
  }
}
