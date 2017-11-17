package com.example.demo.vo;
// Generated 2017. 11. 13 ���� 11:40:39 by Hibernate Tools 4.3.5.Final


public class Hero implements java.io.Serializable {

	private long id;
	private String name;

	public Hero() {
	}

	public Hero(long id) {
		this.id = id;
	}

	public Hero(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
