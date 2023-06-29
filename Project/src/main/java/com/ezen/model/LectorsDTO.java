package com.ezen.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LectorsDTO {
	
	private int lector_num;
	private int user_id;
	private String have_lecture_room;
	private String lecture_room_address;
	private String prefer_personnel;
	private String lecture_theme;
	private String career;
	private double review_score;
	private String membership;
	private String membership_Type;
	private String admin_Permission;
	private Timestamp createDate;

}
