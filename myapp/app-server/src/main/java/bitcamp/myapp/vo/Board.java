package bitcamp.myapp.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * packageName    : bitcamp.myapp.vo
 * fileName       : Board
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 게시판의 기본 정보를 포함하는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 * 24. 9. 09.        narilee       파일 추가
 */
public class Board implements Serializable {

  /**
   *데이터베이스와의 연동을 위해 Serializable을 구현
   */
  private static final long serialVersionUID = 1L;

  /** 게시물 번호 */
  private int no;
  /** 게시물 제목 */
  private String title;
  /** 게시물 내용 */
  private String content;
  /** 게시물 작성자 */
  private User writer;
  /** 게시물 작성일 */
  private Date createdDate;
  /** 게시물 조회수 */
  private int viewCount;
  /** 첨부 파일 */
  private List<AttachedFile> attachedFiles;

  /**
   * 기본 생성자입니다.
   */
  public Board() {
  }

  /**
   * 게시물 번호를 인자로 받는 생성자입니다.
   *
   * @param no 게시물 번호
   */
  public Board(int no) {
    this.no = no;
  }

  /**
   * 객체를 문자열로 변환하여 반환합니다.
   *
   * @return 객체의 문자열
   */
  @Override
  public String toString() {
    return "Board{" +
        "no=" + no +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", createdDate=" + createdDate +
        ", viewCount=" + viewCount +
        '}';
  }

  /**
   * 이 객체와 다른 객체가 동등한지 비교합니다.
   * 게시물 번호만을 기준으로 동등성을 판단합니다.
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
    Board board = (Board) o;
    return no == board.no;
  }

  /**
   * 객체의 해시코드를 반환합니다.
   * 게시물 번호를 기준으로 해시코드를 생성합니다.
   *
   * @return 객체의 해시코드
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(no);
  }

  /**
   * 게시물 번호를 반환합니다.
   *
   * @return 게시물 번호
   */
  public int getNo() {
    return no;
  }

  /**
   * 게시물 번호를 설정합니다.
   *
   * @param no 게시물 번호
   */
  public void setNo(int no) {
    this.no = no;
  }

  /**
   * 게시물 제목을 반환합니다.
   *
   * @return 게시물 제목
   */
  public String getTitle() {
    return title;
  }

  /**
   * 게시물 제목을 설정합니다.
   *
   * @param title 게시물 제목
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 게시물 내용을 반환합니다.
   *
   * @return 게시물 내용
   */
  public String getContent() {
    return content;
  }

  /**
   * 게시물 내용을 설정합니다.
   *
   * @param content 게시물 내용
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 게시물 작성일을 반환합니다.
   *
   * @return 게시물 작성일
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * 게시물 작성일을 설정합니다.
   *
   * @param createdDate 게시물 작성일
   */
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * 게시물 조회수를 반환합니다.
   *
   * @return 게시물 조회수
   */
  public int getViewCount() {
    return viewCount;
  }

  /**
   * 게시물 조회수를 설정합니다.
   *
   * @param viewCount 게시물 조회수
   */
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  /**
   * 게시물 작성자를 반환합니다.
   *
   * @return 게시물 작성자
   */
  public User getWriter() {
    return writer;
  }

  /**
   * 게시물 작성자를 설정합니다.
   *
   * @param writer 게시물 작성자
   */
  public void setWriter(User writer) {
    this.writer = writer;
  }

  public List<AttachedFile> getAttachedFiles() {
    return attachedFiles;
  }

  public void setAttachedFiles(List<AttachedFile> attachedFiles) {
    this.attachedFiles = attachedFiles;
  }
}
