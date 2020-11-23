package com.skole.project.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<?> getRawOsobe() {
		// TODO Auto-generated method stub
		return null;
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
