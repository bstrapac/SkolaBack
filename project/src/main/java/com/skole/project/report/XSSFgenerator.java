package com.skole.project.report;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSSFgenerator {

	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	
	public static void createXSSF() throws Exception {
		
		XSSFWorkbook workbook = new XSSFWorkbook();		
		XSSFSheet spreadsheet = workbook.createSheet("ocjene_raw");		
		XSSFRow row = spreadsheet.createRow((short)0);		
		XSSFCell cell;
		
		cell = row.createCell((short)0);
		cell.setCellValue("ID");
		cell =  row.createCell((short)1);
		cell.setCellValue("ID_PREDMET_OCJENA");
		cell = row.createCell((short)2);
		cell.setCellValue("OCJENA");
		cell = row.createCell((short)3);
		cell.setCellValue("DATUM");
		cell = row.createCell((short)4);
		cell.setCellValue("ID_OSOBA_DOD");
		
		//iz baze povuć sve i prebacit u excel -> raw data
		
		
		//FileOutputStream out =  new FileOutputStream(new File(DEST + "test2.xlsx"));
		//workbook.write(out);
		workbook.close();
		//out.close();
	}
}
