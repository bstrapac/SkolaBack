package com.skole.project.report.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skole.project.report.ReportDAO;
import com.skole.project.report.ReportService;
import com.skole.project.report.entity.OcjenaRaw;
import com.skole.project.report.entity.OsobaRaw;
import com.skole.project.report.entity.PredmetOsobaRaw;
import com.skole.project.report.entity.ReportCard;

@Service
public class ReportServiceimpl implements ReportService {

	@Autowired 
	ReportDAO reportDao;
	
	public ReportServiceimpl() {
	}

	@Override
	public List<OcjenaRaw> getRawOcjene() {
		return reportDao.getRawOcjene();
	}
	
	@Override
	public List<OsobaRaw> getRawOsobe() {
		return reportDao.getRawOsobe();
	}

	@Override
	public List<PredmetOsobaRaw> getPredmetOsobaRaw() {
		// TODO Auto-generated method stub
		return reportDao.getPredmetOsobaRaw();
	}
	
	@Override
	public ReportCard getReportCard(Integer id) {
		try {
			return reportDao.getReportCard(id);
		}catch (Exception e){
			throw e;
		}
	}
}
