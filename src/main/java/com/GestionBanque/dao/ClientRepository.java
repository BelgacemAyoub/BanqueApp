package com.GestionBanque.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GestionBanque.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
