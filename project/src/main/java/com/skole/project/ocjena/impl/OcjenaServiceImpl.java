package com.skole.project.ocjena.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skole.project.entity.Ocjena;
import com.skole.project.ocjena.OcjenaDAO;
import com.skole.project.ocjena.OcjenaService;

@Service
public class OcjenaServiceImpl implements OcjenaService {

	@Autowired
	OcjenaDAO ocjenaDao;
		
	@Override
	public List<Ocjena> getAllOcjene() {
		return ocjenaDao.getAllOcjene();
	}

	@Override
	public List<Ocjena> getOcjenaByIdOsoba(Integer id) {
		List<Ocjena> ocjene = null;
		
		try {
			ocjene = ocjenaDao.getOcjenaByIdOsoba(id);
		}catch(Exception e) {
			throw e;
		}
		return ocjene;
	}

	@Override
	public List<Ocjena> getOcjenaByIdPredmet(Integer id) {
		List<Ocjena> ocjene = null;
		
		try {
			ocjene = ocjenaDao.getOcjenaByIdPredmet(id);
		}catch(Exception e) {
			throw e;
		}
		return ocjene;		
	}

	@Override
	public Ocjena getOcjenaById(Integer id) {
		Ocjena ocjena = null;
		
		try {
			ocjena = ocjenaDao.getOcjenaById(id);
		}catch(Exception e) {
			throw e;
		}
		return ocjena;
	}

	@Override
	@Transactional
	public boolean createOcjena(Ocjena ocjena) {
		try {
			return ocjenaDao.createOcjena(ocjena);
		}catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public boolean deleteOcjena(Integer id) {
		try {
			return ocjenaDao.deleteOcjena(id);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	@Transactional
	public boolean updateOcjena(Ocjena ocjena) {
		try {
			return ocjenaDao.updateOcjena(ocjena);
		}catch(Exception e){
			throw e;
		}
	}

}