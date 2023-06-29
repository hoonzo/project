package com.ezen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class Membership {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_seq")
    @SequenceGenerator(name = "membership_seq", sequenceName = "membership_seq", allocationSize = 1)
	private int membership_Num;
	
	private int user_id;
	private String membership_Type;
	private String paymentMethod;
	
	
}
