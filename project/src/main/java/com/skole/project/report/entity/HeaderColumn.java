package com.skole.project.report.entity;

public class HeaderColumn {

	String text;
	Float width;
	
	public HeaderColumn() {
		
	}
	
	public HeaderColumn(String text, Float width) {
		super();
		this.text = text;
		this.width = width;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	
	
}
