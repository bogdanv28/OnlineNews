package com.bogdanvlas.newspapercms.controllers.categories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bogdanvlas.newspapercms.dao.ArticleRepository;
import com.bogdanvlas.newspapercms.entities.Article;

@Controller
public class FinanceNewsController {

	@Autowired
	ArticleRepository articleRepo;
	
	@GetMapping("/categories/all-finance-news")
	public String displayFinanceNews(Model model) {
		List<Article> articles = articleRepo.findAll();
		List<Article> finance = new ArrayList<>();
		
		Collections.reverse(articles);
		
		for (Article a :  articles) {
			if (a.getCategory().equals("finance")) {
				if (a.getContent().length() < 350) {
					finance.add(new Article(a.getTitle(), a.getContent(), a.getImage()));
				} else {
				finance.add(new Article(a.getTitle(), a.getContent().substring(0,350) + "...", a.getImage()));
			}
			}
		}
		
		model.addAttribute("articlesListFinance", finance);		
		
		return "/categories/all-finance-news";
	}
}
