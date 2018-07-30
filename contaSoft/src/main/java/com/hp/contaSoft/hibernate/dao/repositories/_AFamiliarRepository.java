package com.hp.contaSoft.hibernate.dao.repositories;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hp.contaSoft.hibernate.entities._AFamiliar;


@Repository
public interface _AFamiliarRepository extends CrudRepository< _AFamiliar, Long>{

	@Query("select i.amount from _AFamiliar i where i.hasta > :imponible and i.desde <:imponible")
	Double getAsigFamiliarAmount(@Param("imponible") Double imponible);

}
