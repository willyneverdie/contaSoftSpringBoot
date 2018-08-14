package com.hp.contaSoft.rest.api.payroll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.contaSoft.hibernate.dao.repositories.PayBookInstanceRepository;
import com.hp.contaSoft.hibernate.dao.repositories.TaxpayerRepository;
import com.hp.contaSoft.hibernate.dao.repositories.TemplateDetailsRepository;
import com.hp.contaSoft.hibernate.entities.PayBookInstance;
import com.hp.contaSoft.hibernate.entities.Taxpayer;
import com.hp.contaSoft.hibernate.entities.TemplateDetails;
import com.hp.contaSoft.rest.api.payroll.error.ClientErrorMessage;
import com.hp.contaSoft.rest.api.payroll.exception.ClientNotFoundException;

@RestController
@RequestMapping("/api")
public class APIRestController {

	@Autowired
	TaxpayerRepository taxpayerRepository; 
	
	@Autowired
	PayBookInstanceRepository payBookInstanceRepository;
	
	@Autowired
	TemplateDetailsRepository templateDetailsRepository;
	
	//get clients
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/clients")
	public List<Taxpayer> getClients() {
		List<Taxpayer> clients = (List<Taxpayer>) taxpayerRepository.findAll();
		return clients;
		//return null;
	}
	
	@GetMapping("/clients/{clientId}")
	public Optional<Taxpayer> getClient(@PathVariable Long clientId) {

		Optional<Taxpayer> client = taxpayerRepository.findById(clientId);
		
		if ( !client.isPresent()) {
			throw new ClientNotFoundException("Client not found");
		}
			
		return client;
	}
	
	
	
	@GetMapping("/paybookinstance/{clientRut}")
	public List<PayBookInstance>getPaybookinstance(@PathVariable String clientRut) {
		
		List<PayBookInstance> payBookInstanceList = (List<PayBookInstance>)payBookInstanceRepository.findAllByRut(clientRut);
		
		return payBookInstanceList;
	}
	
	@GetMapping("/templates")
	public Iterable<TemplateDetails> getTemplatesDetail() {
		
		Iterable<TemplateDetails> templates = templateDetailsRepository.findAll();
		return templates;
	}
}
