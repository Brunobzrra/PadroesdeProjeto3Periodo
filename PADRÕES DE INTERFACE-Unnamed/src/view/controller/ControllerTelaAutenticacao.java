package view.controller;

import model.casosDeUsofachadas.CasoDeUsoDoiseNove;

public class ControllerTelaAutenticacao {

	private CasoDeUsoDoiseNove casoDeUso;

	public ControllerTelaAutenticacao() {
		casoDeUso = new CasoDeUsoDoiseNove();
	}

	public void fazerLogin(String login, String senha, String tipoProvedor) throws Exception {
		casoDeUso.fazerLogin(login, senha, tipoProvedor);

	}

	public void fazerLogout(String login) throws Exception {
		casoDeUso.fazerLogout(login);

	}

}
