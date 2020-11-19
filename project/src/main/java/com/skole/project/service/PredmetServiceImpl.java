package com.skole.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skole.project.dao.PredmetDAO;
import com.skole.project.entity.Predmet;

@Service
public class PredmetServiceImpl implements PredmetService {

	@Autowired
	private PredmetDAO predmetDao;
	
	@Override
	public List<Predmet> getAllPredmeti() {
		return predmetDao.getAllPredmeti();
	}

	@Override
	public Predmet getPredmetById(Integer id) {
		Predmet predmet =  null;
		try {
			predmet = predmetDao.getPredmetById(id);
		}catch(Exception e) {
			throw e;
		}
		return predmet;
	}

	@Override
	@Transactional
	public boolean createPredmet(Predmet predmet) {
		try {
			return predmetDao.createPredmet(predmet);
		}catch(Exception e) {
			throw e;
		}
		
	}
	@Override
	@Transactional
	public boolean deletePredmet(Integer id) {
		try {
			return predmetDao.deletePredmet(id);
		}catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public boolean updatePredmet(Predmet predmet){
		try {
			return predmetDao.updatePredmet(predmet);
		} catch (Exception e) {
			throw e;
		}
	}

}
