package bitcamp.myapp.command.project;

import bitcamp.myapp.dao.UserDao;

public class ProjectMemberHandler {

  private UserDao userDao;

  public ProjectMemberHandler(UserDao userDao) {
    this.userDao = userDao;
  }


}
