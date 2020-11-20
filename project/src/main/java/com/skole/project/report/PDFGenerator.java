package com.skole.project.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.skole.project.entity.Osoba;
import com.skole.project.entity.ReportCard;

public class PDFGenerator {

	public static final String DEST = "D:\\bstrapac\\Desktop\\test\\";
	
	public static void createReport (ReportCard report, Osoba osoba) {
	List<HashMap<String, Double>> grades  =  (List<HashMap<String, Double>>) report.getAvgOcjene();
		try {
			PDDocument pdDoc = new PDDocument();
			PDPage page = new PDPage();
			pdDoc.addPage(page);
			try(PDPageContentStream pcs = new PDPageContentStream(pdDoc, page)) {
				pcs.beginText();
				pcs.setFont(PDType1Font.TIMES_ROMAN, 12);
			    pcs.setLeading(14.5f);
			    pcs.newLineAtOffset(25, 725);
			    pcs.showText(osoba.getIme()+" "+osoba.getPrezime().replace("ć", "c"));
			    pcs.newLine();
			    pcs.showText(osoba.getDob());
			    pcs.newLine();
			    pcs.showText("OIB: "+ osoba.getOib());
			    pcs.newLine();
			    for(HashMap<String, Double> entry : grades) {
			    	for(String key : entry.keySet()) {
			    		Double grade = entry.get(key);
			    		pcs.showText(key + " : " + grade);
			    		pcs.newLine();
			    	}
			    }
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
