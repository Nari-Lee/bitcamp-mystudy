package bitcamp.myapp;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";

        String line = "----------------------------------------";
        String title = "[팀 프로젝트 관리 시스템";
        String user = "1. 회원";
        String team = "2. 팀";
        String project = "3. 프로젝트";
        String board = "4. 게시판";
        String help = "5. 도움말";
        String end = "6. 종료";

        System.out.println(boldAnsi + line + resetAnsi);
        System.out.println(boldAnsi + title + resetAnsi + "\n");
        System.out.println(user);
        System.out.println(team);
        System.out.println(project);
        System.out.println(board);
        System.out.println(help);
        System.out.println(boldAnsi + redAnsi + end + resetAnsi);
        System.out.println(boldAnsi + line + resetAnsi);

        String input;
        do {
        System.out.print("> ");
        input = scanner.nextLine();

        switch (input) {
            case "1":
                System.out.println("회원");
                break;
            case "2":
                System.out.println("팀");
                break;
            case "3":
                System.out.println("프로젝트");
                break;
            case "4":
                System.out.println("게시판");
                break;
            case "5":
                System.out.println("도움말");
                break;
            case "6":
                System.out.println("종료");
                break;
            default:
                System.out.println("메뉴 번호가 옳지 않습니다.");
                break;
        }
        } while (!input.equals("6"));

        scanner.close();
    }
}
