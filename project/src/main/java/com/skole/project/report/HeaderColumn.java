package com.skole.project.report;

public class HeaderColumn {
	public String text;
	public Float width;
	
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
