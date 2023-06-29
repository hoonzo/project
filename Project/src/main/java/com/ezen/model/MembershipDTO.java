package com.ezen.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MembershipDTO {
	
	private int membership_Num;
	
	private int user_id;
	private String membership_Type;
	private String paymentMethod;

}
