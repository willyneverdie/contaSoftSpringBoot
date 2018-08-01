package com.hp.contaSoft.hibernate.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hp.contaSoft.excel.entities.PayBookDetails;
import com.hp.contaSoft.hibernate.dao.projection.IdProjection;
import com.hp.contaSoft.hibernate.dao.projection.PrevisionProjection;


@Repository
public interface PayBookDetailsRepository extends CrudRepository<PayBookDetails, Long>{

	@Query("select p.prevision as prevision, sum(p.valorPrevision) as suma from PayBookDetails p where pay_book_instance =:id group by prevision")
	List<PrevisionProjection> getReportByPrevision(@Param("id") Long id);

	
	@Query("select p.id as id from PayBookDetails p")
	List<IdProjection> getAll();
}
