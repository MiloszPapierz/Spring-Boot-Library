package com.springBoot.Bibliotheek.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(of = {"firstname","lastname"})
@EqualsAndHashCode(of = {"firstname","lastname"})
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@Getter
	@Setter
	private String firstname;
	@Column(nullable = false)
	@Getter
	@Setter
	private String lastname;
	@ManyToMany(mappedBy="authors")
	@Getter
	@JsonIgnoreProperties("authors")
	private Set<Book> books = new HashSet<>();

	public Author(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

}
