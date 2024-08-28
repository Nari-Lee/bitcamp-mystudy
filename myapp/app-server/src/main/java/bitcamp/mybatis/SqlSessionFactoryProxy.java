package bitcamp.mybatis;

import org.apache.ibatis.session.*;

import java.sql.Connection;

/**
 * packageName    : bitcamp.mybatis
 * fileName       : SqlSessionFactory
 * author         : narilee
 * date           : 24. 8. 27.
 * description    : SqlSessionFactory의 프록시 클래스입니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 8. 27.        narilee       최초 생성
 */
  public class SqlSessionFactoryProxy implements SqlSessionFactory {

  /**
   * SqlSession 객체를 담을 스레드 전용 변수입니다.
   */
  ThreadLocal<SqlSession> sqlSessionThreadLocal = new ThreadLocal<>();

  /**
   * 원본 SqlSessionFactory 객체입니다.
   */
  private SqlSessionFactory original;

  /**
   * SqlSessionFactoryProxy 생성자입니다.
   *
   * @param original 원본 SqlSessionFactory 객체
   */
  public SqlSessionFactoryProxy(SqlSessionFactory original) {
    this.original = original;
  }

  /**
   * 새로운 SqlSession을 열어 반환합니다.
   *
   * @return 새로운 SqlSession 객체
   */
  @Override
  public SqlSession openSession() {
    return original.openSession();
  }

  /**
   * 현재 스레드 저장소에 보관된 SqlSession 객체를 반환합니다.
   * 존재하지 않는 경우 오리지널 객체를 통해 새로 얻습니다.
   *
   * @param autoCommit 자동 커밋 여부
   * @return SqlSession 객체
   */
  @Override
  public SqlSession openSession(boolean autoCommit) {

    // 1) 현재 스레드 저장소 보관된 SqlSession 객체를 찾는다.
    SqlSession sqlSession = sqlSessionThreadLocal.get();

    // 2) 없으면,
    if (sqlSession == null) {
      // - 오리지널 객체를 통해 새로 얻는다.
      sqlSession = original.openSession(autoCommit);

      // - 다음에 이 객체를 사용하기 위해 현재 스레드 보관소에 저장한다.
      sqlSessionThreadLocal.set(sqlSession);
    }

    return sqlSession;
  }

  /**
   * 주어진 Connection을 사용하여 SqlSession을 열어 반환합니다.
   *
   * @param connection 사용할 Connection 객체
   * @return SqlSession 객체
   */
  @Override
  public SqlSession openSession(Connection connection) {
    return original.openSession(connection);
  }

  /**
   * 주어진 트랜잭션 격리 수준으로 SqlSession을 열어 반환합니다.
   *
   * @param level 트랜잭션 격리 수준
   * @return SqlSession 객체
   */
  @Override
  public SqlSession openSession(TransactionIsolationLevel level) {
    return original.openSession(level);
  }

  /**
   * 주어진 ExecutorType으로 SqlSession을 열어 반환합니다.
   *
   * @param execType 실행자 타입
   * @return SqlSession 객체
   */
  @Override
  public SqlSession openSession(ExecutorType execType) {
    return original.openSession(execType);
  }

  /**
   * 주어진 ExecutorType과 자동 커밋 여부로 SqlSession을 열어 반환합니다.
   * @param execType 실행자 타입
   * @param autoCommit 자동 커밋 여부
   * @return SqlSession 객체
   */
  @Override
  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return original.openSession(execType, autoCommit);
  }

  /**
   * 주어진 ExecutorType과 트랜잭션 격리 수준으로 SqlSession을 열어 반환합니다.
   *
   * @param execType 실행자 타입
   * @param level 트랜잭션 격리 수준
   * @return SqlSession 객체
   */
  @Override
  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return original.openSession(execType, level);
  }

  /**
   * 주어진 ExecutorType과 Connection으로 SqlSession을 열어 반환합니다.
   *
   * @param execType 실행자 타입
   * @param connection 사용할 Connection 객체
   * @return SqlSession 객체
   */
  @Override
  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return original.openSession(execType, connection);
  }

  /**
   * MyBatis Configuration 객체를 반환합니다.
   *
   * @return Configuration 객체
   */
  @Override
  public Configuration getConfiguration() {
    return original.getConfiguration();
  }
}
