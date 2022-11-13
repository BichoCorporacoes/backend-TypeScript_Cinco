package com.atividade.tecnica.jwt.filtro;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.atividade.tecnica.dto.CredenciaisDto;
import com.atividade.tecnica.jwt.JsonWebTotenGerador;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtFiltroAutenticador extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JsonWebTotenGerador jwtTokenGerador;

	public JwtFiltroAutenticador(AuthenticationManager authenticationManager, JsonWebTotenGerador jwtTokenGerador) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenGerador = jwtTokenGerador;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			CredenciaisDto credencialDto = new ObjectMapper().readValue(request.getInputStream(),
					CredenciaisDto.class);
			UsernamePasswordAuthenticationToken tokenAuthentication = new UsernamePasswordAuthenticationToken(
					credencialDto.getEmail(), credencialDto.getPassword(), new ArrayList<>());

			Authentication authentication = authenticationManager.authenticate(tokenAuthentication);
			return authentication;

		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication resultadoAutenticacao) throws IOException, ServletException {
		UserDetails userSpring = (UserDetails) resultadoAutenticacao.getPrincipal();
		String emailUser = userSpring.getUsername();
		String jwtToken = jwtTokenGerador.createToken(emailUser);
		response.addHeader("Authorization", "Bearer " + jwtToken);
	}
}