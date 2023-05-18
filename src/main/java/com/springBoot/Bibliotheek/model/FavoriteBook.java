package com.springBoot.Bibliotheek.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "id")
public class FavoriteBook implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@Getter
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@Getter
	@JoinColumn(name="book_id")
	private Book book;
	
	public FavoriteBook(User user,Book book) {
		this.user = user;
		this.book = book;
	}
}
