package com.hp.contaSoft.rest.api.payroll.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientErrorMessage {
	
	private int status;
	private String message;
	private long timeStamp;
	
	public ClientErrorMessage() {
	
	}

	public ClientErrorMessage(int status, String message, long timeStamp) {
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}
	
	
	
}
