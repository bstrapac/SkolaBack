package com.skole.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skole.project.entity.Osoba;
import com.skole.project.entity.Predmet;
import com.skole.project.exception.Message;
import com.skole.project.osoba.OsobaService;
import com.skole.project.predmet.PredmetService;
import com.skole.project.report.PDFGenerator;
import com.skole.project.report.ReportService;
import com.skole.project.report.XSSFgenerator;
import com.skole.project.report.entity.OcjenaRaw;
import com.skole.project.report.entity.OsobaRaw;
import com.skole.project.report.entity.PredmetOsobaRaw;
import com.skole.project.report.entity.ReportCard;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/report")
public class ReportController {
	
	Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	OsobaService osobaService;
	
	@Autowired
	PredmetService predmetService;
	
	@GetMapping("/generatePDF/{id}")
	public ResponseEntity<?> genratePDF(@PathVariable Integer id) {
		PDFGenerator gen = new PDFGenerator();
		byte[] pdf = null;
		byte[] encoded = null;
		HttpHeaders headers = new HttpHeaders();
		String filename = "file.pdf";
		
		ReportCard report = null;
		Osoba osoba = null;
		LocalDate timestamp = LocalDate.now();
		
		headers.set("Content-Type", "application/pdf; charset=UTF-8");
		headers.set("Content-Disposition", "attachment; filename="+ filename);
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check = 0, pre-check= 0");
		
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
		
		encoded = java.util.Base64.getEncoder().encode(pdf);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(encoded);  
	}
	
	@GetMapping("/showReport/{id}")
	public ResponseEntity<?> showReport(@PathVariable Integer id){
		ReportCard report = null;
		LocalDate timestamp = LocalDate.now();
		try {
			report = reportService.getReportCard(id);
			LOGGER.info("Uspješno kreirano izvješće.");
			
		} catch(Exception e) {
			LOGGER.error(String.format("Greška prilikom kreiranja izvješća. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Greška prilikom kreiranja izvješća."), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(report);  
	}
	
	
	@GetMapping("/generateXlsx")
	public ResponseEntity<?> generateXlsx() {
		XSSFgenerator gen = new XSSFgenerator();
		byte[] report =  null;
		byte[] encoded = null;
		HttpHeaders headers = new HttpHeaders();
		String filename = "file.xlsx";
		
		List<OcjenaRaw> ocjene = null;
		List<OsobaRaw> osobe = null;
		List<Predmet> predmeti = null;
		List<PredmetOsobaRaw> predmetOsoba = null;
		LocalDate timestamp = LocalDate.now();
		
		headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
		headers.set("Content-Disposition", "attachment; filename="+ filename);
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check = 0, pre-check= 0");
		
		try {
			ocjene = reportService.getRawOcjene();
			osobe = reportService.getRawOsobe();
			predmeti = predmetService.getAllPredmeti();
			predmetOsoba = reportService.getPredmetOsobaRaw();
			report = gen.generateXlsx(ocjene, osobe, predmeti, predmetOsoba);
			LOGGER.info("Uspješnno kreirano xlsx izvješće.");
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom kreiranja xlsx datoteke. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					String.format("Greška prilikom kreiranja xlsx datoteke"), 
					timestamp));
		}
		
		encoded = java.util.Base64.getEncoder().encode(report);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(encoded);
	}
}
