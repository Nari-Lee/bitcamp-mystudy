package bitcamp.context;

import bitcamp.menu.MenuGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : bitcamp.context
 * fileName       : ApplicationContext
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : 애플리케이션의 컨텍스트를 관리하는 클래스입니다.
 *                  이 클래스는 메인 메뉴와 객체 컨테이너를 포함하며, 이들에 대한 접근 방법을 제공합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
public class ApplicationContext {

  /** 애플리케이션 메인 메뉴 */
  MenuGroup mainMenu = new MenuGroup("메인");

  /** 객체들을 저장하는 컨테이너 */
  Map<String, Object> objContainer = new HashMap<>();

  /**
   * 메인 메뉴를 반환합니다.
   *
   * @return 애플리케이션의 메인 메뉴
   */
  public MenuGroup getMainMenu() {
    return mainMenu;
  }

  /**
   * 지정된 이름으로 객체를 컨테이너에 저장합니다.
   *
   * @param name 객체의 이름
   * @param value 저장할 객체
   */
  public void setAttribute(String name, Object value) {
    objContainer.put(name, value);
  }

  /**
   * 지정된 이름의 객체를 컨테이너에서 조회합니다.
   *
   * @param name 객체의 이름
   * @return 조회된 객체, 없으면 null
   */
  public Object getAttribute(String name) {
    return objContainer.get(name);
  }
}
