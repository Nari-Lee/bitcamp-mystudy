package bitcamp.myapp.command;

import java.util.Arrays;

public class ArrayList {

  private static final int MAX_SIZE = 3;

  protected int size = 0;
  private Object[] list = new Object[MAX_SIZE];

  public void add(Object obj) {
    if (size == list.length) {
      // 1) 우리가 만든 메서드를 사용하여 배열 크기 증가
      //grow();

      // 2) 자바에서 제공하는 클래스를 사용하여 배열 크기 증가
      int oldSize = list.length;
      int newSize = oldSize + (oldSize >> 1);
      list = Arrays.copyOf(list, newSize);
    }
    list[size++] = obj;
  }

  //  private void grow() {
  //    int oldSize = list.length;
  //    int newSize = oldSize + (oldSize >> 1);
  //    Object[] arr = new Object[newSize];
  //    for (int i = 0; i < list.length; i++) {
  //      arr[i] = list[i];
  //    }
  //    list = arr;
  //  }

  public Object remove(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    Object deletedObj = list[index];
    for (int i = index + 1; i < size; i++) {
      list[i - 1] = list[i];
    }
    list[--size] = null;
    return deletedObj;
  }

  public Object[] toArray() {
    Object[] arr = new Object[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = list[i];
    }
    return arr;
  }

  public int indexOf(Object obj) {
    for (int i = 0; i < size; i++) {
      if (list[i] == obj) {
        return i;
      }
    }
    return -1;
  }

  public int size() {
    return size;
  }

  public Object get(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    return list[index];
  }

  public boolean contains(Object obj) {
    return indexOf(obj) != -1;
  }
}
