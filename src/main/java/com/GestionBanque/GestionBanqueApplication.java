package com.GestionBanque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.GestionBanque.dao.ClientRepository;
import com.GestionBanque.dao.CompteRepository;
import com.GestionBanque.dao.OperationRepository;
import com.GestionBanque.service.IBanqueService;


/**
 * Spring Boot IOC qui va démarer, il va lire le fichier application.properties, ils va lire les dépendances, il va configurer
 * JPA, il va configurer Hibernate, il va scanner les annotations et aprés il va générer les tables.     
 * avec spring data on peut créer les interfaces JPARepository pour gérer les entités 
 *
 */

/**
 *  ça c'est pratiquement la test de partie de la couche DAO 
 *
 */
@SpringBootApplication
public class GestionBanqueApplication implements CommandLineRunner{
	
	
	@Autowired       // on utilise l'annotation @Autowired au lieu d'utiliser ApplicationContext.getBean(ClientRepository.class)  
	private ClientRepository clientRepository;  // déclarer un objet de type ClientRepository et le injecter avec l'annotation @Autowired 
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;   
	@Autowired
	private IBanqueService banqueMetier;


	public static void main(String[] args) {

	SpringApplication.run(GestionBanqueApplication.class, args);
	
		
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*  
		Client c1 = clientRepository.save(new Client("ayoub", "belgacem.ayoub8590@gmail.com"));
		Client c2 = clientRepository.save(new Client("ahmed", "belgacem.ahmed@gmail.com"));
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%");
		Client c = new Client();
		Long a = c.getCode();
		System.out.println(a);

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println(c1.getEmail());
		if (c1 != null) 
			System.out.println("************");
		
		Compte cp1 = compteRepository.save(new CompteCourant("c3", new Date(), 200000, c1, 6000));
		Compte cp2 = compteRepository.save(new CompteEpargne("c4", new Date(), 600000, c2, 5000));
		

		if (cp1 != null) 
			System.out.println("************");
		
		operationRepository.save(new Retrait(new Date(), 20000, cp1));
		operationRepository.save(new Versement(new Date(), 700000, cp2));
		operationRepository.save(new Versement(new Date(), 200000, cp1));
		operationRepository.save(new Retrait(new Date(), 100000, cp2));
		operationRepository.save(new Retrait(new Date(), 350000, cp2));
		operationRepository.save(new Versement(new Date(), 300000, cp1));
		operationRepository.save(new Versement(new Date(), 50000, cp1));
		/*
		/*
		 * if (operationRepository != null) System.out.println("************");
		 * 
		 * banqueMetier.verser("c1", 100000000);
		 */
		
	}

}


