package com.skole.project.predmetosoba;

import java.util.List;

import com.skole.project.entity.PredmetOsoba;

public interface PredmetOsobaDAO {
	
	List<PredmetOsoba> getAllPredmetOsoba();
	
	List<PredmetOsoba> getAllPredmetOsobaByIdOsoba(Integer id);
	
	List<PredmetOsoba> getAllPredmetOsobaByIdPredmet(Integer id);

	PredmetOsoba getPredmetOsobaById(Integer id);
	
	Integer getExisting(Integer id_osoba, Integer id_predmet);
	
	Integer createAndGet(Integer id_osoba, Integer id_predmet);
	
	boolean createPredmetOsoba(PredmetOsoba predmetOsoba);
	
	boolean deletePredmetOsoba(Integer id);
}
