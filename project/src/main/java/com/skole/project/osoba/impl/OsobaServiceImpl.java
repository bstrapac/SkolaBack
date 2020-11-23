package com.skole.project.osoba.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skole.project.entity.Osoba;
import com.skole.project.osoba.OsobaDAO;
import com.skole.project.osoba.OsobaService;

@Service
public class OsobaServiceImpl implements OsobaService {

	@Autowired
	OsobaDAO osobaDao;
	
	
	@Override
	public List<Osoba> getAllOsobe() {
		return osobaDao.getAllOsobe();
	}

	@Override
	public List<Osoba> getAllNastavnici() {
		return osobaDao.getAllNastavnici();
	}

	@Override
	public List<Osoba> getAllUcenici() {
		return osobaDao.getAllUcenici();
	}

	@Override
	public Osoba getOsobaByID(Integer id) {
		Osoba osoba = null;
		try {
			osoba = osobaDao.getOsobaByID(id);
		}catch(Exception e) {
			throw e;
		}
		return osoba;
	}

	@Override
	@Transactional
	public boolean createOsoba(Osoba osoba) {
		osoba.setIdOsoba(0);
		try {
			return osobaDao.createOsoba(osoba);
		}catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public boolean deleteOsoba(Integer id) {
		try {
			return osobaDao.deleteOsoba(id);
		}catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public boolean updateOsoba(Osoba osoba) {
		try {
			return osobaDao.updateOsoba(osoba);
		}catch(Exception e) {
			throw e;
		}	
	}

}
