package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectUpdateServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 */
@WebServlet("/project/update")
  public class ProjectUpdateServlet extends GenericServlet {

  /** Project 엔티티에 대한 데이터 액세스 객체입니다. */
  private ProjectDao projectDao;

  /** 데이터베이스 세션을 괸라하는 SqlSessionFactory 객체입니다. */
  private SqlSessionFactory sqlSessionFactory;

  /**
   * 서블릿 객체를 초기화합니다.
   * 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의헤 호출됩니다.
   * ProjectDao와 SqlSessionFactory 객체를 ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    this.projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
    this.sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {

    try {
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

      if (!projectDao.update(project)) {
        throw new Exception("없는 프로젝트입니다!");
      }

      projectDao.deleteMembers(project.getNo());
      if (project.getMembers() != null && project.getMembers().size() > 0) {
        projectDao.insertMembers(project.getNo(), project.getMembers());
      }
      sqlSessionFactory.openSession(false).commit();
      ((HttpServletResponse) res).sendRedirect("/project/list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
