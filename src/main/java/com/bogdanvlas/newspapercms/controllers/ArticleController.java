package com.bogdanvlas.newspapercms.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bogdanvlas.newspapercms.dao.ArticleRepository;
import com.bogdanvlas.newspapercms.entities.Article;

@Controller
@RequestMapping("/admin/articles")
public class ArticleController {
	
	@Autowired
	ArticleRepository articleRepo;

	@GetMapping("/new")
	public String displayNewArticleForm(Model model) {
		Article aArticle = new Article();
		model.addAttribute("article", aArticle);
		return "admin/new-article";
	}
	
	@PostMapping("/save")
	public String addArticle(Article article, Model model, MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		
		boolean fileOk = false;	
		byte[] bytes = file.getBytes();
		String filename = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + filename);
		
		if (filename.toLowerCase().endsWith("jpg") || filename.toLowerCase().endsWith("jpeg") || filename.toLowerCase().endsWith("png")) {
			fileOk = true;
		}
		
		if (fileOk==false) {
			redirectAttributes.addFlashAttribute("message" , "Invalid Image format! Choose a jpg or png image!");
			redirectAttributes.addFlashAttribute("alertClass" , "alert-danger");
		}
		
		article.setImage(filename);
		if (article.getTitle().length()<26) {
			article.setSlug(article.getTitle().replaceAll("\\s+",""));
		} else {
			article.setSlug(article.getTitle().replaceAll("\\s+","").substring(0,26));
		}
		
		
		articleRepo.save(article);
		
		Files.write(path, bytes);
		
		return "redirect:/admin/all-articles";
	}
	
	@GetMapping("/read/{id}")
	public String getArticle(@PathVariable int id, Model model) {
		
        Optional<Article> aArticle = articleRepo.findById(id);
        
        Article a1= aArticle.get();
        
        if (!aArticle.isPresent()) {
     			return "redirect:/";
     		}
		
		model.addAttribute("aArticle", a1);
		
		return "admin/news-template.html";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable int id, Model model) {
		
		Optional<Article> article = articleRepo.findById(id);
		
		model.addAttribute("article", article);
		
		return "admin/edit-article";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteArticle(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
		
		articleRepo.deleteById(id);
		
		return "redirect:/admin/all-articles";
	}
	
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
		
		
		return "admin/home";
	}
}
