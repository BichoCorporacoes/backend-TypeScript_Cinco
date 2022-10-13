package com.atividade.tecnica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.atividade.tecnica.entidades.Acomodacao;
import com.atividade.tecnica.enumeracao.TipoAcomodacao;
import com.atividade.tecnica.repositorios.RepositorioAcomodacao;

@SpringBootApplication
public class TecnicaApplication implements CommandLineRunner{

	@Autowired
	private RepositorioAcomodacao repositorioAcomodacao;
	
	public static void main(String[] args) {
		SpringApplication.run(TecnicaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Acomodacao acomodacaoCasalSimples = new Acomodacao();
		acomodacaoCasalSimples.setNomeAcomadacao(TipoAcomodacao.CasalSimples);
		acomodacaoCasalSimples.setCamaSolteiro(0);
		acomodacaoCasalSimples.setCamaCasal(1);
		acomodacaoCasalSimples.setSuite(1);
		acomodacaoCasalSimples.setClimatizacao(true);
		acomodacaoCasalSimples.setGaragem(1);
		repositorioAcomodacao.save(acomodacaoCasalSimples);
		
		Acomodacao acomodacaoFamiliaSimples = new Acomodacao();
		acomodacaoFamiliaSimples.setNomeAcomadacao(TipoAcomodacao.FamiliaSimples);
		acomodacaoFamiliaSimples.setCamaSolteiro(2);
		acomodacaoFamiliaSimples.setCamaCasal(1);
		acomodacaoFamiliaSimples.setSuite(1);
		acomodacaoFamiliaSimples.setClimatizacao(true);
		acomodacaoFamiliaSimples.setGaragem(1);
		repositorioAcomodacao.save(acomodacaoFamiliaSimples);
		
		Acomodacao acomodacaoFamiliaMais = new Acomodacao();
		acomodacaoFamiliaMais.setNomeAcomadacao(TipoAcomodacao.FamiliaMais);
		acomodacaoFamiliaMais.setCamaSolteiro(5);
		acomodacaoFamiliaMais.setCamaCasal(1);
		acomodacaoFamiliaMais.setSuite(2);
		acomodacaoFamiliaMais.setClimatizacao(true);
		acomodacaoFamiliaMais.setGaragem(2);
		repositorioAcomodacao.save(acomodacaoFamiliaMais);
		
		Acomodacao acomodacaoFamiliaSuper = new Acomodacao();
		acomodacaoFamiliaSuper.setNomeAcomadacao(TipoAcomodacao.FamiliaSuper);
		acomodacaoFamiliaSuper.setCamaSolteiro(6);
		acomodacaoFamiliaSuper.setCamaCasal(2);
		acomodacaoFamiliaSuper.setSuite(3);
		acomodacaoFamiliaSuper.setClimatizacao(true);
		acomodacaoFamiliaSuper.setGaragem(2);
		repositorioAcomodacao.save(acomodacaoFamiliaSuper);
		
		Acomodacao acomodacaoSolteiroSimples= new Acomodacao();
		acomodacaoSolteiroSimples.setNomeAcomadacao(TipoAcomodacao.SolteiroSimples);
		acomodacaoSolteiroSimples.setCamaSolteiro(1);
		acomodacaoSolteiroSimples.setCamaCasal(0);
		acomodacaoSolteiroSimples.setSuite(1);
		acomodacaoSolteiroSimples.setClimatizacao(true);
		acomodacaoSolteiroSimples.setGaragem(0);		
		repositorioAcomodacao.save(acomodacaoSolteiroSimples);
		
		Acomodacao acomodacaoSolteiroMais= new Acomodacao();
		acomodacaoSolteiroMais.setNomeAcomadacao(TipoAcomodacao.SolteiroMais);
		acomodacaoSolteiroMais.setCamaSolteiro(0);
		acomodacaoSolteiroMais.setCamaCasal(1);
		acomodacaoSolteiroMais.setSuite(1);
		acomodacaoSolteiroMais.setClimatizacao(true);
		acomodacaoSolteiroMais.setGaragem(1);		
		repositorioAcomodacao.save(acomodacaoSolteiroMais);
		
	}
}
