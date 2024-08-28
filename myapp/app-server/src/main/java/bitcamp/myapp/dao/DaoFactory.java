package bitcamp.myapp.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;

/**
 * packageName    : bitcamp.myapp.dao
 * fileName       : DaoFactory
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : DAO 객체를 생성하고 SQL 실행을 처리하는 팩토리 클래스입니다.
 *                  이 클래스는 동적 프록시를 사용하여 DAO 인터페이스의 구현체를 생성합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
  public class DaoFactory {

  private SqlSessionFactory sqlSessionFactory;

  /**
   * DaoFactory의 생성자입니다.
   *
   * @param sqlSessionFactory MyBatis SqlSessionFactory 객체
   */
  public DaoFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  /**
   * 주어진 DAO 인터페이스 타입에 대한 프록시 객체를 생성합니다.
   *
   * @param <T> DAO  인터페이스 타입
   * @param daoType 생성할 DAO 인터페이스의 Class 객체
   * @return 생성된 DAO 프록시 객체
   * @throws Exception
   */
  public <T> T createObject(Class<T> daoType) throws Exception {
    return (T) Proxy.newProxyInstance(
        this.getClass().getClassLoader(),
        new Class[]{daoType},
        this::invoke);
  }

  /**
   * 프록시 객체의 메서드 호출을 처리합니다.
   * 이 메서드는 MyBatis를 사용하여 실제 SQL 쿼리를 실행합니다.
   *
   * @param proxy 프록시 객체
   * @param method 호출된 메서드
   * @param args 메서드 인자
   * @return SQL 쿼리 실행 결과
   * @throws Exception SQL 실행 중 발생한 예외
   */
  public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
    String namespace = proxy.getClass().getInterfaces()[0].getSimpleName();
    String sqlId = method.getName();
    String statement = String.format("%s.%s", namespace, sqlId);

    Object paramValue = null;
    if (args != null) {
      if (args.length == 1) {
        paramValue = args[0];
      } else {
        Parameter[] params = method.getParameters();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
          Param anno = params[i].getAnnotation(Param.class);
          map.put(anno.value(), args[i]);
        }
        paramValue = map;
      }
    }

    Class<?> returnType = method.getReturnType();

    // 현재 스레드에 보관된 SqlSession 을 꺼낸다.
    SqlSession sqlSession = sqlSessionFactory.openSession(false);

    if (returnType == List.class) {
      return sqlSession.selectList(statement, paramValue);
    } else if (returnType == int.class || returnType == void.class || returnType == boolean.class) {
      int count = sqlSession.insert(statement, paramValue);
      if (returnType == boolean.class) {
        return count > 0;
      } else if (returnType == void.class) {
        return null;
      } else {
        return count;
      }
    } else {
      return sqlSession.selectOne(statement, paramValue);
    }
  }
}
