package com.hp.contaSoft.hibernate.dao.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.hp.contaSoft.hibernate.entities.AFPFactors;
import com.hp.contaSoft.hibernate.entities.Taxpayer;
import com.hp.contaSoft.initial.PostConstructBean;

public class AFPFactorsRepositoryImpl  {

/*	private EntityManager manager;
	
	public AFPFactorsRepositoryImpl() {
		manager = PostConstructBean.manager;
	}
	
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(AFPFactors arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends AFPFactors> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existsById(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<AFPFactors> findAll() {
		List<AFPFactors> taxpayer = (List<AFPFactors>) manager.createQuery("FROM AFPFactors").getResultList();
		return taxpayer;
	}

	@Override
	public Iterable<AFPFactors> findAllById(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AFPFactors> findById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends AFPFactors> S save(S arg0) {
		manager.getTransaction().begin();
		manager.persist(arg0);
		manager.getTransaction().commit();
		return null;
	}

	@Override
	public <S extends AFPFactors> Iterable<S> saveAll(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AFPFactors findByName(String prevision) {
		
		manager.getTransaction().begin();
		Query query= manager.
		        createQuery("from AFPFactors where name=:name");
		query.setParameter("name", prevision);
		AFPFactors afp = (AFPFactors) query.getSingleResult();
		manager.getTransaction().commit();
		return afp;
	}
*/
}
