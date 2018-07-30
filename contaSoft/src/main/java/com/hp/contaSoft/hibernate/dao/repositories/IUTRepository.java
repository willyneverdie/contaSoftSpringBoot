package com.hp.contaSoft.hibernate.dao.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hp.contaSoft.hibernate.entities.IUT;


@Repository
public interface IUTRepository extends CrudRepository<IUT, Long>{

	@Query("select i.factor from IUT i where i.hasta > :imponible and i.desde <:imponible")
	Double getIUT(@Param("imponible") double imponible);

}
