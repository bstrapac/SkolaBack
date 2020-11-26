package com.skole.project.report;

import java.util.List;

import com.skole.project.report.entity.OcjenaRaw;
import com.skole.project.report.entity.OsobaRaw;
import com.skole.project.report.entity.PredmetOsobaRaw;
import com.skole.project.report.entity.ReportCard;

public interface ReportService {
	
	List<OcjenaRaw> getRawOcjene();
	
	List<OsobaRaw> getRawOsobe();
	
	List<PredmetOsobaRaw> getPredmetOsobaRaw();
	
	ReportCard getReportCard(Integer id);

}
