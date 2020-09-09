package com.bogdanvlas.newspapercms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private String category;
	
	private String slug;
	
	private String image;
		

	public Article() {
		
	}

	public Article(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	
	

	public Article(int id, String title, String content, String image) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;		
		this.image = image;
	}

	public Article(String title, String content, String image) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content=" + content + ", category=" + category + ", image="
				+ image + "]";
	}
		

}
