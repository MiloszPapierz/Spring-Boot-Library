package com.springBoot.Bibliotheek.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of = "isbn")
@NoArgsConstructor
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
	@NotEmpty(message = "{validation.bookname.NotEmpty}")
	private String name;
	@Getter
	@Setter
	@Column(name="img_url",columnDefinition = "TEXT")
	@JsonProperty("img_url")
	private String imgUrl;
	@ManyToMany
	@Getter
	@JoinTable(name="book_author",
	joinColumns = @JoinColumn(name="book_id"),
	inverseJoinColumns = @JoinColumn(name="author_id"))
	@JsonIgnoreProperties("books")
	private Set<Author> authors = new HashSet<>();
	@Column(nullable = false,unique = true)
	@Getter
	@Setter
	@NotEmpty
	private String isbn;
	@Column(nullable = false)
	@NumberFormat(pattern="#,##0.00")
	@DecimalMin(value = "0.01",message = "{validation.bookprice.DecimalMin}")
	@DecimalMax(value="99.99",message= "{validation.bookprice.DecimalMax}")
	@Getter
	@Setter
	private BigDecimal price;
	
	public Book(String name,String imgUrl,String isbn,BigDecimal price) {
		this.name = name;
		this.imgUrl = imgUrl;
		this.isbn = isbn;
		this.price = price;
	}
	
}
