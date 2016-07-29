package com.sgoshika.user.model;

public class Company {
	private String name;
	private String website;

	public String getName() {
		return name;
	}

	public Company() {
	}

	public Company(String name, String website) {
		super();
		this.name = name;
		this.website = website;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
