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
	
	Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
	
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
		byte[] pdf = null;
		try {
			report = reportService.getReportCard(id);
			osoba = osobaService.getOsobaByID(id);
			pdf = gen.generatePDF(report, osoba);
			LOGGER.info("Uspješno kreirano pdf izvješće.");
			
		} catch(Exception e) {
			LOGGER.error(String.format("Greška prilikom kreiranja pdf izvješća. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Greška prilikom kreiranja pdf izvješća."), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdf);  
	}
	
	@GetMapping("/generateXlsx")
	public ResponseEntity<?> generateXlsx() {
		XSSFgenerator gen = new XSSFgenerator();
		byte[] report =  null;
		List<OcjenaRaw> ocjene = null;
		LocalDate timestamp = LocalDate.now();
		try {
			ocjene = reportService.getRawOcjene();
			report = gen.generateXlsx(ocjene);
			LOGGER.info("Uspješnno kreirano xlsx izvješće.");
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom kreiranja xlsx datoteke. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					String.format("Greška prilikom kreiranja xlsx datoteke"), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(report);
	}
}
