package com.skole.project.report;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Row.RowBuilder;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import com.skole.project.entity.Osoba;

public class PDFGenerator {

	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	
	
	//generate byte array pdf
	//method will create and fill pdf with table and data
	public byte[] generatePDF(ReportCard report, Osoba osoba) {
		byte[] pdf = null;
		PDDocument doc = new PDDocument();
		try {
			PDPage page = new PDPage(new PDRectangle(PDRectangle.A3.getHeight(), PDRectangle.A3.getWidth()));
			doc.addPage(page);
			try(PDPageContentStream pcs = new PDPageContentStream(doc, page)){
				Table table = createTable(getHeaderColumns(), report);
				drawTable(page, pcs, table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pdf = saveAsByteArray(pdf, doc);
		saveOnDisc(doc, osoba);
		try {
			doc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdf;
	}
	
	//draw table -> takes arguments for drawing a table
	private void drawTable(PDPage page, PDPageContentStream pcs, Table table) {
		TableDrawer tableDrawer = TableDrawer.builder()
											.contentStream(pcs)
											.startX(25f)
											.startY(page.getMediaBox().getUpperRightY()-50f)
											.table(table)
											.build();
		tableDrawer.draw();
	}
	
	//createHeaderCell -> creates table header
	private TextCell createHeaderCell(String text) {
		return TextCell.builder()
					.text(text)
					.font(PDType1Font.TIMES_ROMAN)
					.fontSize(14)
					.backgroundColor(Color.WHITE)
					.textColor(Color.BLACK)
					.borderWidth(0.5f)
					.verticalAlignment(VerticalAlignment.MIDDLE)
					.horizontalAlignment(HorizontalAlignment.CENTER)
					.build();
	}
	
	//createRowCell -> creates table rows
	private TextCell createRowCell(String text) {
		return TextCell.builder()
				.text(text)
				.font(PDType1Font.TIMES_ROMAN)
				.fontSize(14)
				.backgroundColor(Color.WHITE)
				.textColor(Color.BLACK)
				.borderWidth(0.5f)
				.borderColor(Color.BLACK)
				.verticalAlignment(VerticalAlignment.MIDDLE)
				.horizontalAlignment(HorizontalAlignment.CENTER)
				.minHeight(15f)
				.paddingLeft(5f)
				.build();
	}
	
	//get header columns -> String arr with header names / titles
	List<HeaderColumn> getHeaderColumns(){
		List<HeaderColumn> headerColumns = new ArrayList<>();
		headerColumns.add(new HeaderColumn("Predmet", (float) 250));
		headerColumns.add(new HeaderColumn("Ocjena", (float) 250));
		return headerColumns;
	}
	
	//create table -> takes arguments for full table creation, including headerCells and rowCells
	private Table createTable(List<HeaderColumn> list, ReportCard report) {
		List<HashMap<String, Double>> grades  =  (List<HashMap<String, Double>>) report.getAvgOcjene();
		final Table.TableBuilder tableBuilder = Table.builder();
		final RowBuilder headerRowBuilder = Row.builder();
		
		for(var column : list ) {
			tableBuilder.addColumnOfWidth(column.getWidth());
			TextCell headerCell = createHeaderCell(column.getText());
			headerRowBuilder.add(headerCell);
		}
		
		Row tableHeaderRow = headerRowBuilder.build();
		tableBuilder.addRow(tableHeaderRow);
		
		 for(HashMap<String, Double> entry : grades) {
		    	for(String key : entry.keySet()) {
		    		String grade = entry.get(key).toString();
		    		Row tableBodyRow = Row.builder()
		    				.add(createRowCell(key))
		    				.add(createRowCell(grade))
		    				.build();
		    		tableBuilder.addRow(tableBodyRow);
		    	}
		    }
		
		return tableBuilder.build();
	}
	
	//saveAsByteArray -> saves pdf as byte arr and sends it further
	private byte[] saveAsByteArray(byte[] pdf, PDDocument doc) {
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
			doc.save(bos);
			pdf = bos.toByteArray();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pdf;
	}
	
	//save on disc -> saves pdf on destination file
	private void saveOnDisc(PDDocument doc, Osoba osoba) {
		try {
			doc.save(DEST + osoba.getPrezime() +"test.pdf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
