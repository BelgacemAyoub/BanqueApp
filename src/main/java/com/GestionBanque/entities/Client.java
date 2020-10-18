package com.GestionBanque.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity; //importer pour la persistance (pour pouvoir utiliser les annotations des entity)
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;  //pou @Id
import javax.persistence.OneToMany;

//les annotations JPA sont : @Entity, @Table, @Id, @GeneratedValue, @Inheritance, @ManyToOne, @ManyToMany, ...

@Entity
public class Client implements Serializable {
	
	@Id  @GeneratedValue    // id pour spécifier l'identifiant ; Generated value pour générer automatiquement l'identifiant
	private Long code; 	   //c'est l'identifiant de la table et qui est générer automatiquement
	private String nom;
	private String email;
	
	@OneToMany (mappedBy = "client", fetch = FetchType.LAZY)   // association biderctionnelle , Lazy pour gérer les associations 
	private Collection<Compte> comptes;						   // entre les classes 
														   
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Client(String nom, String email) {
		super();
		this.nom = nom;
		this.email = email;
	}


	public Long getCode() {
		return code;
	}


	public void setCode(Long code) {
		this.code = code;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Collection<Compte> getComptes() {
		return comptes;
	}


	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	} 
	
	
	
	
	
	

}
