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

import com.skole.project.entity.Osoba;
import com.skole.project.exception.Message;
import com.skole.project.osoba.OsobaService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/osobe")
public class OsobaController {
	
	Logger LOGGER = LoggerFactory.getLogger(OsobaController.class);
	@Autowired
	OsobaService osobaService;
	
	@GetMapping("/") 
	public List<Osoba> getAll(){
		return osobaService.getAllOsobe();
	}
	@GetMapping("/nastavnici") 
	public List<Osoba> getAllNastavnici(){
		return osobaService.getAllNastavnici();
	}
	@GetMapping("/ucenici") 
	public List<Osoba> getAllUcenici(){
		return osobaService.getAllUcenici();
	}
	@GetMapping("/osoba/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Osoba osoba = null; 
		LocalDate timestamp = LocalDate.now();
		try {
			osoba = osobaService.getOsobaByID(id);
		} catch(Exception e) {
			LOGGER.error(String.format("Osoba sa ID: %d nije pronađena. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(
					String.format("Osoba sa ID: %d nije pronađena.", id), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(osoba);
	}
	
	@PostMapping("/osoba/add")
	public ResponseEntity<?> createNew(@RequestBody Osoba osoba) {
		LocalDate timestamp = LocalDate.now();
		try {
			osobaService.createOsoba(osoba);
			LOGGER.info("Uspješno dodana nova osoba.");
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom dodavanja nove osobe. Poruka: %s", e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Greška prilikom dodavanja nove osobe.", timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message("Uspješno dodana nova osoba.", timestamp));
	}
	
	@PutMapping("/osoba/update")
	public ResponseEntity<?> update(@RequestBody Osoba osoba) {
		LocalDate timestamp = LocalDate.now();
		Boolean stat = false;
		try {
			osobaService.updateOsoba(osoba);
			LOGGER.info(String.format("Uspješno ažurirana osoba ID: %d", osoba.getIdOsoba()));
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom ažuiranja osobe ID: %d. Poruka: %s ", osoba.getIdOsoba(), e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					String.format("Greška prilikom ažuriranja osobe ID: %d.", osoba.getIdOsoba()), 
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno ažurirana osoba ID: %d", osoba.getIdOsoba()),
				timestamp));
	}
	
	@DeleteMapping("/osoba/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		LocalDate timestamp = LocalDate.now();
		Boolean stat = false;
		try {
			stat = osobaService.deleteOsoba(id);
			LOGGER.info(String.format("Uspješno obrisana osoba sa ID: %d", id));
		} catch (Exception e) {
			LOGGER.error(String.format("Greška prilikom brisanja osobe ID: %d. Poruka: %s", id, e.getMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(
					String.format("Greška prilikom brisanja osobe ID: %id.", id),
					timestamp));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Message(
				String.format("Uspješno obrisana osoba sa ID: %d", id),
				timestamp));
	}
}
