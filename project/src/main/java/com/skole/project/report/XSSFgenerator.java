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

import com.skole.project.entity.Predmet;
import com.skole.project.report.entity.OcjenaRaw;
import com.skole.project.report.entity.OsobaRaw;
import com.skole.project.report.entity.PredmetOsobaRaw;

public class XSSFgenerator {
	
	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	private static final Color LIGHT_BLUE = new Color(120, 166, 222);
	private static final Color BACKGROUND = new Color(236, 242, 250);
	
	//byte [] generateXlsx
	//kreira novi xlsx file
	//poziva funkcije za izradu headera, body-a i za spremanje xlsx fajla
	
	public byte[] generateXlsx(List<OcjenaRaw> ocjene, List<OsobaRaw> osobe, List<Predmet> predmeti, List<PredmetOsobaRaw> predmetOsoba) throws Exception{
		byte[] report = null;
		int rowNum = 0;
		try(XSSFWorkbook workbook = new XSSFWorkbook()){
			
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
			
			
			XSSFSheet ocjeneSheet = workbook.createSheet("ocjene_raw");
			XSSFSheet osobeSheet = workbook.createSheet("osobe_raw");
			XSSFSheet predmetiSheet = workbook.createSheet("predmeti_raw");
			XSSFSheet predmetiOsobeSheet = workbook.createSheet("predmet_osoba_raw");
			
			XSSFRow headerRowOcjene = ocjeneSheet.createRow(rowNum);
			XSSFRow headerRowOsobe = osobeSheet.createRow(rowNum);
			XSSFRow headerRowPredmeti = predmetiSheet.createRow(rowNum);
			XSSFRow headerRowPredmetiOsobe = predmetiOsobeSheet.createRow(rowNum);
			
			rowNum++;
			
			//HeaderRowCells
			String[] headerOcjene = {"ID", "ID PREDMET OSOBA", "OCJENA", "DATUM", "ID OSOBA DOD"};
			String[] headerOsoba = {"ID", "OIB", "IME", "PREZIME", "DOB", "KONTAKT", "MAIL", "ADRESA", "ID TIP"};
			String[] headerPredmet = {"ID", "NAZIV"};
			String[] headerPredmetOsoba = {"ID", "ID OSOBA", "ID PREDMET"};
			
			//POPULATE
			String[] headerRowCells = createHeader(headerRowOcjene, headerCellStyle, headerOcjene);
			String[] headerCellsOsoba = createHeader(headerRowOsobe, headerCellStyle, headerOsoba);
			String[] headerCellsPredmeti =  createHeader(headerRowPredmeti, headerCellStyle, headerPredmet);
			String[] headerCellsPedmetiOsobe = createHeader(headerRowPredmetiOsobe, headerCellStyle, headerPredmetOsoba);
			
			createBodyOcjene(ocjene, rowNum, ocjeneSheet, rowCellStyle);
			createBodyOsobe(osobe, rowNum, osobeSheet, rowCellStyle);
			createBodyPredmet(predmeti, rowNum, predmetiSheet, rowCellStyle);
			createBodyPredmetOsoba(predmetOsoba, rowNum, predmetiOsobeSheet, rowCellStyle);
			
			
			autoSizeTable(ocjeneSheet,headerRowCells);
			autoSizeTable(osobeSheet, headerCellsOsoba);
			autoSizeTable(predmetiSheet,headerCellsPredmeti);
			autoSizeTable(predmetiOsobeSheet, headerCellsPedmetiOsobe);
			
			report = saveReport(report, workbook);
			saveOnDisc(workbook);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return report;
	}


	//void create body
	//kreira redove za body tablice u xlsx
	//poziva funkciju za popunjavanje redova podatcima
	
	private void createBodyPredmetOsoba(List<PredmetOsobaRaw> list, int rowNum, XSSFSheet spreadsheet,
			XSSFCellStyle rowCellStyle) {
		for(int i = 0; i < list.size(); i++) {
			PredmetOsobaRaw rowData = list.get(i);
			XSSFRow bodyRow = spreadsheet.createRow(rowNum);
			populatePredmetOsoba(bodyRow, rowData, rowCellStyle);
			rowNum++;
		}
	}

	private void createBodyPredmet(List<Predmet> list, int rowNum, XSSFSheet spreadsheet,
			XSSFCellStyle rowCellStyle) {
		for(int i = 0; i < list.size(); i++) {
			Predmet rowData = list.get(i);
			XSSFRow bodyRow = spreadsheet.createRow(rowNum);
			populatePredmet(bodyRow, rowData, rowCellStyle);
			rowNum++;
		}
	}

	private void createBodyOcjene(List<OcjenaRaw> list, int rowNum, XSSFSheet spreadsheet, XSSFCellStyle rowCellStyle) {
		for(int i = 0; i < list.size(); i++) {
			OcjenaRaw rowData = list.get(i);
			XSSFRow bodyRow = spreadsheet.createRow(rowNum);
			populateOcjene(bodyRow, rowData, rowCellStyle);
			rowNum++;
		}
	}

	private void createBodyOsobe(List<OsobaRaw> list, int rowNum, XSSFSheet spreadsheet, XSSFCellStyle rowCellStyle) {
		for(int i = 0; i < list.size(); i++ ) {
			OsobaRaw rowData = list.get(i);
			XSSFRow bodyRow = spreadsheet.createRow(rowNum);
			populateOsobe(bodyRow, rowData, rowCellStyle);
			rowNum++;
		}
		
	}
	
	//string create header
	//stvara header tablice 
	
	String[] createHeader(XSSFRow headerRow, XSSFCellStyle headerCellStyle, String[] headerCells) {
		String[] headerRowCells = headerCells;
		
		for(int i = 0; i < headerRowCells.length; i++) {
			XSSFCell hCell = headerRow.createCell(i);
			hCell.setCellValue(headerRowCells[i]);
			hCell.setCellStyle(headerCellStyle);
		}
		return headerRowCells;
	}
	
	//void populate row data
	//popunjava tablicu podatcima

	private void populatePredmetOsoba(XSSFRow bodyRow, PredmetOsobaRaw rowData, XSSFCellStyle rowCellStyle) {
		XSSFCell idCell = bodyRow.createCell(0);
		idCell.setCellValue(rowData.getIdPredmetOsoba());
		idCell.setCellStyle(rowCellStyle);
		
		XSSFCell idOsobaCell = bodyRow.createCell(1);
		idOsobaCell.setCellValue(rowData.getIdOsoba());
		idOsobaCell.setCellStyle(rowCellStyle);
		
		XSSFCell idPredmetCell = bodyRow.createCell(2);
		idPredmetCell.setCellValue(rowData.getIdPredmet());
		idPredmetCell.setCellStyle(rowCellStyle);
		
	}

	private void populatePredmet(XSSFRow bodyRow, Predmet rowData, XSSFCellStyle rowCellStyle) {
		XSSFCell idCell = bodyRow.createCell(0);
		idCell.setCellValue(rowData.getIdPredmet());
		idCell.setCellStyle(rowCellStyle);
		
		XSSFCell nazivCell = bodyRow.createCell(1);
		nazivCell.setCellValue(rowData.getNazivPredmet());
		nazivCell.setCellStyle(rowCellStyle);
		
	}

	private void populateOcjene(XSSFRow bodyRow, OcjenaRaw rowData, XSSFCellStyle rowCellStyle) {
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

	private void populateOsobe(XSSFRow bodyRow, OsobaRaw rowData, XSSFCellStyle rowCellStyle) {
		XSSFCell idCell = bodyRow.createCell(0);
		idCell.setCellValue(rowData.getIdOsoba());
		idCell.setCellStyle(rowCellStyle);
		
		XSSFCell oibCell = bodyRow.createCell(1);
		oibCell.setCellValue(rowData.getOib());
		oibCell.setCellStyle(rowCellStyle);
		
		XSSFCell imeCell = bodyRow.createCell(2);
		imeCell.setCellValue(rowData.getIme());
		imeCell.setCellStyle(rowCellStyle);
		
		XSSFCell prezimeCell = bodyRow.createCell(3);
		prezimeCell.setCellValue(rowData.getPrezime());
		prezimeCell.setCellStyle(rowCellStyle);
		
		XSSFCell dobCell = bodyRow.createCell(4);
		dobCell.setCellValue(rowData.getDob());
		dobCell.setCellStyle(rowCellStyle);
		
		XSSFCell kontaktCell = bodyRow.createCell(5);
		kontaktCell.setCellValue(rowData.getKontakt());
		kontaktCell.setCellStyle(rowCellStyle);

		XSSFCell mailCell = bodyRow.createCell(6);
		mailCell.setCellValue(rowData.getMail());
		mailCell.setCellStyle(rowCellStyle);
		
		XSSFCell adresaCell = bodyRow.createCell(7);
		adresaCell.setCellValue(rowData.getAdresa());
		adresaCell.setCellStyle(rowCellStyle);
		
		XSSFCell tipCell = bodyRow.createCell(8);
		tipCell.setCellValue(rowData.getIdTipOsobe());
		tipCell.setCellStyle(rowCellStyle);
		
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
			out = new FileOutputStream(new File(DEST + "skolaDemoRawData.xlsx"));
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
	
	private void autoSizeTable(XSSFSheet spreadsheet, String[] headerRowCells) {
		for(int i = 0; i < headerRowCells.length; i++ ) {
			spreadsheet.autoSizeColumn(i);
		}
	}
}
