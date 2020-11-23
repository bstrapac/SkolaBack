package com.skole.project.report;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSSFgenerator {
	
	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	
	public static void createXSSF(List<OcjenaRaw> list) throws Exception {
		
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
		
		int[] numArray = new int[]{1};
		
		list.forEach(item -> {
			XSSFRow nRow = spreadsheet.createRow((short)numArray[0]++);
			XSSFCell nCell;
			nCell = nRow.createCell((short)0);
			nCell.setCellValue(item.getId());
			nCell = nRow.createCell((short)1);
			nCell.setCellValue(item.getIdPredmetOsoba());
			nCell = nRow.createCell((short)2);
			nCell.setCellValue(item.getOcjena());
			nCell = nRow.createCell((short)3);
			nCell.setCellValue(item.getDatum());
			nCell = nRow.createCell((short)4);
			nCell.setCellValue(item.getIdOsobaDod());
		});
		
		
		FileOutputStream out =  new FileOutputStream(new File(DEST + "test3.xlsx"));
		workbook.write(out);
		workbook.close();
		out.close();
	}
}
