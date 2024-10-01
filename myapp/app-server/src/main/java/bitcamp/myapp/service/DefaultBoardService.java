package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : bitcamp.myapp.service
 * fileName       : DefaultBoardService
 * author         : narilee
 * date           : 24. 9. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 11.        narilee       최초 생성
 * 24. 9. 24.        narilee       @Component 적용
 * 24. 9. 26.        narilee       @Transactional 적용
 */
@Service
public class DefaultBoardService implements BoardService {
  private BoardDao boardDao;

  public DefaultBoardService(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Transactional
  public void add(Board board) throws Exception {
      boardDao.insert(board);
      if (board.getAttachedFiles().size() > 0) {
        boardDao.insertFiles(board);
      }
  }

  public List<Board> list() throws Exception {
    return boardDao.list();
  }

  public Board get(int boardNo) throws Exception {
    return boardDao.findBy(boardNo);
  }

  @Transactional
  public boolean update(Board board) throws Exception {
      if (!boardDao.update(board)) {
        return false;
      }
      if (board.getAttachedFiles().size() > 0) {
        boardDao.insertFiles(board);
      }
      return true;
  }

  @Transactional
  public void delete(int boardNo) throws Exception {
      boardDao.deleteFiles(boardNo);
      boardDao.delete(boardNo);
  }

  @Transactional
  public void increaseViewCount(int boardNo) throws Exception {
      Board board = boardDao.findBy(boardNo);
      if (board != null) {
        int newViewCount = board.getViewCount() + 1;
        boardDao.updateViewCount(boardNo, newViewCount);
      }
  }

  public AttachedFile getAttachedFile(int fileNo) throws Exception {
    return boardDao.getFile(fileNo);
  }

  @Transactional
  public boolean deleteAttachedFile(int fileNo) throws Exception {
      if (!boardDao.deleteFile(fileNo)) {
        return false;
      }
      return true;
  }
}
