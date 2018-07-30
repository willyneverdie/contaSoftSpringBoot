package com.hp.contaSoft.hibernate.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hp.contaSoft.hibernate.entities.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{

	
}
