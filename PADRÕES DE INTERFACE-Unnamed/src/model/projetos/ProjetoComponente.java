package model.projetos;

import java.io.Serializable;

import model.autenticacao.Membro;
/**
 * classe principal do composite =, interfaxce emcomum dos itens
 * @author Ant�nio Amorim
 *
 */
public abstract class ProjetoComponente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean ativo = true;

	private ProjetoComponente projetoPai;

	public ProjetoComponente getProjetoPai() {
		return projetoPai;
	}

	public void setProjetoPai(ProjetoComponente projetoPai) {
		this.projetoPai = projetoPai;
	}
	
	public void remover(ProjetoComponente integracao) throws Exception{
		throw new Exception("Este objeto n�o se move!");
	}
	
	//Varios metodos de adcionar para evitar o uso de instanceof, por isso sobrecarregar os metodos 
	public void adicionar(Membro integracao) throws Exception {
		throw new Exception("Membro n�o pode ser adcionado aqui!");
	}
	
	public void adicionar(Projeto integracao) throws Exception {
		throw new Exception("Projeto n�o pode ser adcionado aqui!");
	}
	
	public void adicionar(Participacao integracao) throws Exception {
		throw new Exception("Participa��o n�o pode ser adcionado aqui!");
	}
	
	public void adicionar(Edital integracao) throws Exception {
		throw new Exception("Edital n�o pode ser adcionado aqui!");
	}
	// metodos que precis�o ser abstratatos pois estar�o em todas as classes
	public abstract void ativar();
	
	public abstract void desativar();
	
	public abstract float getCustoTotal() throws Exception;
	
	public abstract float getCusteioReaisNaoGastoTotal() throws Exception;


	public void adicionar(Grupo integracao) throws Exception {
		throw new Exception("Grupo n�o pode ser adcionado aqui!");
	}


	public abstract void mover(ProjetoComponente integracao) throws Exception;

	public abstract float getCapitalReaiNaoGastoTotal() throws Exception;

	// metodos que precis�o ser n�o abstratos pois s� estar�o em algumas as classes

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
