package bitcamp.myapp;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";

        String appTitle = "[팀 프로젝트 관리 시스템]";
        String line = "----------------------------------------";

        String[] menus = new String[] {
                "회원",
                "팀",
                "프로젝트",
                "게시판",
                "도움말",
                "종료"};

        System.out.println(boldAnsi + line + resetAnsi);
        System.out.println(boldAnsi + appTitle + "\n" + resetAnsi);

        for (int i = 0; i <menus.length; i++) {
            if (menus[i] == "종료") {
                System.out.printf("%s%d. %s%s\n", (boldAnsi + redAnsi), (i + 1), menus[i], resetAnsi);
            } else {
                System.out.printf("%d. %s\n",  (i + 1), menus[i]);
            }
        }

        System.out.println(boldAnsi+ line + resetAnsi);

        int menuNo;
        while (true) {
            try {
                System.out.print("> ");
                menuNo = scanner.nextInt();

                if (menuNo >= 1 && menuNo <= menus.length) {
                    if (menus[menuNo - 1] == "종료") {
                        break;
                    }
                    System.out.println(menus[menuNo-1]);;
                } else {
                    System.out.println("유효한 메뉴 번호가 아닙니다..");
                }
            }catch (InputMismatchException e) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
                scanner.next();
            }
        }
        System.out.println("종료합니다.");
        }
    }

