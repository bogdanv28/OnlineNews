package com.bogdanvlas.newspapercms.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bogdanvlas.newspapercms.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
	
	@Override
	public List<Article> findAll();

}
