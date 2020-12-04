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

import com.skole.project.entity.PredmetOsoba;
import com.skole.project.exception.Message;
import com.skole.project.predmetosoba.PredmetOsobaService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/predmetosoba")
public class PredmetOsobaController {
	
	Logger LOGGER = LoggerFactory.getLogger(PredmetOsobaController.class); 

	@Autowired
	PredmetOsobaService predmetOsobaService;
	
	@GetMapping("/")
	public List<PredmetOsoba> getAll(){
		return predmetOsobaService.getAllPredmetOsoba();
	}
	@GetMapping("/osoba/{id}")
	public ResponseEntity<?> getAllByOsobaId(@PathVariable Integer id){
		LocalDate timestamp = LocalDate.now();
		List<PredmetOsoba> list = null;
		try {
			list = predmetOsobaService.getAllPredmetOsobaByIdOsoba(id);
		}catch(Exception e) {
			LOGGER.error(String.format("Nisu pronađeni predmeti za osobu sa ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nisu pronađeni predmeti za osobu ID: %d.", id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	@GetMapping("/predmet/{id}")
	public ResponseEntity<?> getAllByPredmetId(@PathVariable Integer id){
		LocalDate timestamp = LocalDate.now();
		List<PredmetOsoba> list = null;
		try {
			list = predmetOsobaService.getAllPredmetOsobaByIdPredmet(id);
		}catch(Exception e) {
			LOGGER.error(String.format("Nisu pronađeni zapisi za predmet ID: %d", id), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nisu pronađeni zapisi za predmet ID: %d", id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		LocalDate timestamp = LocalDate.now();
		PredmetOsoba predmetOsoba = null;
		try {
			predmetOsoba = predmetOsobaService.getPredmetOsobaById(id);
		} catch (Exception e) {
			LOGGER.error(String.format("Nije pronađen zapis sa ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nije pronađen zapis sa ID: %d", id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(predmetOsoba);
	}
	
	@GetMapping("/{idOsoba}/{idPredmet}")
	public ResponseEntity<?> getExisting(@PathVariable Integer idOsoba, @PathVariable Integer idPredmet) {
		LocalDate timestamp = LocalDate.now();
		Integer idZapis = null;
		try {
			idZapis = predmetOsobaService.getExisting(idOsoba, idPredmet);
		} catch (Exception e) {
			LOGGER.error(String.format("Nisu pronađeni zapisi za osoba ID: %d te predmet ID: %d. Poruka: %s", idOsoba, idPredmet, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nisu pronađeni zapisi za osoba ID: %d te predmet ID: %d.", idOsoba, idPredmet), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(idZapis);
	}
	@GetMapping("/create/{idOsoba}/{idPredmet}")
	public ResponseEntity<?> createAndGet(@PathVariable Integer idOsoba, @PathVariable Integer idPredmet) {
		LocalDate timestamp = LocalDate.now();
		Integer idZapis = null;
		try {
			idZapis = predmetOsobaService.createAndGet(idOsoba, idPredmet);
			LOGGER.info(String.format("Kreiran je novi zapis predmetOsoba. Osoba ID: %d, predmet ID: %d.", idOsoba, idPredmet));
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom kreiranja novog zapisa te dohvaćanja ID-a istog. Poruka: %s ", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					"Greška prilikom kreiranja novog zapisa te dohvaćanja ID-a istog.", 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(idZapis);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> createNew(@RequestBody PredmetOsoba predmetOsoba) {
		LocalDate timestamp = LocalDate.now();
		try {
			predmetOsobaService.createPredmetOsoba(predmetOsoba);
			LOGGER.info("Spremljen novi zapis predmetOsoba");
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom spremanja novog zapisa. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Greška prilikom spremanja novog zapisa.", timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message("Spremljen novi zapis predmetOsoba.", timestamp));
	}
		
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		LocalDate timestamp = LocalDate.now();
		Boolean stat = false;
		try {
			stat = predmetOsobaService.deletePredmetOsoba(id);
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom brisanja zapisa predmetOsoba sa ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					String.format("Greška prilikom brisanja zapisa predmetOsoba sa ID: %d.", id), 
					timestamp));
		}
		LOGGER.info(String.format("Uspješno obrisan zapis predmetOsoba ID: %d.", id));
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno obrisan zapis predmetOsoba ID: %d.", id),
				timestamp));
	}
}