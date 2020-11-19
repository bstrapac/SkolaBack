package com.skole.project.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.skole.project.entity.Osoba;
import com.skole.project.entity.ReportCard;

public class PDFGenerator {

	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	public static List<?> grades  = new ArrayList<>();
	public static void createReport (ReportCard report, Osoba osoba) {
		try {
			PDDocument pdDoc = new PDDocument();
			PDPage page = new PDPage();
			pdDoc.addPage(page);
			grades = report.getAvgOcjene();
			try(PDPageContentStream pcs = new PDPageContentStream(pdDoc, page)) {
				pcs.beginText();
				pcs.setFont(PDType1Font.TIMES_ROMAN, 12);
			    pcs.setLeading(14.5f);
			    pcs.newLineAtOffset(25, 725);
			    pcs.showText(osoba.getIme()+" "+osoba.getPrezime().replace("ć", "c"));
			    pcs.newLine();
			    pcs.showText(osoba.getDob() + ", OIB: "+ osoba.getOib());
			    pcs.newLine();
			    grades.forEach((g) -> {
			    	try {
			    		String line = g.toString();
			    		String newLine = line.replace("{", "").replace("}", "").replace("=", " : ");
						pcs.showText(newLine);
						pcs.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    });
				pcs.showText("Završni prosjek : " + report.getFinalGrade().toString());
				pcs.newLine();
				pcs.endText();
			}
			pdDoc.save(DEST + osoba.getIme() + osoba.getPrezime() + ".pdf");
			pdDoc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
