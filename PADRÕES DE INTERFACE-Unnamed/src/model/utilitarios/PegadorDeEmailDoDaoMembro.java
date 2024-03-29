package model.utilitarios;

import java.util.ArrayList;
import model.autenticacao.Membro;
import model.projetos.Participacao;
import model.projetos.ProjetoComponente;

/**
 * Classe responsavel por recuperar as participa��es de um membro
 * 
 * @author Ant�nio Amorim
 *
 */
public class PegadorDeEmailDoDaoMembro {

	public static ArrayList<Participacao> recuperarParticipacao(Membro membro) throws Exception {
		ArrayList<Participacao> participacoes = new ArrayList<Participacao>();
		for (ProjetoComponente participacaoFor : membro.getParticipacoes()) {
			Participacao participacao = (Participacao) participacaoFor;
			participacoes.add(participacao);
		}
		if (participacoes.size()!=0) {
			return participacoes;
		}
		throw new Exception("Este membro n�o tem pontos trabalhados cadastrao!");

	}
}
