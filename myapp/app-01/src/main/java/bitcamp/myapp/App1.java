package bitcamp.myapp;

import java.util.Scanner;

public class App1 {
    static String[] menus = new String[] {
            "회원",
            "팀",
            "프로젝트",
            "게시판",
            "도움말",
            "종료"};
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        printMenu();
        String input;

        while (true) {
            try {
                String command = prompt();
                if (command.equals("menu")) {
                    printMenu();
                } else {
                    int menuNo = Integer.parseInt(command);
                    if (isValidateMenu(menuNo)) {
                        if (getMenuTitle(menuNo) == null) {
                            System.out.println("유효한 메뉴 번호가 아닙니다..");
                        } else if (getMenuTitle(menuNo).equals("종료")) {
                            break;
                        } else {
                            System.out.println(getMenuTitle(menuNo));
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }

        System.out.println("종료합니다.");

        scanner.close();
        }
        static void printMenu() {
            String boldAnsi = "\033[1m";
            String redAnsi = "\033[31m";
            String resetAnsi = "\033[0m";

            String appTitle = "[팀 프로젝트 관리 시스템]";
            String line = "----------------------------------------";

            System.out.println(boldAnsi + line + resetAnsi);
            System.out.println(boldAnsi + appTitle + "\n" + resetAnsi);

            for (int i = 0; i <menus.length; i++) {
                if (menus[i].equals("종료")) {
                    System.out.printf("%s%d. %s%s\n", (boldAnsi + redAnsi), (i + 1), menus[i], resetAnsi);
                } else {
                    System.out.printf("%d. %s\n",  (i + 1), menus[i]);
                }
            }
            System.out.println(boldAnsi+ line + resetAnsi);
        }
        static String prompt(){
            System.out.print("> ");
            return scanner.nextLine();
        }
        static boolean isValidateMenu(int menuNo) {
            return menuNo >= 1 && menuNo <= menus.length;
        }
        static String getMenuTitle(int menuNo) {
            return isValidateMenu(menuNo) ? menus[menuNo - 1] : null;
        }
    }

