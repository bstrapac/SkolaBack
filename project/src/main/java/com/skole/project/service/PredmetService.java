package com.skole.project.service;

import java.util.List;

import com.skole.project.entity.Predmet;

public interface PredmetService {
	
	List<Predmet> getAllPredmeti();
	
	Predmet getPredmetById(Integer id);
	
	boolean createPredmet(Predmet predmet);
	
	boolean deletePredmet(Integer id);
	
	boolean updatePredmet(Predmet predmet);
}