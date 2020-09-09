package com.bogdanvlas.newspapercms.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bogdanvlas.newspapercms.dao.ArticleRepository;
import com.bogdanvlas.newspapercms.entities.Article;

@Controller
@RequestMapping("/admin")
public class AllArticlesController {

	@Autowired
	ArticleRepository articleRepo;
	
	@GetMapping("/all-articles")
	public String allArticles(Model model) {
		
		List<Article> articles = articleRepo.findAll();
		Collections.reverse(articles);
		
		model.addAttribute("allArticles", articles);
		
		
		return "admin/all-articles";
	}
}
