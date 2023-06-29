package com.ezen.model;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Lectors {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lector_seq")
	@SequenceGenerator(name = "lector_seq", sequenceName = "lector_seq", allocationSize = 1)
	private int lector_num;

	private int user_id;

	private String have_lecture_room;
	private String lecture_room_address = "";

	private String prefer_personnel;
	private String lecture_theme;
	private String career;
	
	private double review_score;
	private String reviewWriteCheck;
	
	@Column(columnDefinition = "number(10,0) default 0")
	private double all_review_score = 0;
	
	
	private String membership;
	private String membership_Type = "";
	private Timestamp membershipJoinDate;
	private Timestamp membershipEndDate;

	private String admin_Permission = "X";

	@CreationTimestamp
	private Timestamp createDate;

	
	
	public void setMembershipJoinDate() {
		membershipJoinDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public void setMembershipEndDate(int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, months);
		membershipEndDate = new Timestamp(calendar.getTimeInMillis());
	}
	
	public void renewalMembershipEndDate(int months, Timestamp membershipEndDateValue) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(membershipEndDateValue.getTime());
	    calendar.add(Calendar.MONTH, months);
	    membershipEndDate = new Timestamp(calendar.getTimeInMillis());
	}
	
	
	
	
}