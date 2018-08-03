package com.hp.contaSoft.rest.api.payroll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	@GetMapping("/clients")
	public List<Taxpayer> getClients() {
		List<Taxpayer> clients = (List<Taxpayer>) taxpayerRepository.findAll();
		return clients;
		//return null;
	}
	
	@GetMapping("/clients/{clientId}")
	public Optional<Taxpayer> getClient(@PathVariable Long clientId) {
		
		
		//Taxpayer client = taxpayerRepository.findByIdNew(clientId);
		Optional<Taxpayer> client = taxpayerRepository.findById(clientId);
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
