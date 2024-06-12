package study.lang.string;

public class Test03 {
  public static void main(String[] args) {
    java.util.Scanner keyboard = new java.util.Scanner(System.in);

    System.out.print("입력1:");
    String s1 = keyboard.nextLine();

    System.out.print("입력2:");
    String s2 = keyboard.nextLine();

    System.out.println(s1 == s2);

    System.out.println(s1.equals(s2));

    keyboard.close();
  }
}
