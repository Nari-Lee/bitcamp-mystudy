package bitcamp.myapp.command.project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.util.Prompt;

public class ProjectAddCommand implements Command {

  private ProjectDao projectDao;

  public ProjectAddCommand(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @Override
  public void execute(String menuName) {
    System.out.printf("[%s]\n", menuName);

    Project project = new Project();
    project.setTitle(Prompt.input("프로젝트명?"));
    project.setDescription(Prompt.input("설명?"));
    project.setStartDate(Prompt.input("시작일?"));
    project.setEndDate(Prompt.input("종료일?"));

    System.out.println("팀원:");
    projectDao.addMembers(project);

    project.setNo(Project.getNextSeqNo());

    projectDao.put(project.getNo(), project);
    projectNoList.add(project.getNo());

    try {
      projectDao.insert(project);
    } catch (Exception e) {
      System.out.println("프로젝트 등록 중 오류 발생!");
    }
  }
}
