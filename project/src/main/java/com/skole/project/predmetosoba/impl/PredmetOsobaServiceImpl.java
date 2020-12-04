package com.skole.project.predmetosoba.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skole.project.entity.PredmetOsoba;
import com.skole.project.predmetosoba.PredmetOsobaDAO;
import com.skole.project.predmetosoba.PredmetOsobaService;

@Service
public class PredmetOsobaServiceImpl implements PredmetOsobaService {

	Logger LOGGER = LoggerFactory.getLogger(PredmetOsobaService.class);
	
	@Autowired
	PredmetOsobaDAO predmetOsobaDao;
	
	@Override
	public List<PredmetOsoba> getAllPredmetOsoba() {
		return predmetOsobaDao.getAllPredmetOsoba();
	}

	@Override
	public List<PredmetOsoba> getAllPredmetOsobaByIdOsoba(Integer id) {
		List<PredmetOsoba> list = null;
		try {
			list =  predmetOsobaDao.getAllPredmetOsobaByIdOsoba(id);
		}catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	@Override
	public List<PredmetOsoba> getAllPredmetOsobaByIdPredmet(Integer id) {
		List<PredmetOsoba> list = null;
		try {
			list =  predmetOsobaDao.getAllPredmetOsobaByIdPredmet(id);
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public PredmetOsoba getPredmetOsobaById(Integer id) {
		PredmetOsoba predmetOsoba = null;
		try {
			predmetOsoba = predmetOsobaDao.getPredmetOsobaById(id);
		}catch(Exception e) {
			throw e;
		}
		return predmetOsoba;	
	}

	@Override
	@Transactional
	public boolean createPredmetOsoba(PredmetOsoba predmetOsoba) {
		try {
			return predmetOsobaDao.createPredmetOsoba(predmetOsoba);
		}catch(Exception e){
			throw e;			
		}
	}

	@Override
	@Transactional
	public boolean deletePredmetOsoba(Integer id) {
		try {
			return predmetOsobaDao.deletePredmetOsoba(id);
		}catch(Exception e){
			throw e;			
		}	
	}

	@Override
	@Transactional
	public Integer getExisting(Integer idOsoba, Integer idPredmet) {
		Integer idZapis = null;
		try {
			idZapis = predmetOsobaDao.getExisting(idOsoba, idPredmet);
		} catch(Exception e) {
			LOGGER.info(String.format("Ne postosji zapis sa idOsoba : %d te idPredmet : %d. Stvaram novi zapis. Poruka: %s", idOsoba, idPredmet, e.getMessage()));
			idZapis = predmetOsobaDao.createAndGet(idOsoba, idPredmet);
		}
		return idZapis;
	}

	@Override
	@Transactional
	public Integer createAndGet(Integer idOsoba, Integer idPredmet) {
		Integer idZapis = null;
		try {
			idZapis = predmetOsobaDao.createAndGet(idOsoba, idPredmet);
		} catch(Exception e) {
			throw e;
		}
		return idZapis;
	}
}
