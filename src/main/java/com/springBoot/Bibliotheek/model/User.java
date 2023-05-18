package com.springBoot.Bibliotheek.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(of = "username")
@Entity
@Table(name="users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;
	@NotNull
	@Column(unique = true,length = 50,nullable = false)
	@Getter
	@Setter
	private String username;
	@Column(length= 120,nullable = false)
	@Getter
	@Setter
	private String password;
	@Getter
	@Column(nullable = false)
	private boolean enabled = true;
	@Getter
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="user_role",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;
	@Getter
	private int maxFavorites;
	
	public User(String username,String password,Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.maxFavorites = new Random().nextInt(5)+1;
	}
	
	//constructor for development to add maxFavorites in seeds
	public User(String username,String password,Set<Role> roles,int favorites) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.maxFavorites = favorites;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
}
