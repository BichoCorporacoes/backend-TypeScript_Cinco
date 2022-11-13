package com.atividade.tecnica.servico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.atividade.tecnica.enumeracao.Roles;

public class ClienteDetaisImpl implements UserDetails{
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> autoridades;

	public ClienteDetaisImpl() {
	}

	public ClienteDetaisImpl(String email, String senha, List<Roles> perfil) {
		this.email = email;
		this.password = senha;
		this.gerarAutoridades(perfil);
	}
	
	private void gerarAutoridades(List<Roles> perfil) {
		List<SimpleGrantedAuthority> autoridadesPerfis = new ArrayList<>();
		for (Roles perfils : perfil) {
			autoridadesPerfis.add(new SimpleGrantedAuthority(perfils.name()));
		}
		this.autoridades = autoridadesPerfis;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.autoridades;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
