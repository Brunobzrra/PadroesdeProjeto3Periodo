package model.autenticacao;

import java.util.Set;

import persistencia.xml.DAOXMLMembroConta;

/**
 * Classe que ira autenticar um membro de acordo com os dados inseridos 
 * por meio do banco de dados interno.
 * 
 * @author bruno
 * */
public class ContaAutenticacaoProvedorInterno extends ContaBridge {
/*
 * O metodo autentica um membro via BD interno, consultando o seu nome e senha e retornando um membro, caso
 * o membro seja recuperado, que ocorrer� se ele ja estiver sido registrado anteriormente.
 * @param emai, senha*/
	public Membro autenticar(String email, String senha) {
		DAOXMLMembroConta dao = new DAOXMLMembroConta();
		String[] atributos = { "email", "senha" };
		String[] valores = { email, senha };

		Set<Membro> membros = dao.consultarAnd(atributos, valores);
		for (Membro membro : membros) {
			return membro;
		}
		return null;
	}

}
