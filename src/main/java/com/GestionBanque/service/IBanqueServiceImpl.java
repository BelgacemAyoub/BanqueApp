package com.GestionBanque.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.GestionBanque.dao.CompteRepository;
import com.GestionBanque.dao.OperationRepository;
import com.GestionBanque.entities.Compte;
import com.GestionBanque.entities.CompteCourant;
import com.GestionBanque.entities.Operation;
import com.GestionBanque.entities.Retrait;
import com.GestionBanque.entities.Versement;

@Service     	  // l'annotation @Service utilisé pour les objet de la couche métier 
@Transactional    // j'ai garantie que tous les opération eli fi wost les méthodes sont transactionnal, donc soit     
				  // que toutes les opérations s'exécute correctement, soit j'annule tous.
public class IBanqueServiceImpl implements IBanqueService { //puis , on va faire le couplage faible avec la couche dao --> la couche metier va faire appel � la couche dao
	  // pour faire l'injection de dependance  --> on va demander a spring d'injecter une implementation de cette interface

    @Autowired   // a importer : import org.springframework.beans.factory.annotation.Autowired;
	private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;
    
    
	@Override
	public Compte consulterCompte(String codeCompte) {
		Compte cp = compteRepository.findById(codeCompte).orElseThrow(() -> new RuntimeException("Compte Introuvable"));
		return cp ;
	
	}

	@Override
	public void verser(String codeCompte, double montant) {    
		 Compte c = consulterCompte(codeCompte);
		 Versement v = new Versement(new Date(), montant, c);  // le versement est une operation
		 operationRepository.save(v);  // ici, la methode save() permet l'enregistrement
		 //mettre a jour le solde du compte
		 c.setSolde(c.getSolde() + montant); 
		 compteRepository.save(c);  // ici, la methode save permet de mettre a jours le compte (update)  ---->Meme dans la console, 
		 //on aura comme requette : Hibernate: update compte set code_cli=?, date_creation=?, solde=?, decouvert=? where code_compte=?
		
	}

	@Override
	public void retirer(String codeCompte, double montant) {
		
		 Compte c = consulterCompte(codeCompte);
		 double facilitesCaisse = 0;
		 if (c instanceof CompteCourant)  {
			facilitesCaisse = ((CompteCourant) c).getDecouvert();
		 if (c.getSolde()+facilitesCaisse < montant)  throw new RuntimeException("Solde insufisant");
		 }
		 Retrait r = new Retrait(new Date(), montant, c);  // le retrait est une operation
		 operationRepository.save(r);  // ici, la methode save() permet l'enregistrement
		//mettre a jour le solde du compte
		 c.setSolde(c.getSolde() - montant);
		 compteRepository.save(c);  // ici, la methode save permet de mettre a jours le compte (update)
		
	}

	@Override
	public void virement(String codeCompte1, String codeCompte2, double montant) {
		if(codeCompte1.equals(codeCompte2)) throw new RuntimeException("Impossible : On ne peut pas effectuer un virement sur le meme compte");
		 retirer(codeCompte1, montant);
		 verser(codeCompte2, montant);
		
	}

	@Override
	public Page<Operation> listOperation(String codeCompte, int page, int size) {
		
		 
		return operationRepository.listOperation(codeCompte, PageRequest.of(page, size) );
	}
	

	

}
