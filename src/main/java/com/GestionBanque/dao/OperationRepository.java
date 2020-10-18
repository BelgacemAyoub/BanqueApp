package com.GestionBanque.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.GestionBanque.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
	
	@Query ("select o from Operation o where o.compte.codeCompte=:x order by o.dateOperation desc")  // HQL: langage de hibernate
	public Page<Operation> listOperation(@Param("x") String codeCompte, Pageable pageable); 

}
