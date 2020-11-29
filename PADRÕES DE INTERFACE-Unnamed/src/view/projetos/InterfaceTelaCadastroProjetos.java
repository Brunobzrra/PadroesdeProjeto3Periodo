package view.projetos;

import java.util.ArrayList;
import java.util.Date;

public interface InterfaceTelaCadastroProjetos {

	public abstract void criarProjeto(String nome, float aporteCusteioReais, float aporteCapitalReais,
			float gastoExecutadoCusteioReais, float gastoExecutadoCapitalReais, long matricula,
			float aporteCusteioMensalReais, short qtdMesesCusteados, short qtdMesesPagos) throws Exception;

	public abstract void atualizarProjeto(String nome, float aporteCusteioReais, float aporteCapitalReais)
			throws Exception;

	public abstract void removerProjeto(String nomeDoProjeto) throws Exception;

	public abstract void adicionarParticipacao(long matriculaDoCordenador, long matriculaDoMembroQueQuerEstrarNoProjeto,
			String nomeDoProjeto, Date dataInicio, float aporteCusteioMensalReais, short qtdMesesCusteados,
			short qtdMesesPagos) throws Exception;

	public abstract void removerParticipacao(long matriculaDoCordenador, long matriculaDoMembroQueQuerRemover,
			String nomeDoProjeto) throws Exception;

	public abstract Object[] mostrarProjetosDoUsuarioLogado() throws Exception;

}
