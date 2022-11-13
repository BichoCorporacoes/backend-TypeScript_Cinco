package com.atividade.tecnica.servico;

import java.util.List;

public interface Selecionador<T,ID> {
	public T selecionar(List<T> objetos, ID identificador);
}