package com.hp.contaSoft.hibernate.entities;

import javax.persistence.Column;

public class SalaryStatementResume extends Base {

	@Column
	private Long payBookInstanceId;
	
	@Column
	private String fileName;
	
	@Column
	private String status;
	
	
}
