package com.sps.data.enumeration;

public enum ParagraphIndexType {
	CHARACTER("CHARACTER"),
	RECTANGLE_BULLET("RECTANGLE BULLET"),
	SQUARE_BULLET("SQUARE BULLET"),
	CIRCLE_BULLET("CIRCLE BULLET");
	private String text;

	ParagraphIndexType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
