package bitcamp.myapp.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * packageName    : bitcamp.myapp.vo
 * fileName       : Project
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 프로젝트의 기본 정보를 포함하는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
public class Project implements Serializable {

  /**
   *데이터베이스와의 연동을 위해 Serializable을 구현
   */
  private static final long serialVersionUID = 1L;

  /** 프로젝트 번호 */
  private int no;
  /** 프로젝트 제목 */
  private String title;
  /** 프로젝트 설명 */
  private String description;
  /** 프로젝트 시작일 */
  private Date startDate;
  /** 프로젝트 종료일 */
  private Date endDate;
  /** 프로젝트 팀원*/
  private List<User> members;

  /**
   * 기본 생성자입니다.
   */
  public Project() {
  }

  /**
   * 프로젝트 번호를 인자로 받는 생성자입니다.
   *
   * @param no 프로젝트 번호
   */
  public Project(int no) {
    this.no = no;
  }

  /**
   * 이 객체와 다른 객체가 동등한지 비교합니다.
   * 프로젝트 번호만을 기준으로 동증성을 판단합니다.
   *
   * @param o 비교할 객체
   * @return 두 객체가 같으면 true, 그렇지 않으면 false
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Project project = (Project) o;
    return no == project.no;
  }

  /**
   * 객체의 해시코드를 반환합니다.
   * 프로젝트 번호를 기준으로 해시코드를 생성합니다.
   *
   * @return 객체의 해시코드
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(no);
  }

  /**
   * 프로젝트 번호를 반환합니다.
   *
   * @return 프로젝트 번호
   */
  public int getNo() {
    return no;
  }

  /**
   * 프로젝트 번호를 설정합니다.
   *
   * @param no 프로젝트 번혼
   */
  public void setNo(int no) {
    this.no = no;
  }

  /**
   * 프로젝트 제목을 반환합니다.
   *
   * @return 프로젝트 제목
   */
  public String getTitle() {
    return title;
  }

  /**
   * 프로젝트 제목을 설정합니다.
   *
   * @param title 프로젝트 제목
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 프로젝트 설명을 반환합니다.
   *
   * @return 프로젝트 설명
   */
  public String getDescription() {
    return description;
  }

  /**
   * 프로젝트 설명을 설정합니다.
   *
   * @param description 프로젝트 설명
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 프로젝트 시작일을 반환합니다.
   *
   * @return 프로젝트 시작일
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * 프로젝트 시작일을 설정합니다.
   *
   * @param startDate 프로젝트 시작일
   */
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  /**
   * 프로젝트 종료일을 반환합니다.
   *
   * @return 프로젝트 종료일
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * 프로젝트 종료일을 설정합니다.
   *
   * @param endDate 프로젝트 종료일
   */
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  /**
   * 프로젝트 팀원 목록을 반환합니다.
   *
   * @return 프로젝트 팀원 목록
   */
  public List<User> getMembers() {
    return members;
  }

  /**
   * 프로젝트 팀원 목록을 설정합니다.
   *
   * @param members 프로젝트 팀원 목록
   */
  public void setMembers(List<User> members) {
    this.members = members;
  }
}
