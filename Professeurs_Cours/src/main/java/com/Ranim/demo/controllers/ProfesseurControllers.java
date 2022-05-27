package com.Ranim.demo.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Ranim.demo.entities.Cours;
import com.Ranim.demo.entities.Professeur;
import com.Ranim.demo.Service.ProfesseurService;
import com.Ranim.demo.Service.CoursService;



@Controller
public class ProfesseurControllers {


	@Autowired
	ProfesseurService ProfesseurService;
	
	
	@Autowired
	CoursService CoursService;
	
	

	@RequestMapping("/showCreate")
	public String showCreate(ModelMap modelMap) {
		List<Cours> cours = ProfesseurService.getAllCours();
		Professeur prof = new Professeur();
		Cours cour = new Cours();
		cour = cours.get(1); // prendre la première courégorie de la liste
		prof.setCours(cour); // affedter une courégorie par défaut au professeur pour éviter le pb avec une
								// courégorie null
		modelMap.addAttribute("professeur",prof);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("Cours", cours);
		return "formProfesseur";
	}


	// @RequestMapping("/showCreate")
		// public String showCreate() {
		// return "createProfesseur";
		// }

		// @RequestMapping("/saveProfesseur")
		// public String saveProfesseur(@ModelAttribute("professeur") Professeur professeur)
		// {
		// Professeur.saveProfesseur(professeur);
		// return "createProfesseur";
		// }

		// @RequestMapping("/saveProfesseur")
		// public String saveProfesseur(@ModelAttribute("professeur") Professeur professeur,
		// @RequestParam("date") String date,
		// ModelMap modelMap) throws ParseException {
		// conversion de la date
		// SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		// Date dateCreation = dateformat.parse(String.valueOf(date));
		// professeur.setDateCreation(dateCreation);

		// Professeur saveProfesseur = Professeur.saveProfesseur(professeur);
		// String msg = "professeur enregistré avec Id " + saveProfesseur.getIdProfesseur();
		// modelMap.addAttribute("msg", msg);
		// return "createProfesseur";
		// }

	
		@RequestMapping("/supprimerProfesseur")
		public String supprimerProfesseur(@RequestParam("id") Long id, ModelMap modelMap,
				@RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "2") int size) {
			ProfesseurService.deleteProfesseurById(id);
			Page<Professeur> profs = ProfesseurService.getAllProfesseursParPage(page, size);
			modelMap.addAttribute("Professeurs", profs);
			modelMap.addAttribute("pages", new int[profs.getTotalPages()]);
			modelMap.addAttribute("currentPage", page);
			modelMap.addAttribute("currentPage", page);
			
			return "listeProfesseurs";
		}
		
		
		

	

		
		@RequestMapping("/modifierProfesseur")
		public String editerProfesseur(@RequestParam("id") Long id,ModelMap modelMap)
		{
		Professeur p= ProfesseurService.getProfesseur(id);

	    List<Cours> cours = CoursService.findAll();
	    cours.remove(p.getCours());
	    modelMap.addAttribute("Courss", cours);
		modelMap.addAttribute("professeur", p);
		modelMap.addAttribute("mode", "edit");
		return "formProfesseur";
		}
		
		
		
		
		@RequestMapping("/updateProfesseur")
		public String updateProfesseur(@ModelAttribute("professeur") Professeur professeur,
		@RequestParam("date") String date,
		ModelMap modelMap) throws ParseException 
		{
			//conversion de la date 
			 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			 Date dateCreation = dateformat.parse(String.valueOf(date));
			 professeur.setDateCreation(dateCreation);
			 
			 ProfesseurService.updateProfesseur(professeur);
			 List<Professeur> profs = ProfesseurService.getAllProfesseurs();
			 modelMap.addAttribute("Professeurs", profs);
			return "listeProfesseurs";
			}
		
		@RequestMapping("/showCreateCours")
		public String showCreateCat(ModelMap modelMap)
		{
		modelMap.addAttribute("Courss", new Cours());
		modelMap.addAttribute("mode", "new");
		return "formCours";
		}
		
		@RequestMapping("/ListeProfesseurs")
		public String listeProfesseurs(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "4") int size) {
			Page<Professeur> profs = ProfesseurService.getAllProfesseursParPage(page, size);
			modelMap.addAttribute("professeurs", profs);
			modelMap.addAttribute("pages", new int[profs.getTotalPages()]);
			modelMap.addAttribute("currentPage", page);
			return "listeProfesseurs";
		}

		
		@RequestMapping("/ListeCours")
		public String ListeCours(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "3") int size) {
		
		Page<Cours> cours = CoursService.getAllCourssParPage(page, size);
		modelMap.addAttribute("Courss", cours);
		modelMap.addAttribute("pages", new int[cours.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "ListeCours";
		}
		
		@RequestMapping("/supprimerCours")
		public String supprimerCours(@RequestParam("id") Long id,
		 ModelMap modelMap)
		{ 
			CoursService.deleteCoursById(id);
		List<Cours> cours = CoursService.findAll();
		modelMap.addAttribute("Cours", cours);
		return "ListeCours";
		}
		
		@RequestMapping("/saveCours")
		public String saveCours(@ModelAttribute("Cours") Cours Cours,ModelMap modelMap) throws ParseException 
		{
		Cours saveCours = CoursService.saveCours(Cours);
		return "redirect:/ListeCours";
		}
		

		@RequestMapping("/saveProfesseur")
		public String saveProfesseur(@Valid Professeur professeur, BindingResult bindingResult)

		{
			if (bindingResult.hasErrors())
				return "formProfesseur";
			ProfesseurService.saveProfesseur(professeur);
			
			return "redirect:/ListeProfesseurs";
		}
		
		@RequestMapping("/modifierCours")
		public String editerCours(@RequestParam("id") Long id,ModelMap modelMap)
		{
		Cours c= CoursService.getCours(id);
		modelMap.addAttribute("Cours", c);
		modelMap.addAttribute("mode", "edit");
		return "formCours";
		}
		
		@RequestMapping("/updateCours")
		public String updateCours(@ModelAttribute("Cours") Cours Cours,ModelMap modelMap) throws ParseException {
			CoursService.updateCours(Cours);
			 List<Cours> cours = CoursService.findAll();
			 modelMap.addAttribute("Cours", cours);
			return "ListeCat";
	
	
}
		}
