package com.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;

import com.hp.contaSoft.excel.entities.PayBookDetails;
import com.hp.contaSoft.hibernate.dao.service.TaxpayerService;
import com.hp.contaSoft.hibernate.entities.Address;
import com.hp.contaSoft.hibernate.entities.Alumno;
import com.hp.contaSoft.hibernate.entities.Employee;
import com.hp.contaSoft.hibernate.entities.Profesor;
import com.hp.contaSoft.hibernate.entities.Subsidiary;
import com.hp.contaSoft.hibernate.entities.Taxpayer;

public class Test3 {

	private static EntityManager manager;
	private static EntityManagerFactory emf;
	
	
	
	public static void main(String[] args) {
		emf = Persistence.createEntityManagerFactory("A");
		manager = emf.createEntityManager();
		
		initialLoad();
		
		@SuppressWarnings("unchecked")
		List<Profesor> taxpayer = (List<Profesor>) manager.createQuery("FROM Profesor").getResultList();
		System.out.println("En esta db hay:" + taxpayer.size() + " profesor");
		
		Profesor profesor = null;
		
		for(Profesor tax : taxpayer) {
			profesor = tax;
			System.out.println(tax);
		}
		
		/**
		 * second iteration
		 */
		
		List<Alumno> listaAlumnos = profesor.getAlumnos();
		Alumno a2 = listaAlumnos.get(0);
		a2.setEdad(98);
		
		manager.getTransaction().begin();
		manager.persist(a2);
		manager.getTransaction().commit();
		//manager.refresh(a2);
		
		@SuppressWarnings("unchecked")
		List<Profesor> taxpayer2 = (List<Profesor>) manager.createQuery("FROM Profesor").getResultList();
		System.out.println("En esta db hay:" + taxpayer2.size() + " profesor");
		
		for(Profesor tax : taxpayer2) {
			System.out.println(tax);
		}
		
		/**
		 * Third Iteration
		 */
		@SuppressWarnings("unchecked")
		List<Alumno> alumnos = (List<Alumno>) manager.createQuery("FROM Alumno").getResultList();
		System.out.println("En esta db hay:" + alumnos.size() + " alumnos");
		
		Alumno alum = null;
		for(Alumno tax : alumnos) {
			alum = tax;
			System.out.println(tax);
		}
		alum.setCasillero(33);
		manager.getTransaction().begin();
		manager.persist(alum);
		manager.getTransaction().commit();
		manager.refresh(alum);
		
		@SuppressWarnings("unchecked")
		List<Alumno> alumnos2 = (List<Alumno>) manager.createQuery("FROM Alumno").getResultList();
		System.out.println("En esta db hay:" + alumnos2.size() + " alumnos");
		
		
		for(Alumno tax : alumnos2) {
		
			System.out.println(tax);
		}
		
		/**Fourth Iteration
		 * 
		 */
		@SuppressWarnings("unchecked")
		List<Profesor> taxpayer3 = (List<Profesor>) manager.createQuery("FROM Profesor").getResultList();
		System.out.println("En esta db hay:" + taxpayer3.size() + " profesor");
		
		for(Profesor tax : taxpayer3) {

			System.out.println(tax);
		}
	}
	
	
	public static void initialLoad() {
				
		Alumno a1 = new Alumno(1,1,1);
		List<Alumno> alumnos = new ArrayList<>();
		alumnos.add(a1);
		Profesor p1 = new Profesor(1, "Prof NONO", alumnos);
		
		
		
		
		manager.getTransaction().begin();
		
		manager.persist(p1);
		//manager.flush();
		
		manager.getTransaction().commit();
		manager.refresh(a1);
		//manager.detach(a1);
	}
	
}
