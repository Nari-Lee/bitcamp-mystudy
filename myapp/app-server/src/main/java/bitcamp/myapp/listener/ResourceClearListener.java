package bitcamp.myapp.listener;

import bitcamp.mybatis.SqlSessionFactoryProxy;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * packageName    : bitcamp.myapp.listener
 * fileName       : ResourceClearListener
 * author         : narilee
 * date           : 24. 8. 28.
 * description    : 각 요청 처리후 리소스를 정리하는 servletRequestListner 구현체입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 28.        narilee       최초 생성
 */
@WebListener
public class ResourceClearListener implements ServletRequestListener {
  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    System.out.println("요청 끝 => SqlSession.close()");
    // 응답을 완료하면, 클라이언트 요청을 처리하기 위해 준비했던 자원을 해제시킨다.
    ServletContext ctx = sre.getServletContext();
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSessionFactory");

    // 스레드가 사용했던 SqlSession 객체의 자원을 해제시키고,
    // 스레드에 보관된 SqlSession 객체를 제거한다.
    ((SqlSessionFactoryProxy) sqlSessionFactory).clearSession();
  }
}
