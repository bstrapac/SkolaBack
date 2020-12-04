package com.skole.project.report;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Row.RowBuilder;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import com.skole.project.entity.Osoba;
import com.skole.project.report.entity.HeaderColumn;
import com.skole.project.report.entity.ReportCard;

import rst.pdfbox.layout.text.Alignment;
import rst.pdfbox.layout.text.Constants;
import rst.pdfbox.layout.text.Position;
import rst.pdfbox.layout.text.TextFlow;

public class PDFGenerator {

	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	private static final Color LIGHT_BLUE = new Color(120, 166, 222);
	private static final Color BACKGROUND = new Color(236, 242, 250);
	private static final int TEXT_FONT_SIZE = 12;
	private static final int TITLE_FONT_SIZE = 16;
	private static final int FONT_SIZE = 14;
	
	//generate byte array pdf
	//method will create and fill pdf with table and data
	public byte[] generatePDF(ReportCard report, Osoba osoba) {
		byte[] pdf = null;
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	    InputStream SansRegular = classloader.getResourceAsStream("LiberationSans-Regular.ttf");
	    InputStream SansBold = classloader.getResourceAsStream("LiberationSans-Bold.ttf");
	    InputStream SansBoldItalic = classloader.getResourceAsStream("LiberationSans-BoldItalic.ttf");
	    
		PDDocument doc = new PDDocument();
		try {
			
			PDType0Font RegularFont = PDType0Font.load(doc, SansRegular, true);
		    PDType0Font BoldFont = PDType0Font.load(doc, SansBold, true);
		    PDType0Font BoldItalicFont = PDType0Font.load(doc, SansBoldItalic, true);
			
			PDPage page = new PDPage(Constants.A4);//842pt × 595pt
			doc.addPage(page);
			
			try(PDPageContentStream pcs = new PDPageContentStream(doc, page)){
				
				//page title
				getTitle(pcs, page, BoldFont);
				
				//person info
				getInfo(pcs, page, osoba, RegularFont);

				//final grades table
				Table table = createTable(getHeaderColumns(), report, RegularFont);
				drawTable(page, pcs, table);
				
				//final grade
				finalGrade(pcs, page, report, RegularFont, BoldItalicFont);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pdf = saveAsByteArray(pdf, doc);
		try {
			doc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdf;
	}
	
	private void finalGrade(PDPageContentStream pcs, PDPage page, ReportCard report, PDType0Font Regular, PDType0Font BoldItalic) {
		//final grade note
		TextFlow finalGrade = new TextFlow();
		DecimalFormat df2 = new DecimalFormat("#.##");
		try {
			finalGrade.addText("Završna ocjena: ", FONT_SIZE, Regular);
			finalGrade.addText(df2.format(report.getFinalGrade()).toString(), FONT_SIZE, BoldItalic);
			finalGrade.setMaxWidth(200);
			finalGrade.drawText(pcs, new Position(350, page.getMediaBox().getHeight()-500), Alignment.Left, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String dateFormat(String inputDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pattern = "dd.MMMMM yyyy.";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("hr", "HR"));
		String formatedDate = sdf.format(date);
		return formatedDate;
	}

	private void getInfo(PDPageContentStream pcs, PDPage page, Osoba osoba, PDType0Font font) {
		//osoba info -> name, lastname, dob, oib
		String name = osoba.getIme() + " " + osoba.getPrezime() +"\n";
		String oib = "OIB: " + osoba.getOib() + "\n";
		String dob = "Datum rođenja: \n" + dateFormat(osoba.getDob()); 
		TextFlow left = new TextFlow();
		try {
			left.addText(name, TEXT_FONT_SIZE, font );
			left.addText(oib, TEXT_FONT_SIZE, font);
			left.addText(dob, TEXT_FONT_SIZE, font);
			left.setMaxWidth(150);
			left.drawText(pcs, new Position(50, page.getMediaBox().getHeight()-160), Alignment.Left, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//osoba additional info -> address, phone, mail
		TextFlow right = new TextFlow();
		try {
			right.addText(osoba.getAdresa(), TEXT_FONT_SIZE, font);
			right.addText("\n", TEXT_FONT_SIZE, font);
			right.addText(osoba.getKontakt(), TEXT_FONT_SIZE, font);
			right.addText("\n", TEXT_FONT_SIZE, font);
			right.addText(osoba.getMail(), TEXT_FONT_SIZE, font);
			right.addText("\n", TEXT_FONT_SIZE, font);
			right.setMaxWidth(150);
			right.drawText(pcs, new Position(400, page.getMediaBox().getHeight()-160), Alignment.Left, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getTitle(PDPageContentStream pcs, PDPage page, PDType0Font font) {
		TextFlow title = new TextFlow();
		try {
			title.addText("Report Card", TITLE_FONT_SIZE, font);
			title.setMaxWidth(100);
			title.drawText(pcs, new Position(250, page.getMediaBox().getHeight()-80), Alignment.Center, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//draw table -> takes arguments for drawing a table
	private void drawTable(PDPage page, PDPageContentStream pcs, Table table) {
		TableDrawer tableDrawer = TableDrawer.builder()
											.contentStream(pcs)
											.startX(50f)
											.startY(page.getMediaBox().getUpperRightY()-250f)
											.table(table)
											.build();
		tableDrawer.draw();
	}
	
	//createHeaderCell -> creates table header
	private TextCell createHeaderCell(String text, PDType0Font font) {
		return TextCell.builder()
					.text(text)
					.font(font)
					.fontSize(FONT_SIZE)
					.backgroundColor(LIGHT_BLUE)
					.textColor(Color.BLACK)
					.borderWidth(0.3f)
					.verticalAlignment(VerticalAlignment.MIDDLE)
					.horizontalAlignment(HorizontalAlignment.CENTER)
					.build();
	}
	
	//createRowCell -> creates table rows
	private TextCell createRowCell(String text, PDType0Font font) {
		return TextCell.builder()
				.text(text)
				.font(font)
				.fontSize(FONT_SIZE)
				.backgroundColor(BACKGROUND)
				.textColor(Color.BLACK)
				.borderWidth(0.3f)
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
	private Table createTable(List<HeaderColumn> list, ReportCard report, PDType0Font font) {
		List<HashMap<String, Double>> grades  =  (List<HashMap<String, Double>>) report.getAvgOcjene();
		final Table.TableBuilder tableBuilder = Table.builder();
		final RowBuilder headerRowBuilder = Row.builder();
		
		for(var column : list ) {
			tableBuilder.addColumnOfWidth(column.getWidth());
			TextCell headerCell = createHeaderCell(column.getText(), font);
			headerRowBuilder.add(headerCell);
		}
		
		Row tableHeaderRow = headerRowBuilder.build();
		tableBuilder.addRow(tableHeaderRow);
		
		 DecimalFormat df2 = new DecimalFormat("#.##");
		 for(HashMap<String, Double> entry : grades) {
		    	for(String key : entry.keySet()) {
		    		String grade = df2.format(entry.get(key)).toString();
		    		Row tableBodyRow = Row.builder()
		    				.add(createRowCell(key, font))
		    				.add(createRowCell(grade, font))
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
}
