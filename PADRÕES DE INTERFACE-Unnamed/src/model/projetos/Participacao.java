package model.projetos;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import org.joda.time.Period;

import model.autenticacao.Membro;
import ponto.model.projetos.HorarioPrevisto;
import ponto.model.projetos.PontoTrabalhado;

public class Participacao extends ProjetoComponente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dataInicio;

	private Date dataTermino;

	// quantidade de dinheiro recebido por mes
	private float aporteCusteioMensalReais;

	// meses de recebimento futuro
	private short qtdMesesCusteados;

	// meses que foram pagos
	private short qtdMesesPagos;

	private boolean coordenador;

	private ArrayList<PontoTrabalhado> pontos = new ArrayList<PontoTrabalhado>();

	private ArrayList<HorarioPrevisto> horarios = new ArrayList<HorarioPrevisto>();

	private Membro membro;

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	// metodos obrigatorios
	@Override

	public void ativar() {
		// TODO Auto-generated method stub
		setAtivo(true);
	}

	public Participacao(Date dataInicio, float aporteCusteioMensalReais, short qtdMesesCusteados, short qtdMesesPagos,
			boolean coordenador) {
		super();
		this.dataInicio = dataInicio;

		this.aporteCusteioMensalReais = aporteCusteioMensalReais;
		this.qtdMesesCusteados = qtdMesesCusteados;
		this.qtdMesesPagos = qtdMesesPagos;
		this.coordenador = coordenador;
		setTipo(TipoProjetoComponente.PARTICIPACAO);
	}

	@Override
	public void desativar() {
		// TODO Auto-generated method stub
		setAtivo(false);
	}

	public float getCustoTotal() {
		return this.quantidadeDeMesesProjeto() * aporteCusteioMensalReais;

	}

	public void adcionarHorarioPrevisto(HorarioPrevisto horario) {
		for (HorarioPrevisto horarioPrevisto : horarios) {
			if (horario == horarioPrevisto) {
				return;
			}
		}
		horarios.add(horario);
	}

	public void adicionarPonto(PontoTrabalhado ponto) {
		for (PontoTrabalhado pontoTrabalho : pontos) {
			if (ponto == pontoTrabalho) {
				return;
			}
		}
		pontos.add(ponto);
	}

	public void removerPonto(PontoTrabalhado ponto) {
		pontos.remove(ponto);
	}
	public boolean adicionarHorario(HorarioPrevisto hora) {
		for (HorarioPrevisto horario : horarios) {
			if (hora == horario) {
				return false;
			}
		}
		horarios.add(hora);
		return true;
	}
	public void removerHrorario(HorarioPrevisto hora) {
		horarios.remove(hora);
	}
	public float getCusteioReaisNaoGastoTotal() {
		float mesesDife = this.quantidadeDeMesesProjeto() - qtdMesesPagos;
		return aporteCusteioMensalReais * mesesDife;
	}

	private float quantidadeDeMesesProjeto() {
		DateTime i = new DateTime(dataInicio);
		DateTime f = new DateTime(dataTermino);
		Period dura = new Period(i, f);
		return dura.getMonths();
	}

	@Override
	public float getCapitalReaisNaoGastoTotal() throws Exception {
		float aux = 0;
		aux = this.qtdMesesCusteados - this.qtdMesesPagos;
		return aux * this.aporteCusteioMensalReais;
	}

	public void adicionar(ProjetoComponente item) throws Exception {
		throw new Exception("Este item n�o adciona!");
	}

	@Override
	public void remover(ProjetoComponente integracao) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Paricipa��o n�o remove!");
	}

	@Override
	public void mover(ProjetoComponente integracao) throws Exception {
		// TODO Auto-generated method stub
		// Deixamos assim, pois, achamos que n�o faz sentido parci���o mover-se
		throw new Exception("Paricipa��o n�o se move!");
	}

	public boolean equals(Participacao participacao) {
		if (participacao.getDataTermino().equals(dataTermino) && participacao.getDataInicio().equals(dataInicio)
				&& aporteCusteioMensalReais == participacao.getAporteCusteioMensalReais()
				&& qtdMesesCusteados == getQtdMesesCusteados() && qtdMesesPagos == getQtdMesesPagos()) {
			return true;
		}
		return false;
	}

	// getters e setters
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public float getAporteCusteioMensalReais() {
		return aporteCusteioMensalReais;
	}

	public void setAporteCusteioMensalReais(float aporteCusteioMensalReais) {
		this.aporteCusteioMensalReais = aporteCusteioMensalReais;
	}

	public short getQtdMesesCusteados() {
		return qtdMesesCusteados;
	}

	public void setQtdMesesCusteados(short qtdMesesCusteados) {
		this.qtdMesesCusteados = qtdMesesCusteados;
	}

	public short getQtdMesesPagos() {
		return qtdMesesPagos;
	}

	public void setQtdMesesPagos(short qtdMesesPagos) {
		this.qtdMesesPagos = qtdMesesPagos;
	}

	public boolean isCoordenador() {
		return coordenador;
	}

	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}

	public ArrayList<PontoTrabalhado> getPontos() {
		return pontos;
	}

	public Membro getMembro() {
		return membro;
	}

	public ArrayList<HorarioPrevisto> getHorarios() {
		return horarios;
	}

	public String getNome() {
		return membro.getNome();
	}

	public float getGastoTotal() {
		return getCusteioReaisGastoTotal() + getCapitalReaisGastoTotal();
	}

	public float getCusteioReaisGastoTotal() {
		return aporteCusteioMensalReais * qtdMesesPagos;
	}

	public float getCapitalReaisGastoTotal() {
		return 0;
	}
}
