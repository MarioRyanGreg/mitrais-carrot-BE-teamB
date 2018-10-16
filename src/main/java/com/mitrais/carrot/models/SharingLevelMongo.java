package com.mitrais.carrot.models;

import org.springframework.data.annotation.Id;

public class SharingLevelMongo {

	@Id
	public Integer id;

	public String grade;
	public Integer sharingLevel;
	
	public SharingLevelMongo() {}

	public SharingLevelMongo(Integer id, String grade, Integer sharingLevel) {
		this.id = id;
		this.grade = grade;
		this.sharingLevel = sharingLevel;
	}

	@Override
	public String toString() {
		return "SharingLevelMongo [id=" + id + ", grade=" + grade + ", sharingLevel=" + sharingLevel + "]";
	}
}
