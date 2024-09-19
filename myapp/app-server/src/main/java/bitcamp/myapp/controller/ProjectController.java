package bitcamp.myapp.controller;

import bitcamp.myapp.annotation.RequestMapping;
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
  public String form2(HttpServletRequest req, HttpServletResponse res)
      throws Exception {
      Project project = new Project();
      project.setTitle(req.getParameter("title"));
      project.setDescription(req.getParameter("description"));
      project.setStartDate(Date.valueOf(req.getParameter("startDate")));
      project.setEndDate(Date.valueOf(req.getParameter("endDate")));

      HttpSession session = req.getSession();
      session.setAttribute("project", project);

      List<User> users = userService.list();
      req.setAttribute("users", users);
      return "project/form2.jsp";
  }

  @RequestMapping("/project/form3")
  public String form3(HttpServletRequest req, HttpServletResponse res)
      throws Exception {
      Project project = (Project) req.getSession().getAttribute("project");

      String[] memberNos = req.getParameterValues("member");
      if (memberNos != null) {
        ArrayList<User> members = new ArrayList<>();
        for (String memberNo : memberNos) {
          User user = userService.get(Integer.parseInt(memberNo));
          members.add(user);
        }
        project.setMembers(members);
      }

      return "project/form3.jsp";
    }

  @RequestMapping("/project/add")
  public String add(HttpServletRequest req, HttpServletResponse res)
      throws Exception {

      Project project = (Project) req.getSession().getAttribute("project");
      projectService.add(project);
      // 세션에 임시 보간했던 Project 객치를 제거한다.
      req.getSession().removeAttribute("project");
      return "redirect:list";
  }

  @RequestMapping("/project/list")
  public String list(HttpServletRequest req, HttpServletResponse res)
      throws Exception {
      List<Project> list = projectService.list();
      req.setAttribute("list", list);
      return "project/list.jsp";
  }

  @RequestMapping("/project/view")
  public String view(HttpServletRequest req, HttpServletResponse res)
      throws Exception {
      int projectNo = Integer.parseInt(req.getParameter("no"));
      Project project = projectService.get(projectNo);
      req.setAttribute("project", project);

      List<User> users = userService.list();
      req.setAttribute("users", users);
      return "project/view.jsp";
  }

  @RequestMapping("/project/update")
  public String update(HttpServletRequest req, HttpServletResponse res) throws Exception {

      Project project = new Project();
      project.setNo(Integer.parseInt(req.getParameter("no")));
      project.setTitle(req.getParameter("title"));
      project.setDescription(req.getParameter("description"));
      project.setStartDate(Date.valueOf(req.getParameter("startDate")));
      project.setEndDate(Date.valueOf(req.getParameter("endDate")));

      String[] memberNos = req.getParameterValues("member");
      if (memberNos != null) {
        ArrayList<User> members = new ArrayList<>();
        for (String memberNo : memberNos) {
          members.add(new User(Integer.parseInt(memberNo)));
        }
        project.setMembers(members);
      }

      if (!projectService.update(project)) {
        throw new Exception("없는 프로젝트입니다!");
      }
      return "redirect:list";
  }

  @RequestMapping("/project/delete")
  public String delete(HttpServletRequest req, HttpServletResponse res) throws Exception {
      int projectNo = Integer.parseInt(req.getParameter("no"));

      if (projectService.delete(projectNo)) {
        req.setAttribute("viewName", "redirect:list");
      } else {
        throw new Exception("없는 프로젝트입니다.");
      }
      return "redirect:list";
  }
}
