package com.sps.data.enumeration;

public enum Role {
	ROLE_ADMINISTRATOR("ADMINISTRATOR"),
	ROLE_USER("USER");
	private String text;

	Role(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
