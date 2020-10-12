package model.casosDeUsofachadas;

import model.autenticacao.Membro;
import persistenia.xml.DAOXMLMembroConta;

//caso de uso 1
public class CasoDeUsoUmCadastroFachada {

	private DAOXMLMembroConta daoMembro = new DAOXMLMembroConta();

	public boolean cadastrarMembro(String nome, long matricula, String email, String senha, String senhaEmail) {
		Membro membro = new Membro(matricula, nome, email, senha, senhaEmail);

		

		membro.setAtivo(true);
		if (daoMembro.isVazia()) {
			membro.setAdministrador(true);
		} else {
			membro.setAdministrador(false);
		}
		return daoMembro.criar(membro);
	}

	public void atualizarMembro(Membro membro, String[] atributosQueroAtualizar, Object[] valores) throws Exception {
		boolean atualizado = false;
		Membro membroAtual = membro;
		for (int i = 0; i < atributosQueroAtualizar.length; i++) {
			if (atributosQueroAtualizar[i].equals("nome")) {
				membroAtual.setNome((String) valores[i]);
				atualizado = true;
			}
			if (atributosQueroAtualizar[i].equals("ativo")) {
				membroAtual.setAtivo((boolean) valores[i]);
				atualizado = true;
			}
			if (atributosQueroAtualizar[i].equals("email")) {
				membroAtual.setEmail((String) valores[i]);
				atualizado = true;
			}
			if (atributosQueroAtualizar[i].equals("senha")) {
				membroAtual.setSenha((String) valores[i]);
				atualizado = true;
			}

		}
		if (atualizado) {
			daoMembro.atualizar(membro, membroAtual);
		} else {
			throw new Exception("O membro nao pode ser atualizado.");

		}

	}

}
