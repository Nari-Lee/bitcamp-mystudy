package bitcamp.menu;

import bitcamp.command.Command;
import bitcamp.net.Prompt;

/**
 * packageName    : bitcamp.menu
 * fileName       : MenuItem
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 메뉴 항목을 나타내는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
public class MenuItem extends AbstractMenu {

  /** 메뉴 항목과 연관된 Command 객체 */
  private Command command;

  /**
   * 제목만 있는 MenuItem 객체를 생성합니다.
   *
   * @param title 메뉴 항목의 제목
   */
  public MenuItem(String title) {
    super(title);
  }

  /**
   * 제목과 Command 객체를 가진 MenuItem 객체를 생성합니다.
   *
   * @param title 메뉴 항목의 제목
   * @param command 메뉴 항목과 연관된 Command 객체
   */
  public MenuItem(String title, Command command) {
    super(title);
    this.command = command;
  }

  /**
   * MenuItem의 Command 객체를 설정합니다.
   *
   * @param command 설정할 Command 객체
   */
  public void setCommand(Command command) {
    this.command = command;
  }

  /**
   * MenuItem을 실행합니다.
   * Command 객체가 설정되어 있다면 해당 Command를 실행하고,
   * 그렇지 않다면 메뉴 항목의 제목을 출력합니다.
   *
   * @param prompt 사용자 입출력을 처리하는 Prompt 객체
   */
  @Override
  public void execute(Prompt prompt) {
    if (command != null) {
      command.execute(title, prompt);
    } else {
      prompt.println(title);
    }
  }
}
