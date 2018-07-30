package com.hp.contaSoft.hibernate.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hp.contaSoft.hibernate.entities.PayBookInstance;

@Repository
public interface PayBookInstanceRepository extends CrudRepository<PayBookInstance, Long>{

	//@Query("SELECT version FROM PayBookInstance p WHERE p.rut = :rut")
	//public int findVersion(@Param("rut") String rut);
	
	@Query("select p from PayBookInstance p where p.rut =:rut and p.month=:month ORDER BY id desc")
	List<PayBookInstance> getVersionByRutAndMonth(@Param("rut") String rut,@Param("month") String month);
	
	public List<PayBookInstance> findAllByTaxpayerId(Long id);
	
	public List<PayBookInstance> findAllByTaxpayerIdOrderByVersionDesc(Long id);
	
	
	public List<PayBookInstance> findAllByRut(String rut);
	
}
