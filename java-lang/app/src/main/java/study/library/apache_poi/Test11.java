package study.library.apache_poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test11 {
  public static void main(String[] args) throws Exception {

    XSSFWorkbook workbook = new XSSFWorkbook("temp/test.xlsx");

    XSSFSheet sheet = workbook.getSheet("users");

    for(int i = 1; i <= sheet.getLastRowNum(); i++) {

      Row row = sheet.getRow(i);

      System.out.printf("%d %s %s %s %s\n",
          (int) row.getCell(0).getNumericCellValue(),
          row.getCell(1).getNumericCellValue(),
          row.getCell(2).getNumericCellValue(),
          row.getCell(3).getNumericCellValue(),
          row.getCell(4).getNumericCellValue());
    }

    System.out.println("엑셀 파일 생성 완료!");
  }
}
