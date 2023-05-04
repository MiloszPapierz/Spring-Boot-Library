package com.springBoot.Bibliotheek.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(exclude = {"id","imgUrl"})
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString(exclude = "id")
public class Book implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Getter
	@Setter
	@Column(nullable = false)
	private String name;
	@Getter
	@Setter
	@Column(name="img_url",columnDefinition = "TEXT")
	private String imgUrl;
	@ManyToMany
	@Getter
	@JoinTable(name="book_author",
	joinColumns = @JoinColumn(name="book_id"),
	inverseJoinColumns = @JoinColumn(name="author_id"))
	private Set<Author> authors = new HashSet<>();
	@Column(nullable = false,unique = true)
	@Getter
	@Setter
	private String isbn;
	@Column(nullable = false)
	@NumberFormat(pattern="#,##0.00")
	@Getter
	@Setter
	private BigDecimal price;
	/*
	@OneToMany
	@Getter
	private List<Location> locations;*/
	
	public Book(String name,String imgUrl,String isbn,BigDecimal price) {
		this.name = name;
		this.imgUrl = imgUrl;
		this.isbn = isbn;
		this.price = price;
	}
}
