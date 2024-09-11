package bitcamp.myapp.service;

import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;

import java.util.List;

/**
 * packageName    : bitcamp.myapp.service
 * fileName       : BoardService
 * author         : narilee
 * date           : 24. 9. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 9. 11.        narilee       최초 생성
 */
public interface BoardService {

  void add(Board board) throws Exception;

  List<Board> list() throws Exception;

  Board get(int boardNo) throws Exception;

  boolean update(Board board) throws Exception;

  void delete(int boardNo) throws Exception;

  void increaseViewCount(int boardNo) throws Exception;

  AttachedFile getAttachedFile(int fileNo) throws Exception;

  boolean deleteAttachedFile(int fileNo) throws Exception;
}
