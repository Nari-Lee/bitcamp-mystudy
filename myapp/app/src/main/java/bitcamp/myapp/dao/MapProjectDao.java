package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapProjectDao implements ProjectDao {

  private static final String DEFAULT_DATANAME = "projects";
  private int seqNo;
  private Map<Integer, Project> projectMap = new HashMap<>();
  private List<Integer> projectNoList = new ArrayList<>();
  private String path;
  private String dataName;
  private MapUserDao userDao;

  public MapProjectDao(String path, MapUserDao userDao) {
    this(path, DEFAULT_DATANAME, userDao);
  }

  public MapProjectDao(String path, String dataName, MapUserDao userDao) {

    this.path = path;
    this.dataName = dataName;
    this.userDao = userDao;

    try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
      XSSFSheet sheet = workbook.getSheet(dataName);

      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        try {
          Project project = new Project();
          project.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
          project.setTitle(row.getCell(1).getStringCellValue());
          project.setDescription(row.getCell(2).getStringCellValue());
          project.setStartDate(row.getCell(3).getStringCellValue());
          project.setEndDate(row.getCell(4).getStringCellValue());

          String[] members = row.getCell(5).getStringCellValue().split(",");
          for (String memberNo : members) {
            User member = userDao.findBy(Integer.valueOf(memberNo));
            if (member != null) {
              project.getMembers().add(member);
            }
          }
          projectMap.put(project.getNo(), project);
          projectNoList.add(project.getNo());

        } catch (Exception e) {
          System.out.printf("%s 번 프로젝트의 데이터 형식이 맞지 않습니다.\n", row.getCell(0).getStringCellValue());
        }
      }

      seqNo = projectNoList.getLast();

    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public void save() throws Exception {

    try (FileInputStream in = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(in)) {

      int sheetIndex = workbook.getSheetIndex(dataName);
      if (sheetIndex != -1) {
        workbook.removeSheetAt(sheetIndex);
      }

      XSSFSheet sheet = workbook.createSheet(dataName);

      // 셀 이름 출력
      String[] cellHeaders = {"no", "title", "description", "start_date", "end_date", "members"};
      Row headerRow = sheet.createRow(0);
      for (int i = 0; i < cellHeaders.length; i++) {
        headerRow.createCell(i).setCellValue(cellHeaders[i]);
      }

      // 데이터 저장
      int rowNo = 1;
      for (Integer projectNo : projectNoList) {
        Project project = projectMap.get(projectNo);
        Row dataRow = sheet.createRow(rowNo++);
        dataRow.createCell(0).setCellValue(String.valueOf(project.getNo()));
        dataRow.createCell(1).setCellValue(project.getTitle());
        dataRow.createCell(2).setCellValue(project.getDescription());
        dataRow.createCell(3).setCellValue(project.getStartDate());
        dataRow.createCell(4).setCellValue(project.getEndDate());

        StringBuilder strBuilder = new StringBuilder();
        for (User member : project.getMembers()) {
          if (strBuilder.length() > 0) {
            strBuilder.append(",");
          }
          strBuilder.append(member.getNo());
        }
        dataRow.createCell(5).setCellValue(strBuilder.toString());
      }
      try (FileOutputStream out = new FileOutputStream(path)) {
        workbook.write(out);
      }
    }
  }

  @Override
  public boolean insert(Project project) throws Exception {
    project.setNo(++seqNo);
    projectMap.put(project.getNo(), project);
    projectNoList.add(project.getNo());
    return true;
  }

  @Override
  public List<Project> list() throws Exception {
    ArrayList<Project> projects = new ArrayList<>();
    for (Integer projectNo : projectNoList) {
      projects.add(projectMap.get(projectNo));
    }
    return projects;
  }

  @Override
  public Project findBy(int no) throws Exception {
    return projectMap.get(no);
  }

  @Override
  public boolean update(Project project) throws Exception {
    if (!projectMap.containsKey(project.getNo())) {
      return false;
    }
    projectMap.put(project.getNo(), project);
    return true;
  }

  @Override
  public boolean delete(int no) throws Exception {
    if (projectMap.remove(no) == null) {
      return false;
    }
    projectNoList.remove(Integer.valueOf(no));
    return true;
  }

  @Override
  public void addMembers(Project project) throws Exception {
    while (true) {
      int userNo = Prompt.inputInt("추가할 팀원 번호?(종료: 0)");
      if (userNo == 0) {
        break;
      }

      User user;
      try {
        user = userDao.findBy(userNo);
      } catch (Exception e) {
        System.out.println("데이터를 가져가는 중 오류가 발생했습니다.");
        e.printStackTrace();
        continue;
      }

      if (user == null) {
        System.out.println("없는 팀원입니다.");
        continue;
      }

      if (project.getMembers().contains(user)) {
        System.out.printf("'%s'은 현재 팀원입니다.\n", user.getName());
        continue;
      }

      project.getMembers().add(user);
      System.out.printf("'%s'을 추가했습니다.\n", user.getName());
    }
  }

  @Override
  public void deleteMembers(Project project) throws Exception {
    Object[] members = project.getMembers().toArray();
    for (Object obj : members) {
      User member = (User) obj;
      String str = Prompt.input("팀원(%s) 삭제?", member.getName());
      if (str.equalsIgnoreCase("y")) {
        project.getMembers().remove(member);
        System.out.printf("'%s' 팀원을 삭제합니다.\n", member.getName());
      } else {
        System.out.printf("'%s' 팀원을 유지합니다.\n", member.getName());
      }
    }
  }
}
