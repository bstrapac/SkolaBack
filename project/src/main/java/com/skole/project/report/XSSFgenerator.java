package com.skole.project.report;


import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class XSSFgenerator {
	
	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	
	//byte [] generateXlsx
	//kreira novi xlsx file
	//poziva funkcije za izradu headera, body-a i za spremanje xlsx fajla
	
	public byte[] generateXlsx(List<OcjenaRaw> list) throws Exception{
		byte[] report = null;
		int rowNum = 0;
		try(XSSFWorkbook workbook = new XSSFWorkbook()){
			XSSFSheet spreadsheet = workbook.createSheet("ocjene_raw");
			XSSFRow headerRow = spreadsheet.createRow(rowNum);
			rowNum++;
			
			//style props and settings 
			//COLORS
			XSSFColor black = new XSSFColor(Color.BLACK, null);
			XSSFColor white = new XSSFColor(Color.WHITE, null);
			
			//FONTS
			XSSFFont bold = createBoldFont(workbook, black);
			
			//CELL STYLES
			XSSFCellStyle headerCellStyle = createHeaderCellStyle(workbook, white, bold);
			
			
			String[] headerRowCells = createHeader(headerRow, headerCellStyle);
			createBody(list, rowNum, spreadsheet);
			autoSizeTable(spreadsheet,headerRowCells);
			report = saveReport(report, workbook);
			saveOnDisk(workbook);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return report;
	}
	
	private void autoSizeTable(XSSFSheet spreadsheet, String[] headerRowCells) {
		for(int i = 0; i < headerRowCells.length; i++ ) {
			spreadsheet.autoSizeColumn(i);
		}
	}
	
	//void create body
	//kreira redove za body tablice u xlsx
	//poziva funkciju za popunjavanje redova podatcima
	
	private void createBody(List<OcjenaRaw> list, int rowNum, XSSFSheet spreadsheet) {
		for(int i = 0; i < list.size(); i++) {
			OcjenaRaw rowData = list.get(i);
			XSSFRow bodyRow = spreadsheet.createRow(rowNum);
			populateRowData(bodyRow, rowData);
			rowNum++;
		}
	}
	
	//string create header
	//stvara header tablice 
	
	String[] createHeader(XSSFRow headerRow, XSSFCellStyle headerCellStyle) {
		String[] headerRowCells = {"ID", "ID PREDMET OSOBA", "OCJENA", "DATUM", "ID OSOBA DOD"};
		
		for(int i = 0; i < headerRowCells.length; i++) {
			XSSFCell hCell = headerRow.createCell(i);
			hCell.setCellValue(headerRowCells[i]);
			hCell.setCellStyle(headerCellStyle);
		}
		return headerRowCells;
	}
	
	//void populate row data
	//popunjava tablicu podatcima
	
	private void populateRowData(XSSFRow bodyRow, OcjenaRaw rowData) {
		XSSFCell idCell = bodyRow.createCell(0);
		idCell.setCellValue(rowData.getId());
		
		XSSFCell idPOCell = bodyRow.createCell(1);
		idPOCell.setCellValue(rowData.getIdPredmetOsoba());
		
		XSSFCell ocjenaCell = bodyRow.createCell(2);
		ocjenaCell.setCellValue(rowData.getOcjena());
		
		XSSFCell datumCell = bodyRow.createCell(3);
		datumCell.setCellValue(rowData.getDatum());
		
		XSSFCell idODCell = bodyRow.createCell(4);
		idODCell.setCellValue(rowData.getIdOsobaDod());
		
	}
	
	//save report
	//sprema xlsx u obliku byte array-a 
	
	private byte[] saveReport(byte[] report, XSSFWorkbook workbook) {
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
			workbook.write(bos);
			report = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return report;
	}
	
	private void saveOnDisk(XSSFWorkbook workbook) throws FileNotFoundException {
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(DEST + "test4.xlsx"));
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//CELL STYLES
	private XSSFFont createBoldFont(XSSFWorkbook workbook, XSSFColor black) {
		XSSFFont bold = workbook.createFont();
		bold.setFontHeightInPoints((short) 10);
		bold.setFontName("Arial");
		bold.setColor(black);
		bold.setBold(true);
		bold.setItalic(false);
		return bold;
	}
	
	private XSSFCellStyle createHeaderCellStyle(XSSFWorkbook workbook, XSSFColor white, XSSFFont bold) {
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(white);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setFont(bold);
		return headerCellStyle;
	}
}
