package bitcamp.menu;

import bitcamp.net.Prompt;

import java.util.ArrayList;
import java.util.Stack;

/**
 * packageName    : bitcamp.menu
 * fileName       : MenuGroup
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 메뉴 그룹을 나타내는 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
public class MenuGroup extends AbstractMenu {

  private MenuGroup parent;
  private ArrayList<Menu> children = new ArrayList<>();
  private String exitMenuTitle = "이전";

  /**
   * MenuGroup 객체를 생성합니다.
   *
   * @param title 메뉴 그룹의 제목
   */
  public MenuGroup(String title) {
    super(title);
  }

  /**
   * 메뉴 그룹을 실행합니다. 사용자의 입력을 받아 해당하는 메뉴를 실행합니다.
   *
   * @param prompt 사용자 입출력을 처리하는 Prompt 객체
   */
  @Override
  public void execute(Prompt prompt) {
    try {
      String menuPath = getMenuPath();

      while (true) {
        printMenus(prompt);
        String command = prompt.input("%s>", menuPath);
        if (command.equals("menu")) {
          continue;
        } else if (command.equals("0")) {
          return;
        }

        try {
          int menuNo = Integer.parseInt(command);
          Menu menu = getMenu(menuNo - 1);
          if (menu == null) {
            prompt.println("유효한 메뉴 번호가 아닙니다.");
            continue;
          }
          menu.execute(prompt);
        } catch (NumberFormatException ex) {
          prompt.println("숫자로 메뉴 번호를 입력하세요.");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      prompt.println("실행 오류!");
    }
  }

  /**
   * 종료 메뉴의 제목을 설정합니다.
   *
   * @param title 설정할 종료 메뉴 제목
   */
  public void setExitMenuTitle(String title) {
    exitMenuTitle = title;
  }

  /**
   * 현재 메뉴 그룹의 모든 하위 메뉴를 출력합니다.
   *
   * @param prompt 사용자 입출력을 처리하는 Prompt 객체
   */
  private void printMenus(Prompt prompt) {
    prompt.printf("[%s]\n", title);
    int i = 1;
    for (Menu menu : children) {
      prompt.printf("%d. %s\n", i++, menu.getTitle());
    }
    prompt.printf("0. %s\n", exitMenuTitle);
  }

  /**
   * 현재 메뉴 그룹의 전체 경로를 문자열로 반환합니다.
   *
   * @return 메뉴 경로 문자열
   */
  private String getMenuPath() {
    Stack<String> menuPathStack = new Stack<>();
    MenuGroup menuGroup = this;
    while (menuGroup != null) {
      menuPathStack.push(menuGroup.title);
      menuGroup = menuGroup.parent;
    }

    StringBuilder strBuilder = new StringBuilder();
    while (!menuPathStack.isEmpty()) {
      if (strBuilder.length() > 0) {
        strBuilder.append("/");
      }
      strBuilder.append(menuPathStack.pop());
    }

    return strBuilder.toString();
  }

  /**
   * 부모 메뉴 그룹을 설정합니다.
   *
   * @param parent 설정할 부모 MenuGroup 객체
   */
  private void setParent(MenuGroup parent) {
    this.parent = parent;
  }

  /**
   * 하위 메뉴를 추가합니다.
   *
   * @param child 추가할 Menu 객체
   */
  public void add(Menu child) {
    if (child instanceof MenuGroup) {
      ((MenuGroup) child).setParent(this);
    }
    children.add(child);
  }

  /**
   * 하위 메뉴를 제거합니다.
   *
   * @param child 제거할 Menu 객체
   */
  public void remove(Menu child) {
    children.remove(child);
  }

  /**
   * 지정된 인덱스의 하위 메뉴를 반환합니다.
   *
   * @param index 가져올 메뉴의 인덱스
   * @return 해당 인덱스의 Menu 객체, 없으면 null
   */
  public Menu getMenu(int index) {
    if (index < 0 || index >= children.size()) {
      return null;
    }
    return children.get(index);
  }

  /**
   * 현재 메뉴 그룹에 포함된 하위 메뉴의 개수를 반환합니다.
   *
   * @return 하위 메뉴의 개수
   */
  public int countMenus() {
    return children.size();
  }
}
