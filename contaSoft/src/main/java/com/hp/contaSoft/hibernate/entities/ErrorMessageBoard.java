package com.hp.contaSoft.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Entity
public class ErrorMessageBoard extends Base{

	@Column
	private String code;
	
	@Column
	private String description;
	
	
}
