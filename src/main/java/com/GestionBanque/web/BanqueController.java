package com.GestionBanque.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.GestionBanque.entities.Compte;
import com.GestionBanque.entities.Operation;
import com.GestionBanque.service.IBanqueService;

@Controller
public class BanqueController {  //ceci est un controlleur Spring MVC 
	
// la couche web a besoin de la couche metier 
	@Autowired
	private IBanqueService banqueService;
		
	@GetMapping ("/operation")  //càd , quand je tape : localhost/operations j'aurai une vue qui s'appelle comptes.html
	public String index () {    //cette methode retourne une vue tous simplement

		//banqueService.consulterCompte();
		return "comptes";       //càd : le nom de la vue est : comptes.html
		
	}
	
	
	@GetMapping ("/consultercompte")  //càd , quand je tape : localhost/consulterCompte j'aurai une vue qui s'appelle comptes.html
	public String consulter(Model model, String codeCompte,
			@RequestParam (name = "page", defaultValue = "0") int p,
			@RequestParam (name = "size", defaultValue = "5") int s)
			{
		model.addAttribute("codeCompte", codeCompte);
		try {
			Compte cp = banqueService.consulterCompte(codeCompte);
			System.out.println("=================================");
			System.out.println(cp.getCodeCompte());
			System.out.println(cp.getSolde());
			Page<Operation> pageOperation = banqueService.listOperation(codeCompte, p, s);
			model.addAttribute("listOperations", pageOperation.getContent()); //getContent() permet de retourner la liste des opérations
			int [] pages = new int [pageOperation.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("consulterCpte", cp);	
		} catch (Exception e) {
			model.addAttribute("exceptions", e);     // si le commpte n'existe pas 
		}
			
		return "comptes";   //càd : le nom de la vue est : comptes.html
	
	}
	
	@PostMapping ("/saveOperation")
	public String saveOperation (Model model, String typeOperation, String codeCompte, double montant, String codeCompte2) {  //cette methode retourne une vue tous simplement
		try {
			if (typeOperation.equals("VERS")) 
				banqueService.verser(codeCompte, montant);
			else if (typeOperation.equals("RETR")) {
				banqueService.retirer(codeCompte, montant);
			}
			if (typeOperation.equals("VIR")) {
				banqueService.virement(codeCompte, codeCompte2, montant);
			}
		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consultercompte?codeCompte="+codeCompte+"&errorr ="+e.getMessage();
		}

		return "redirect:/consultercompte?codeCompte="+codeCompte;
	}
	
	
	

}
