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

import com.skole.project.entity.Ocjena;
import com.skole.project.exception.Message;
import com.skole.project.ocjena.OcjenaService;
import com.skole.project.osoba.OsobaService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/ocjene")
public class OcjenaController {

	Logger LOGGER = LoggerFactory.getLogger(OcjenaController.class);
	
	@Autowired
	OcjenaService ocjenaService;
	
	@Autowired 
	OsobaService osobaService;
	
	@GetMapping("/")
	public List<Ocjena> getAll(){
		return ocjenaService.getAllOcjene();
	}
	
	@GetMapping("/osoba/{id}")
	public ResponseEntity<?> getAllByUcenik(@PathVariable Integer id){
		List<Ocjena> ocjene = null;
		LocalDate timestamp = LocalDate.now();
		try {
			ocjene = ocjenaService.getOcjenaByIdOsoba(id);
		}catch(Exception e) {
			LOGGER.error(String.format("Nisu pronađene ocjene za osobu ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nisu pronađene ocjene za osobu ID: %d", id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(ocjene); 
	}
	@GetMapping("/predmeti/{id}")
	public ResponseEntity<?> getAllByPredmet(@PathVariable Integer id){
		List<Ocjena> ocjene = null;
		LocalDate timestamp = LocalDate.now();
		try {
			ocjene = ocjenaService.getOcjenaByIdPredmet(id);
		}catch(Exception e) {
			LOGGER.error(String.format("Nisu pronađene ocjene za predmet ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nisu pronađene ocjene za predmet ID: %d", id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(ocjene);  
	}
	@GetMapping("/ocjena/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Ocjena ocjena = null;
		LocalDate timestamp = LocalDate.now();
		try {
			ocjena = ocjenaService.getOcjenaById(id);
		} catch(Exception e) {
			LOGGER.error(String.format("Nije pronađena ocjena sa ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Nije pronađena ocjena sa ID: %d.", id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(ocjena);  
	}
	
	@PostMapping("/ocjena/add")
	public ResponseEntity<?> create(@RequestBody Ocjena ocjena) {
		LocalDate timestamp = LocalDate.now();
		try {
			ocjenaService.createOcjena(ocjena);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage(), timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@PutMapping("/ocjena/update")
	public ResponseEntity<?> update(@RequestBody Ocjena ocjena) {
		LocalDate timestamp = LocalDate.now();
		Boolean stat = false;
		try {
			stat = ocjenaService.updateOcjena(ocjena);
			LOGGER.info(String.format("Uspješno ažurirana ocjena ID: %d", ocjena.getIdOcjena()));
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom ažuriranja ocjene. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Greška prilikom ažuriranja ocjene.", timestamp));
		}
		if(!stat) {
			LOGGER.error(String.format("Ocjena sa ID: %d nije pronađena", ocjena.getIdOcjena()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Ocjena sa ID: %d nije pronađena", ocjena.getIdOcjena()), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno ažurirana ocjena ID: %d", ocjena.getIdOcjena()), 
				timestamp));
	}
	
	@DeleteMapping("/ocjena/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		LocalDate timestamp = LocalDate.now();
		Boolean stat = false;
		try {
			stat = ocjenaService.deleteOcjena(id);
			LOGGER.info(String.format("Uspješno obrisana ocjena ID: %d.", id));
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom brisanja ocjene. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Greška prilikom brisanja ocjene. Poruka: %s", e.getMessage()), 
					timestamp));
		}
		if(!stat) {
			LOGGER.error(String.format("Ocjena sa ID: %d nije pronađena",id));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Ocjena sa ID: %d nije pronađena",id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno obrisana ocjena ID: %d.", id),
				timestamp));
	};
	
}