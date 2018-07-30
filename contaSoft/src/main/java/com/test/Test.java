package com.test;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.contaSoft.hibernate.dao.repositories.TaxpayerRepository;
import com.hp.contaSoft.hibernate.dao.service.TaxpayerService;
import com.hp.contaSoft.hibernate.entities.Address;
import com.hp.contaSoft.hibernate.entities.Employee;
import com.hp.contaSoft.hibernate.entities.Subsidiary;
import com.hp.contaSoft.hibernate.entities.Taxpayer;


public class Test {
	
	
	private static EntityManager manager;
	private static EntityManagerFactory emf;
	@Autowired
	private static TaxpayerService taxpayerService;
	
	public static void main(String[] args) {
		try{
			emf = Persistence.createEntityManagerFactory("A");
			manager = emf.createEntityManager();

			
			initialLoad();
			

			
			List<Taxpayer> taxpayer = (List<Taxpayer>) manager.createQuery("FROM Taxpayer").getResultList();
				System.out.println("En esta db hay:" + taxpayer.size() + " taxpayers");
			
			for(Taxpayer payer : taxpayer)
			{
				System.out.println(payer.toString());
				System.out.println(payer.getAddress().toString());
				//System.out.println(payer.getSubsidiary().toString());
			}
			
			
			List<Employee> employee = (List<Employee>) manager.createQuery("FROM Employee").getResultList();
			for(Employee emp : employee)
			{
				System.out.println(emp.toString());
				
			}
			
			manager.clear();
			
		}catch(Exception e) {
			throw e;
		}
	
	}
	
	
	@SuppressWarnings("null")
	public static void initialLoad() {
		
		
		Address address = new Address("Tu Casa");
		
		Taxpayer tp = new Taxpayer("Williams SA","15961703-3", new Address("Mi Casa"), new Subsidiary("Oficina I"));
		Taxpayer tp2 = new Taxpayer("Marco SA","15961703-3",address, new Subsidiary("Oficina I"));
		
		Employee emp = new Employee("Williams","Herrera","15961703-3", new Address("Pasaje Uno Poniente","1190"));
		//Employee emp2 = new Employee("Marco","Herrera","13961703-3");
		//Employee emp3 = new Employee("Cathy","Herrera","12961703-3");
		
		
		
		manager.getTransaction().begin();
		manager.persist(tp);
		manager.persist(tp2);
		manager.persist(emp);
		//manager.persist(emp2);
		//manager.persist(emp3);
		manager.getTransaction().commit();
		
	}
}
