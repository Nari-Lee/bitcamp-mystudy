package bitcamp.myapp.service;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : bitcamp.myapp.service
 * fileName       : DefaultProjectService
 * author         : narilee
 * date           : 24. 9. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 12.        narilee       최초 생성
 * 24. 9. 24.        narilee       @Component 적용
 * 24. 9. 26.        narilee       @Transactional 적용
 */
@Component
public class DefaultProjectService implements ProjectService {
  private ProjectDao projectDao;

  public DefaultProjectService(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @Transactional
  public void add(Project project) throws Exception {
      projectDao.insert(project);

      if (project.getMembers() != null && project.getMembers().size() > 0) {
        projectDao.insertMembers(project.getNo(), project.getMembers());
      }
    }

  public List<Project> list() throws Exception {
    return projectDao.list();
  }

  public Project get(int projectNo) throws Exception {
    return projectDao.findBy(projectNo);
  }

  @Transactional
  public boolean update(Project project) throws Exception {
      if (!projectDao.update(project)) {
        return false;
      }
      projectDao.deleteMembers(project.getNo());
      if (project.getMembers() != null && project.getMembers().size() > 0) {
        projectDao.insertMembers(project.getNo(), project.getMembers());
      }
      return true;
    }

  @Transactional
  public boolean delete(int projectNo) throws Exception {
      projectDao.deleteMembers(projectNo);
      if (!projectDao.delete(projectNo)) {
        return false;
      }
      return true;
  }
}
