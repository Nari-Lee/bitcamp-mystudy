package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;
import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectAddServlet
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 프로젝트 등록을 처리하는 서블릿 클래스입니다.
 *                  이 서블릿은 사용자가 입력한 프로젝트 정보를 받아 데이터베이스에 저장하고,
 *                  처리 결과를 HTML 형식으로 응답합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 * 24. 9. 05.        narilee       HttpServlet으로 변경
 * 24. 9. 09.        narilee       UTF-8 필터 적용, 첨부파일 추가
 * 24. 9. 11.        narilee       projectService 적용
 * 24. 9. 12.        narilee       DispatcherServlet 적용
 * 24. 9. 19.        narilee       HttpServletResponse 삭제, Param 변경
 */
public class ProjectController {

  /** Project 엔티티에 대한 데이터 액세스 객체입니다. */
  private ProjectService projectService;
  private UserService userService;

  public ProjectController(ProjectService projectService, UserService userService) {
    this.projectService = projectService;
    this.userService = userService;
  }

  @RequestMapping("/project/form1")
  public String form1(HttpServletRequest req, HttpServletResponse res)
      throws Exception {
    return "project/form1.jsp";
  }

  @RequestMapping("/project/form2")
  public String form2(Project project, HttpSession session, Map<String, Object> map)
      throws Exception {
      session.setAttribute("project", project);
      List<User> users = userService.list();
      map.put("users", users);
      return "project/form2.jsp";
  }

  @RequestMapping("/project/form3")
  public String form3(
      @RequestParam("member") int[] memberNos,
      HttpSession session
      )
      throws Exception {

      Project project = (Project) session.getAttribute("project");

      if (memberNos.length > 0) {
        ArrayList<User> members = new ArrayList<>();
        for (int memberNo : memberNos) {
          User user = userService.get(memberNo);
          members.add(user);
        }
        project.setMembers(members);
      }
      return "project/form3.jsp";
    }

  @RequestMapping("/project/add")
  public String add(HttpSession session)
      throws Exception {
      Project project = (Project) session.getAttribute("project");
      projectService.add(project);
      // 세션에 임시 보간했던 Project 객치를 제거한다.
      session.removeAttribute("project");
      return "redirect:list";
  }

  @RequestMapping("/project/list")
  public String list(Map<String, Object> map)
      throws Exception {
      List<Project> list = projectService.list();
      map.put("projects", list);
      return "project/list.jsp";
  }

  @RequestMapping("/project/view")
  public String view(@RequestParam("no") int projectNo, Map<String, Object> map)
      throws Exception {
      Project project = projectService.get(projectNo);
      map.put("project", project);

      List<User> users = userService.list();
      map.put("users", users);
      return "project/view.jsp";
  }

  @RequestMapping("/project/update")
  public String update(Project project, @RequestParam("member") int[] memberNos) throws Exception {

      if (memberNos.length > 0) {
        ArrayList<User> members = new ArrayList<>();
        for (int memberNo : memberNos) {
          members.add(new User(memberNo));
        }
        project.setMembers(members);
      }

      if (!projectService.update(project)) {
        throw new Exception("없는 프로젝트입니다!");
      }
      return "redirect:list";
  }

  @RequestMapping("/project/delete")
  public String delete(@RequestParam("no") int no) throws Exception {
      if (projectService.delete(no)) {
        return "redirect:list";
      } else {
        throw new Exception("없는 프로젝트입니다.");
      }
  }
}
