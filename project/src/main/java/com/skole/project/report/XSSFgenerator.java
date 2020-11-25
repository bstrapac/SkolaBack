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
	private static final Color LIGHT_BLUE = new Color(120, 166, 222);
	private static final Color BACKGROUND = new Color(236, 242, 250);
	
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
			XSSFColor lightBlue = new XSSFColor(LIGHT_BLUE, null);
			XSSFColor background = new XSSFColor(BACKGROUND, null);
			
			//FONTS
			XSSFFont bold = createBoldFont(workbook, black);
			
			//CELL STYLES
			XSSFCellStyle headerCellStyle = createHeaderCellStyle(workbook, lightBlue, bold);
			XSSFCellStyle rowCellStyle = createRowCellStyle(workbook, background);
			
			
			String[] headerRowCells = createHeader(headerRow, headerCellStyle);
			createBody(list, rowNum, spreadsheet, rowCellStyle);
			autoSizeTable(spreadsheet,headerRowCells);
			report = saveReport(report, workbook);
			saveOnDisc(workbook);
			
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
	
	private void createBody(List<OcjenaRaw> list, int rowNum, XSSFSheet spreadsheet, XSSFCellStyle rowCellStyle) {
		for(int i = 0; i < list.size(); i++) {
			OcjenaRaw rowData = list.get(i);
			XSSFRow bodyRow = spreadsheet.createRow(rowNum);
			populateRowData(bodyRow, rowData, rowCellStyle);
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
	
	private void populateRowData(XSSFRow bodyRow, OcjenaRaw rowData, XSSFCellStyle rowCellStyle) {
		XSSFCell idCell = bodyRow.createCell(0);
		idCell.setCellValue(rowData.getId());
		idCell.setCellStyle(rowCellStyle);
		
		XSSFCell idPOCell = bodyRow.createCell(1);
		idPOCell.setCellValue(rowData.getIdPredmetOsoba());
		idPOCell.setCellStyle(rowCellStyle);
		
		XSSFCell ocjenaCell = bodyRow.createCell(2);
		ocjenaCell.setCellValue(rowData.getOcjena());
		ocjenaCell.setCellStyle(rowCellStyle);
		
		XSSFCell datumCell = bodyRow.createCell(3);
		datumCell.setCellValue(rowData.getDatum());
		datumCell.setCellStyle(rowCellStyle);
		
		XSSFCell idODCell = bodyRow.createCell(4);
		idODCell.setCellValue(rowData.getIdOsobaDod());
		idODCell.setCellStyle(rowCellStyle);
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
	
	private void saveOnDisc(XSSFWorkbook workbook) throws FileNotFoundException {
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
	
	private XSSFCellStyle createHeaderCellStyle(XSSFWorkbook workbook, XSSFColor color, XSSFFont bold) {
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(color);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setFont(bold);
		return headerCellStyle;
	}
	

	private XSSFCellStyle createRowCellStyle(XSSFWorkbook workbook, XSSFColor color) {
		XSSFCellStyle rowCellStyle = workbook.createCellStyle();
		rowCellStyle.setFillForegroundColor(color);
		rowCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		rowCellStyle.setAlignment(HorizontalAlignment.RIGHT);
		rowCellStyle.setBorderBottom(BorderStyle.THIN);
		rowCellStyle.setBorderLeft(BorderStyle.THIN);
		rowCellStyle.setBorderRight(BorderStyle.THIN);
		rowCellStyle.setBorderTop(BorderStyle.THIN);
		return rowCellStyle;
	}
}
