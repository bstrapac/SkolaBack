package com.skole.project.entity;

import java.util.List;

public class ReportCard {
	public List<?> avgOcjene;
	public Double finalGrade;
	
	public ReportCard() {
	}
	
	public ReportCard(List<?> avgOcjene, Double finalGrade) {
		super();
		this.avgOcjene = avgOcjene;
		this.finalGrade = finalGrade;
	}
	
	public List<?> getAvgOcjene() {
		return avgOcjene;
	}
	public void setAvgOcjene(List<?> avgOcjene) {
		this.avgOcjene = avgOcjene;
	}
	public Double getFinalGrade() {
		return finalGrade;
	}
	public void setFinalGrade(Double finalGrade) {
		this.finalGrade = finalGrade;
	}
	
	@Override
	public String toString() {
		return "ReportCard [avgOcjene=" + avgOcjene + ", finalGrade=" + finalGrade + "]";
	}
}
