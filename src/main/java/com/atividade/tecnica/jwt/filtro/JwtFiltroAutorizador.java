package com.atividade.tecnica.jwt.filtro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.atividade.tecnica.jwt.JsonWebTotenGerador;

public class JwtFiltroAutorizador extends BasicAuthenticationFilter {

	private JsonWebTotenGerador tokenService;
	private UserDetailsService userDetaisService;
	
	public JwtFiltroAutorizador(AuthenticationManager gerenciadorAutenticacao, JsonWebTotenGerador tokenService, UserDetailsService userDetaisService) {
		super(gerenciadorAutenticacao);
		this.userDetaisService = userDetaisService;
		this.tokenService = tokenService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String[] partes = header.split(" ");
			String jwtToken = partes[1];
			UsernamePasswordAuthenticationToken tokenAutenticacao = getAuthentication(jwtToken);
			if (tokenAutenticacao != null) {
				SecurityContextHolder.getContext().setAuthentication(tokenAutenticacao);
			}
		}
		filterChain.doFilter(request, response);
		
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {
		if (tokenService.validateToken(jwtToken)) {
			String nomeUsuario = tokenService.getUsername(jwtToken);
			UserDetails usuarioSpring = userDetaisService.loadUserByUsername(nomeUsuario);
			return new UsernamePasswordAuthenticationToken(usuarioSpring, usuarioSpring.getPassword(), usuarioSpring.getAuthorities());
		}
		return null;
	}
}