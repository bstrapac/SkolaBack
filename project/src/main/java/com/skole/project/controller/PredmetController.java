package com.skole.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skole.project.entity.Predmet;
import com.skole.project.exception.Message;
import com.skole.project.service.PredmetService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/predmeti")
public class PredmetController {
	
	Logger LOGGER = LoggerFactory.getLogger(PredmetController.class);
	
	@Autowired 
	PredmetService predmetService;
	
	@GetMapping("/")
	public List<Predmet> getAll() {
		return predmetService.getAllPredmeti();
	}
	
	@GetMapping("/predmet/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id){
		LocalDate timestamp = LocalDate.now();
		Predmet predmet = null;
		try {
			predmet =  predmetService.getPredmetById(id);
		} catch (Exception e) {
			LOGGER.error(String.format("Nije pronađen predmet sa ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nije pronađen predmet sa ID: %d", id),
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(predmet);
	}
	
	@PostMapping("/predmet/add")
	public ResponseEntity<?> createNew( @RequestBody Predmet predmet){
		LocalDate timestamp = LocalDate.now();
		try {
			predmetService.createPredmet(predmet);
			LOGGER.info("Uspješno spremljen novi predmet u bazu.");
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom spremanja novog predmeta u bazu. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					"Greška  prilikom spremanja novog predmeta u bazu.", 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				"Uspješno spremljen novi predmet u bazu", 
				timestamp));
	}
	
	@PutMapping("/predmet/update")
	public ResponseEntity<?> update(@RequestBody Predmet predmet){
		LocalDate timestamp = LocalDate.now();
		Boolean stat = false; 
		try {
			stat = predmetService.updatePredmet(predmet);
			LOGGER.info(String.format("Ažuriran predmet ID: %d", predmet.getIdPredmet()));
		} catch (Exception e) {
			LOGGER.error(String.format("Greša prilikom ažuriranja predmeta. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					"Greška prilikom ažuriranja predmeta.", 
					timestamp));
		}
		if(!stat)
		{
			LOGGER.error(String.format("Predmet sa ID: %d nije pronađen.", predmet.getIdPredmet()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Predmet sa ID: %d nije pronađen.", predmet.getIdPredmet()), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno ažuriran predmet ID: %d.", predmet.getIdPredmet()), 
				timestamp));
	}
	
	@DeleteMapping("/predmet/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		LocalDate timestamp = LocalDate.now();
		Boolean stat = false;
		try {
			if(id > 0 ) {
				stat = predmetService.deletePredmet(id);
				LOGGER.info(String.format("Obrisan predmet ID: %d", id));
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Greša prilikom brisanja predmeta. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					"Greška prilikom brisanja predmeta.", 
					timestamp));
		}
		if(!stat) {
			LOGGER.error(String.format("Predmet sa ID: %d nije pronađen.", id));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					"Predmet za brisanje nije pronađen.", 
					timestamp));
		}
		return  ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno obrisan predmet ID: %d", id), 
				timestamp));		
	}
}