package bitcamp.myapp.vo;

public class Board {

  private String headline;
  private String contents;
  private int viewCount;
  private String writeDate;

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public void incrementViewCount() {
    this.viewCount++;
  }

  public String getWriteDate() {
    return writeDate;
  }

  public void setWriteDate(String writeDate) {
    this.writeDate = writeDate;
  }
}
