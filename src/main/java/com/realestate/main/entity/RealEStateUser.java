package com.realestate.main.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class RealEStateUser implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true)
	private String email;
	private String password;
	
	@Column(unique = true)
	private String phoneNumber;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="User_Roles", joinColumns = {@JoinColumn(name="User_Id")},inverseJoinColumns = {@JoinColumn(name="Role_Name")})
	private Set<Role> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> authorities=new HashSet<Authority>();
		this.roles.forEach(userRole->{
			authorities.add(new Authority("ROLE_"+userRole.getRoleName()));
		});
		return authorities;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
}
