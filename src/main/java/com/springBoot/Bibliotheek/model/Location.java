package com.springBoot.Bibliotheek.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","book"})
@ToString(exclude =  "id")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;
	@Getter
	@Setter
	@Column(nullable = false,name = "placecode_1")
	private int placecode1;
	@Getter
	@Setter
	@Column(nullable = false,name = "placecode_2")
	private int placecode2;
	@Getter
	@Setter
	@Column(nullable= false)
	private String placename;
	@ManyToOne
	@Getter
	@Setter
	private Book book;
	
	public Location(int placecode1, int placecode2,String placename,Book book) {
		this.placecode1 = placecode1;
		this.placecode2 = placecode2;
		this.placename = placename;
		this.book = book;
	}
}
