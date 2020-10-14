package model.projetos;

import java.util.ArrayList;
import java.util.Date;

import model.autenticacao.Membro;
import model.utilitarios.Utilidade;

public class Grupo extends ProjetoComponente {

	private String nome;

	private Date dataCriacao;

	private String linkCNPq;
	/**
	 * Cole��o de Membros e Projetos
	 */
	private ArrayList<ProjetoComponente> itens = new ArrayList<ProjetoComponente>();

	public void adicionar(Membro integracao) throws Exception {
		integracao.setProjetoPai(this);
		itens.add(integracao);
	}

	public void adicionar(Projeto integracao) throws Exception {
		integracao.setProjetoPai(this);
		itens.add(integracao);
	}

	public void remover(ProjetoComponente integracao) {
		itens.remove(integracao);
		integracao.setProjetoPai(null);
	}

	@Override
	public void mover(ProjetoComponente integracao) throws Exception {
		// TODO Auto-generated method stub
		integracao.setProjetoPai(integracao);
		try {
			integracao.adicionar(this);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public float getCapitalReaiNaoGastoTotal() throws Exception {
		float aux = 0;
		for (ProjetoComponente participantes : itens) {
			aux = +participantes.getCapitalReaiNaoGastoTotal();
		}
		return aux;
	}

	@Override
	public float getCustoTotal() throws Exception {
		float aux = 0;
		for (ProjetoComponente participantes : itens) {
			aux += participantes.getCustoTotal();
		}
		return aux;
	}

	@Override
	public float getCusteioReaisNaoGastoTotal() throws Exception {
		float aux = 0;
		for (ProjetoComponente participantes : itens) {
			aux += participantes.getCusteioReaisNaoGastoTotal();
		}
		return aux;
	}

	// metodos obrigatorios
	@Override
	public void ativar() {
		// TODO Auto-generated method stub
		Utilidade.ativar(itens, this);
	}

	@Override
	public void desativar() {
		// TODO Auto-generated method stub
		Utilidade.desativar(itens, this);
	}

	// getters e setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getLinkCNPq() {
		return linkCNPq;
	}

	public void setLinkCNPq(String linkCNPq) {
		this.linkCNPq = linkCNPq;
	}

	public ArrayList<ProjetoComponente> getItens() {
		return itens;
	}

	public boolean equals(Grupo grupo) {
		if (grupo.getLinkCNPq().equals(linkCNPq)) {
			return true;
		}
		return false;
	}
}
