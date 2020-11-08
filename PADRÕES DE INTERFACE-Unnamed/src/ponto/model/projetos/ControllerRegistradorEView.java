package ponto.model.projetos;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Set;

import model.autenticacao.ContaAutenticacaoProvedorInterno;
import model.autenticacao.ContaEmail;
import model.autenticacao.ContaEmailLivre;
import model.autenticacao.Membro;
import model.projetos.Participacao;
import model.projetos.Projeto;
import model.projetos.ProjetoComponente;
import model.utilitarios.ConversorDeHoraEDia;
import model.utilitarios.PegadorDeEmailDoDaoMembro;
import persistencia.xml.DAOXMLMembroConta;
import persistencia.xml.DAOXMLProjetoParticipacao;

public class ControllerRegistradorEView {

	private static ControllerRegistradorEView controllerUnico;

	private static DAOXMLProjetoParticipacao dao = new DAOXMLProjetoParticipacao();

	private ServicoRegistradorPontoCentral proxy;

	private ControllerRegistradorEView()
			throws RemoteException, MalformedURLException, UnknownHostException, NotBoundException {
		proxy = (ServicoRegistradorPontoCentral) Naming
				.lookup("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/ServicoRemotoPontoTrabalhado");

	}

	public void registrarPonto(String nomeDoProjeto, String login, String senha) throws Exception {

		Object[] valores = { nomeDoProjeto };
		String[] atributos = { "nome" };
		Set<Projeto> recuperado = dao.consultarAnd(atributos, valores);
		Object[] recuperados = recuperado.toArray();
		if (recuperados!=null) {
			proxy.registrarPonto((Projeto) recuperados[0], login);
		}else
			throw new Exception("Projeto n�o existente!");
	}

	public float horasTrabalhadasValidas(String login, String nomeDoProjeto) throws RemoteException, Exception {

		Object[] valores = { nomeDoProjeto };
		String[] atributos = { "nome" };
		Set<Projeto> recuperado = dao.consultarAnd(atributos, valores);
		Projeto[] recuperados = (Projeto[]) recuperado.toArray();
		dao.consultarAnd(atributos, valores);
		for (ProjetoComponente membroDoFor : recuperados[0].getItens()) {
			if (membroDoFor instanceof Membro) {
				if (((Membro) membroDoFor).getEmail().equalsIgnoreCase(login)) {
					for (Participacao participacaoDoFor : PegadorDeEmailDoDaoMembro
							.recuperarParticipacao((Membro) membroDoFor)) {
						for (PontoTrabalhado pontoDoFor : participacaoDoFor.getPontos()) {
							return proxy.horasTrabalhadasValidas(pontoDoFor.getDataHoraEntrada(),
									pontoDoFor.getDataHoraSaida(), (Membro) membroDoFor);
						}
					}
				}

			}
		}
		return 0;

	}

	public Set<PontoTrabalhado> getPontosInvalidos(String login, String nomeDoProjeto) throws Exception {
		Object[] valores = { nomeDoProjeto };
		String[] atributos = { "nome" };
		Set<Projeto> recuperado = dao.consultarAnd(atributos, valores);
		Projeto[] recuperados = (Projeto[]) recuperado.toArray();
		dao.consultarAnd(atributos, valores);
		for (ProjetoComponente membroDoFor : recuperados[0].getItens()) {
			if (membroDoFor instanceof Membro) {
				if (((Membro) membroDoFor).getEmail().equalsIgnoreCase(login)) {
					return proxy.getPontosInvalidos((Membro) membroDoFor);

				}

			}
		}
		return null;
	}

	public String getDetalhes(String login, String nomeDoProjeto) throws Exception {
		String retorno = "Defict de Horas: " + defcitHoras(login, nomeDoProjeto) + "\n Horas trabalhadas validas: "
				+ horasTrabalhadasValidas(login, nomeDoProjeto) + "\nPontos invalidos: ";

		Set<PontoTrabalhado> pontos = getPontosInvalidos(login, nomeDoProjeto);
		PontoTrabalhado[] recuperados = (PontoTrabalhado[]) pontos.toArray();

		for (int i = 0; i < recuperados.length; i++) {
			retorno += "\n" + " Hora e dia de entrada: "
					+ ConversorDeHoraEDia.pegarHoraEDia(recuperados[i].getDataHoraEntrada()) + " Hora e dia de saida: "
					+ ConversorDeHoraEDia.pegarHoraEDia(recuperados[i].getDataHoraSaida()) + " justificativa: "
					+ recuperados[i].getJustificativa() + "\n";
		}
		return retorno;
	}

	public float defcitHoras(String login, String nomeDoProjeto) throws Exception {
		Object[] valores = { nomeDoProjeto };
		String[] atributos = { "nome" };
		Set<Projeto> recuperado = dao.consultarAnd(atributos, valores);
		Projeto[] recuperados = (Projeto[]) recuperado.toArray();
		dao.consultarAnd(atributos, valores);
		for (ProjetoComponente membroDoFor : recuperados[0].getItens()) {
			if (membroDoFor instanceof Membro) {
				if (((Membro) membroDoFor).getEmail().equalsIgnoreCase(login)) {
					for (Participacao participacaoDoFor : PegadorDeEmailDoDaoMembro
							.recuperarParticipacao((Membro) membroDoFor)) {
						for (PontoTrabalhado pontoDoFor : participacaoDoFor.getPontos()) {
							return proxy.defcitHoras(pontoDoFor.getDataHoraEntrada(), pontoDoFor.getDataHoraSaida(),
									(Membro) membroDoFor);
						}
					}
				}

			}
		}
		return 0;
	}

	public static ControllerRegistradorEView getInstance() {

		if (controllerUnico == null) {
			try {
				return new ControllerRegistradorEView();
			} catch (RemoteException | MalformedURLException | UnknownHostException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return controllerUnico;
	}

	public static void main(String[] args) {
		Membro joseClaudiu = new Membro(Long.parseLong("111"), "jose claudiu", "fananitadz@gmail.com", "1212");
		ContaEmail conta = new ContaEmailLivre();
		conta.setImplementacaoContaBridge(new ContaAutenticacaoProvedorInterno());
		joseClaudiu.setConta(conta);
		joseClaudiu.setAtivo(true);
		joseClaudiu.setAdministrador(true);
		Participacao participacao = new Participacao(new Date(System.currentTimeMillis()),
				Float.parseFloat("0"), (short) 0, (short) 0, true);
		participacao.setMembro(joseClaudiu);
		try {
			participacao.adicionar(joseClaudiu);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Projeto projeto1 = new Projeto("Projeto novo", 0, 0, 0, 0);
		DAOXMLProjetoParticipacao daoao = new DAOXMLProjetoParticipacao();
		try {
			DAOXMLMembroConta daoMembro = new DAOXMLMembroConta();
			daoMembro.criar(joseClaudiu);
			projeto1.adicionar(participacao);
			daoao.criar(projeto1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
