package com.bogdanvlas.newspapercms.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bogdanvlas.newspapercms.dao.ArticleRepository;
import com.bogdanvlas.newspapercms.entities.Article;

@Controller
public class HomeController {
	
	@Autowired
	ArticleRepository articleRepo;
	
	@GetMapping("/")
	public String homePageDisplay(Model model) {
		
		List<Article> articles = articleRepo.findAll();
		List<Article> politics = new ArrayList<>();
		List<Article> general = new ArrayList<>();
		List<Article> finance = new ArrayList<>();
		List<Article> sports = new ArrayList<>();
	
		Collections.reverse(articles);//adding last 3 articles from general categ on first page
		int countGen=0;
		for (Article a :  articles) {
			if (a.getCategory().equals("general")) {
				countGen++;
				if (a.getContent().length() < 350) {
					general.add(new Article(a.getId(), a.getTitle(), a.getContent(), a.getImage()));
				} else {
				general.add(new Article(a.getId(), a.getTitle(), a.getContent().substring(0,350) + "...",  a.getImage()));
			}
				if (countGen==3) {
					break;
				}
			}
		}
		
		int countPol = 0;
		for (Article a :  articles) {
			if (a.getCategory().equals("politics")) {
				countPol++;
				if (a.getContent().length() < 350) {
					politics.add(new Article(a.getId(), a.getTitle(), a.getContent(), a.getImage()));
				} else {
					politics.add(new Article(a.getId(), a.getTitle(), a.getContent().substring(0,350) + "...",  a.getImage()));	
				}
				if(countPol==3) {
					break;
				}
			}
		}
		
		int countFin = 0;
		for (Article a :  articles) {
			if (a.getCategory().equals("finance")) {
				countFin++;
				if (a.getContent().length() < 350) {
					finance.add(new Article(a.getId(), a.getTitle(), a.getContent(), a.getImage()));
				} else {
					finance.add(new Article(a.getId(), a.getTitle(), a.getContent().substring(0,350) + "...",  a.getImage()));
			}
				if(countFin==3) {
					break;
				}
			}
		}
		
		int countSport = 0;
		for (Article a :  articles) {
			if (a.getCategory().equals("sports")) {
				countSport++;
				if (a.getContent().length() < 350) {
					sports.add(new Article(a.getId(), a.getTitle(), a.getContent(), a.getImage()));
				} else {
					sports.add(new Article(a.getId(), a.getTitle(), a.getContent().substring(0,350) + "...",  a.getImage()));
			}
				if(countSport==3) {
					break;
				}
		 }
		}
		
		
		model.addAttribute("articlesListGeneral", general);
		model.addAttribute("articlesListPolitics", politics);
		model.addAttribute("articlesListFinance", finance);
		model.addAttribute("articlesListSports", sports);
		
		
		return "home";
	}
	
	
	@GetMapping("/{id}")
	public String getArticle(@PathVariable int id, Model model) {
		
        Optional<Article> aArticle = articleRepo.findById(id);
		
		model.addAttribute("aArticle", aArticle);
		
		return "fragments/news-template.html";
	}

}
