package bitcamp.myapp.servlet.project;

import bitcamp.myapp.dao.ProjectDao;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName    : bitcamp.myapp.servlet.project
 * fileName       : ProjectDeleteServlet
 * author         : narilee
 * date           : 24. 8. 29.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 29.        narilee       최초 생성
 */
@WebServlet("/project/delete")
public class ProjectDeleteServlet extends GenericServlet {

  /** Project 엔티티에 대한 데이터 액세스 객체입니다. */
  private ProjectDao projectDao;

  /** 데이터베이스 세션을 괸라하는 SqlSessionFactory 객체입니다. */
  private SqlSessionFactory sqlSessionFactory;

  /**
   * 서블릿 객체를 초기화합니다. 이 메서드는 서블릿이 배치될 때 서블릿 컨테이너에 의헤 호출됩니다. ProjectDao와 SqlSessionFactory 객체를
   * ServletContext에서 가져와 초기화합니다.
   *
   * @throws ServletException 서블릿 초기화 중 오류가 발생한 경우
   */
  @Override
  public void init() throws ServletException {
    this.projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
    this.sqlSessionFactory =
        (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
  }

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    try {
      int projectNo = Integer.parseInt(req.getParameter("no"));

      projectDao.deleteMembers(projectNo);
      if (projectDao.delete(projectNo)) {
        sqlSessionFactory.openSession(false).commit();
        ((HttpServletResponse) res).sendRedirect("/project/list");
      } else {
        throw new Exception("없는 프로젝트입니다.");
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, res);
    }
  }
}
