package com.GestionBanque.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)  // @Inheritance parce que il y'a un héritage
@DiscriminatorColumn (name = "TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2) //la comonne nommée"Type_CPTE" contient "CC" si le compte est courant && "CE" si le compte est epargne
/* single table cad on va avoir une tables pour toutes les hiérarchies de classe
   cad une seule table dans laquelle on va stocker tous les types de comptes */
/*
 * cad on va ajouter un autre colonne dans la table Compte qui s'appelle TYPE_
 * CPTE
 */
																								
public abstract class Compte implements Serializable {
   
	@Id   
	//@GeneratedValue(strategy = GenerationType.IDENTITY): le code est string donc not auto increment
	private String codeCompte;
	private Date dateCreation;
	private double solde;
	
	//attributs d'association
	@ManyToOne()
	@JoinColumn(name = "CODE_CLIENT") // pour nommer le nom de la clé étrangére client dans la table Compte
	private Client client;  // plusieurs comptes appartient à un client 
	
	@OneToMany (mappedBy = "compte", fetch = FetchType.LAZY)
	private Collection<Operation> operations;  //un compte peut subir plusieurs opérations 

	public Compte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Compte(String codeCompte, Date dateCreation, double solde, Client client) {
		super();
		this.codeCompte = codeCompte;
		this.dateCreation = dateCreation;
		this.solde = solde;
		this.client = client;
	}

	public String getCodeCompte() {
		return codeCompte;
	}
	
	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

}
