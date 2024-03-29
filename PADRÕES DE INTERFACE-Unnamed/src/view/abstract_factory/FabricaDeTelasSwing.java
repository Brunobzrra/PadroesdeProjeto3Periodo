package view.abstract_factory;

import view.TelaCadastroHorarioPrevistoSwing;
import view.TelaPontoSwing;
import view.autenticacao.TelaAutenticacaoSwing;
import view.autenticacao.TelaConfiguracaoAdminSwing;
import view.autenticacao.TelaCriarContaSwing;
import view.projetos.TelaCadastroEditaisSwing;
import view.projetos.TelaCadastroGruposSwing;
import view.projetos.TelaCadastroProjetosSwing;
import view.projetos.TelaJustificativaPontoSwing;
import view.projetos.TelaPrincipalSwing;

/**
 * Fabrica de telas concreta para implementacao em swing usada
 * 
 * @author bruno
 */

public class FabricaDeTelasSwing implements InterfaceFabricaDeTelas {
	private static FabricaDeTelasSwing fabricaTelasSwing = new FabricaDeTelasSwing();

	private FabricaDeTelasSwing() {

	}

	public synchronized static InterfaceFabricaDeTelas getFabrica() {
		return fabricaTelasSwing;
	}

	public InterfaceTelaAutenticacao fabricarTelaAutenticacao() {

		return new TelaAutenticacaoSwing();
	}

	public InterfaceTelaCriarConta fabricarTelaCriarConta() {

		return new TelaCriarContaSwing();
	}

	public InterfaceTelaConfiguracaoAdmin fabricarTelaConfiguracaoAdmin() {

		return new TelaConfiguracaoAdminSwing();
	}

	public InterfaceTelaCadastroProjetos fabricarTelaCadastroProjetos() {

		return new TelaCadastroProjetosSwing();
	}

	public InterfaceTelaCadastroGrupos fabricarTelaCadastroGrupos() {

		return new TelaCadastroGruposSwing();
	}

	public InterfaceTelaCadastroEditais fabricarTelaCadastroEditais() {

		return new TelaCadastroEditaisSwing();
	}

	public InterfaceTelaPonto fabricarTelaPonto() {

		return new TelaPontoSwing();
	}

	public InterfaceTelaJustificarPonto fabricarTelaJustificarPonto() {

		return new TelaJustificativaPontoSwing();
	}

	public InterfaceTelaPrincipal fabricarTelaPrincipal() {

		return new TelaPrincipalSwing();
	}

	public InterfaceTelaCadastroHorarioPrevisto fabricarTelaCadastroHorarioPrevisto() {
		return new TelaCadastroHorarioPrevistoSwing();
	}

}
