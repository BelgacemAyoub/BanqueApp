package com.GestionBanque.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionBanque.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, String> {

}
