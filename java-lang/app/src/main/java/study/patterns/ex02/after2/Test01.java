package study.patterns.ex02.after2;

public class Test01 {
  public static void main(String[] args) {
    System.out.println(1);
    ContentPrinter printer0 = new ContentPrinter();
    printer0.print("안녕하세요");
    System.out.println();

    System.out.println(2);
    ContentPrinter printer1a = new ContentPrinter();
    HeaderPrinter printer1b = new HeaderPrinter(printer1a, "편지");
    printer1b.print("안녕하세요");
    System.out.println();

    System.out.println(3);
    ContentPrinter printer2a = new ContentPrinter();
    HeaderPrinter printer2b = new HeaderPrinter(printer2a, "편지");
    FooterPrinter printer2c = new FooterPrinter(printer2b, "비트캠프");
    printer2c.print("안녕하세요");
    System.out.println();

    System.out.println(4);
    ContentPrinter printer3a = new ContentPrinter();
    FooterPrinter printer3b = new FooterPrinter(printer3a, "편지");
    printer3b.print("안녕하세요");
    System.out.println();

    System.out.println(5);
    ContentPrinter printer4a = new ContentPrinter();
    SignPrinter printer4b = new SignPrinter(printer4a, "홍길동");
    printer4b.print("안녕하세요");
    System.out.println();

    System.out.println(6);
    ContentPrinter printer5a = new ContentPrinter();
    SignPrinter printer5b = new SignPrinter(printer5a, "홍길동");
    FooterPrinter printer5c = new FooterPrinter(printer5b, "편지");
    printer5c.print("안녕하세요");
    System.out.println();

    System.out.println(7);
    ContentPrinter printer6a = new ContentPrinter();
    SignPrinter printer6b = new SignPrinter(printer6a, "홍길동");
    HeaderPrinter printer6c = new HeaderPrinter(printer6b, "편지");
    printer6c.print("안녕하세요");
    System.out.println();

    System.out.println(8);
    ContentPrinter printer7a = new ContentPrinter();
    SignPrinter printer7b = new SignPrinter(printer7a, "홍길동");
    FooterPrinter printer7c = new FooterPrinter(printer7b, "비트캠프");
    HeaderPrinter printer7d = new HeaderPrinter(printer7c, "편지");
    printer7d.print("안녕하세요");
    System.out.println();
  }
}
