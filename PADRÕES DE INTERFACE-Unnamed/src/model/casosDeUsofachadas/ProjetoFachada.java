package model.casosDeUsofachadas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import model.autenticacao.Membro;
import model.projetos.Participacao;
import model.projetos.Projeto;
import model.projetos.ProjetoComponente;
import persistenia.xml.DAOXMLProjetoParticipacao;

//caso de uso 5
public class ProjetoFachada {
	private Membro membro;
	private Participacao participacao;
	private DAOXMLProjetoParticipacao daoProjetoParticipacao = new DAOXMLProjetoParticipacao();
	private Projeto projeto;

	public ProjetoFachada(Membro membro, Participacao participacao) throws Exception {
		this.membro = membro;
		this.participacao = participacao;
		membro.adicionar(participacao);
	}

	public void criarProjeto(String nome, float aporteCusteioReais, float aporteCapitalReais,
			float gastoExecutadoCusteioReais, float gastoExecutadoCapitalReais) throws Exception {
		String[] atributo = { "nome" };
		Object[] valores = { nome };
		Set<Projeto> projetoRecuperados = daoProjetoParticipacao.consultarAnd(atributo, valores);
		if (projetoRecuperados.size() == 0) {
			projeto = new Projeto(nome, aporteCusteioReais, aporteCapitalReais, gastoExecutadoCusteioReais,
					gastoExecutadoCapitalReais);
			projeto.adicionar(membro);
			participacao.setCoordenador(true);
			membro.adicionar(participacao);
			daoProjetoParticipacao.criar(projeto);
			return;
		}
		throw new Exception("Projeto j� existente!");
	}

	public void atualizarDado(Projeto projeto, String atributoASerAtualizado, Object novoDado, Object dadoAntigo) {
		String[] atributo = { atributoASerAtualizado };
		Object[] valor = { novoDado };
		Set<Projeto> projetoRecuperados = daoProjetoParticipacao.consultarAnd(atributo, valor);

		if (!projetoRecuperados.isEmpty()) {
			Projeto auxiliar = null;
			Iterator<Projeto> it = projetoRecuperados.iterator();
			while (it.hasNext()) {
				auxiliar = (Projeto) it.next();
			}
			ArrayList<ProjetoComponente> itensProjeto = auxiliar.getItens();
			if (atributoASerAtualizado.equalsIgnoreCase("participacao")) {
				boolean atributoNovoEhParticipacao = novoDado instanceof Participacao;
				for (int i = 0; i < itensProjeto.size(); i++) {
					if (itensProjeto.get(i) instanceof Membro) {
						Membro temporario = (Membro) itensProjeto.get(i);
						if (dadoAntigo instanceof Participacao && atributoNovoEhParticipacao) {
							temporario.getParticipacoes().remove(dadoAntigo);
							Participacao p = (Participacao) dadoAntigo;
							temporario.getParticipacoes().add(p);
						}
					}
				}
			} else if (atributoASerAtualizado.equalsIgnoreCase("membro")) {
				boolean atributoNovoEhMembro = novoDado instanceof Membro;
				for (int i = 0; i < itensProjeto.size(); i++) {
					if (itensProjeto.get(i) instanceof Membro && atributoNovoEhMembro) {
						Membro temporario = (Membro) itensProjeto.get(i);
						if (temporario.equals((Membro) novoDado)) {
							itensProjeto.remove(dadoAntigo);
							itensProjeto.add(temporario);
						}
					}
				}
			} else if (atributoASerAtualizado.equalsIgnoreCase("aporteCusteioReais")) {
				auxiliar.setAporteCusteioReais((float) novoDado);
			} else if (atributoASerAtualizado.equalsIgnoreCase("gastoExecutadoCusteioReais")) {
				auxiliar.setGastoExecutadoCapitalReais((float) novoDado);
			} else if (atributoASerAtualizado.equalsIgnoreCase("gastoExecutadoCapitalReais")) {
				auxiliar.setGastoExecutadoCapitalReais((float) novoDado);
			} else if (atributoASerAtualizado.equalsIgnoreCase("aporteCusteioReais")) {
				auxiliar.setGastoExecutadoCusteioReais((float) novoDado);
			} else if (atributoASerAtualizado.equalsIgnoreCase("nome")) {
				auxiliar.setNome((String) novoDado);
			}
			daoProjetoParticipacao.atualizar(projeto, auxiliar);

		}
	}

	public void remover(Projeto preojetoParaRemover) throws Exception {
		for (ProjetoComponente participacao : membro.getParticipacoes()) {
			if (participacao instanceof Participacao) {
				if (((Participacao) participacao).isCoordenador()) {
					daoProjetoParticipacao.remover(projeto);				}
			} else {
				throw new Exception("O membro que n�o for cordenador n�o pode remover!");
			}
		}
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}
	public Membro getMembro() {
		return membro;
	}
	public void setParticipacao(Participacao participacao) {
		this.participacao = participacao;
	}
	public Projeto getProjeto() {
		return projeto;
	}
}
