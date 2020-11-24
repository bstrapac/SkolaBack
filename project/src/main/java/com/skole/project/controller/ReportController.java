package com.skole.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skole.project.entity.Osoba;
import com.skole.project.exception.Message;
import com.skole.project.osoba.OsobaService;
import com.skole.project.report.OcjenaRaw;
import com.skole.project.report.PDFGenerator;
import com.skole.project.report.ReportCard;
import com.skole.project.report.ReportService;
import com.skole.project.report.XSSFgenerator;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/report")
public class ReportController {
	
	Logger LOGGER = LoggerFactory.getLogger(OcjenaController.class);
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	OsobaService osobaService;
	
	@GetMapping("/generatePDF/{id}")
	public ResponseEntity<?> genratePDF(@PathVariable Integer id) {
		ReportCard report = null;
		Osoba osoba = null;
		LocalDate timestamp = LocalDate.now();
		PDFGenerator gen = new PDFGenerator();
		try {
			report = reportService.getReportCard(id);
			osoba = osobaService.getOsobaByID(id);
			
		} catch(Exception e) {
			LOGGER.error(String.format("Nije pronađena reportCard za učenika sa ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nije pronađena reportCard za učenika sa ID: %d.", id), 
					timestamp));
		}
		gen.generatePDF(report, osoba);
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno kreiran pdf za učenika sa ID : %d.", id), 
				timestamp));  
	}
	
	@GetMapping("/generateXlsx")
	public byte[] generateXlsx() {
		XSSFgenerator gen = new XSSFgenerator();
		byte[] report =  null;
		List<OcjenaRaw> ocjene = null;
		ocjene = reportService.getRawOcjene();
		try {
			report = gen.generateXlsx(ocjene);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;
	}
	

}
