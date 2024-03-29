package persistencia.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import model.autenticacao.ContaEmail;
import model.autenticacao.Membro;

public class DAOXMLMembroConta {

	private final XStream xstream = new XStream(new DomDriver("UTF-8"));
	private long id = 0;
	private HashMap<Long, Membro> persistidos;

	private final File arquivoColecao = new File("XMLMembroConta.xml");

	/*
	 * metodo que vai verificar se existe algum membro no hashmap de persistidos,
	 * caso sim, retorna true
	 */
	public boolean isVazia() {
		persistidos = carregarXML();
		if (persistidos.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * este metodo retorna o objeto por indentificador
	 * 
	 * @param nome
	 * @return
	 */
	public Membro recuperarPorIndentificador(long matricula) {
		this.persistidos = this.carregarXML();
		Set<Long> chaves = persistidos.keySet();
		for (Long long1 : chaves) {
			if (persistidos.get(long1).getMatricula() == matricula) {
				return persistidos.get(long1);
			}
		}
		return null;
	}

	public Membro recuperarPorEmail(String email) {
		this.persistidos = this.carregarXML();
		Set<Long> chaves = persistidos.keySet();
		for (Long chave : chaves) {
			if (persistidos.get(chave).getEmail().equals(email)) {
				return persistidos.get(chave);
			}
		}
		return null;
	}
	public Membro isAdmimPelaMatricula(long matricula) {
		this.persistidos = this.carregarXML();
		Membro membro = recuperarPorIndentificador(matricula);
		if (membro.isAdministrador()) {
			return membro;
		}
		return null;
	}

	/*
	 * adiciona um novo membro na colecao de persistidos, que � salvo
	 * posteriormente, adicionando assim um membro ao XML, que � nosso BD.
	 * 
	 * @params membro
	 */
	public void criar(Membro membro) throws Exception {
		if (membro.getNome().length() < 5 || membro.getSenha().length() < 5 || membro.getMatricula() == 0) {
			throw new Exception("Digite todos os parametros corretamente!");
		}
		String[] atributos = { "matricula" };
		Object[] valores = { membro.getMatricula() };
		if (consultarAnd(atributos, valores).size() == 0) {
			this.persistidos = this.carregarXML();
			id = persistidos.size() + 1;
			this.persistidos.put(id, membro);
			this.salvarXML(persistidos);
			return;
		}
		throw new Exception("Membro j� existente");
	}
	/*
	 * Metodo que ira procurar uma chave de um membro especifico no HASHSET de
	 * persistidos, returna o indice que o membro se encontra no HASHSET
	 * 
	 * @params procurado
	 */

	private Long procurarChave(Membro procurado) {
		Long indice = null;
		Set<Long> chaves = persistidos.keySet();
		for (Long long1 : chaves) {
			Membro recuperado = persistidos.get(long1);
			if (recuperado.equals(procurado)) {
				indice = long1;
				break;
			}
		}
		return indice;
	}

	/*
	 * metodo usado para remover um membro do HASHSET persistidos, que eh
	 * recuperado, modificado, e posterior mente salvo no BD via salvarXML()
	 * 
	 * @params membroRemover
	 */
	public void remover(Membro membroRemover) {
		this.persistidos = this.carregarXML();
		Long indice = procurarChave(membroRemover);
		if (indice != null) {
			persistidos.remove(indice);
			this.salvarXML(persistidos);
		}
	}

	/*
	 * Metodo que substitui um membro no HASHSET de persistidos, colocando outro
	 * membro de interesse no lugar com isso e salvando o hashset posteriormente,
	 * com isso, atualizando o valor do membro no BD
	 */
	public boolean atualizar(Membro membroSubstituivel, Membro membroSubstituto) throws Exception {
		this.persistidos = this.carregarXML();
		Set<Long> chaves = persistidos.keySet();
		for (Long chave : chaves) {
			if (persistidos.get(chave).equals(membroSubstituivel)) {
				persistidos.replace(chave, membroSubstituto);
			}

		}
		this.salvarXML(persistidos);
		return true;
	}

	/*
	 * metodo usado para consultar um membro no hashset de persistidos, por meio de
	 * seus atributos. caso exista um membro com o mesmo ou os mesmos atributos
	 * escolhidos,eh retornado um set com todos os membros correspondentes. sao
	 * ultilizados como paramentro de entrada um array de string onde o nome dos
	 * atributos que se deseja consulta, exatamente como estao declarados na classe
	 * do membro sao inseridos e os seus respectivos valores sao adicionados nas
	 * repectivas posicoes em um array de object, que no cao sao os valores desses
	 * atributos.
	 * 
	 * @params atributos, valores
	 */
	public Set<Membro> consultarAnd(String[] atributos, Object[] valores) {
		this.persistidos = this.carregarXML();
		Set<Membro> auxiliar = new HashSet<Membro>();
		Set<Long> chaves = persistidos.keySet();
		for (Long chave : chaves) {
			if (chave != null) {
				Membro membroAuxiliar = persistidos.get(chave);
				boolean[] confirmacoes = new boolean[7];
				if (atributos != null) {
					for (int j = 0; j < atributos.length; j++) {
						if (valores != null) {
							if (atributos[j].equals("nome")) {
								String nomeRecuperado = membroAuxiliar.getNome();
								if (j == 0) {
									if (valores[0].equals(nomeRecuperado)) {
										confirmacoes[0] = true;
									}
								} else if (j == 1) {
									if (valores[1].equals(nomeRecuperado)) {
										confirmacoes[1] = true;
									}
								} else if (j == 2) {
									if (valores[2].equals(nomeRecuperado))
										confirmacoes[2] = true;
								} else if (j == 3) {
									if (valores[3].equals(nomeRecuperado)) {
										confirmacoes[3] = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(nomeRecuperado)) {
										confirmacoes[4] = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(nomeRecuperado)) {
										confirmacoes[5] = true;
									}
								} else if (valores[6].equals(nomeRecuperado)) {
									confirmacoes[6] = true;
								}
							} else if (atributos[j].equals("email")) {
								String emailRecuperado = membroAuxiliar.getEmail();
								if (j == 0) {
									if (emailRecuperado.equals(valores[0])) {
										confirmacoes[0] = true;
									}
								} else if (j == 1) {
									if (emailRecuperado.equals(valores[1])) {
										confirmacoes[1] = true;
									}
								} else if (j == 2) {
									if (emailRecuperado.equals(valores[2]))
										confirmacoes[2] = true;
								} else if (j == 3) {
									if (valores[3].equals(emailRecuperado)) {
										confirmacoes[3] = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(emailRecuperado)) {
										confirmacoes[4] = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(emailRecuperado)) {
										confirmacoes[5] = true;
									}
								} else {
									if (valores[6].equals(emailRecuperado)) {
										confirmacoes[6] = true;
									}
								}
							} else if (atributos[j].equals("senha")) {
								String senhaRecuperada = membroAuxiliar.getSenha();
								if (j == 0) {
									if (senhaRecuperada.equals(valores[0])) {
										confirmacoes[0] = true;
									}
								} else if (j == 1) {
									if (senhaRecuperada.equals(valores[1])) {
										confirmacoes[1] = true;
									}
								} else if (j == 2) {
									if (senhaRecuperada.equals(valores[2]))
										confirmacoes[2] = true;
								} else if (j == 3) {
									if (valores[3].equals(senhaRecuperada)) {
										confirmacoes[3] = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(senhaRecuperada)) {
										confirmacoes[4] = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(senhaRecuperada)) {
										confirmacoes[5] = true;
									}
								} else {
									if (valores[6].equals(senhaRecuperada)) {
										confirmacoes[6] = true;
									}
								}
							} else if (atributos[j].equals("conta")) {
								ContaEmail contaRecuperada = membroAuxiliar.getConta();
								if (j == 0) {
									if (contaRecuperada.equals(valores[0])) {
										confirmacoes[0] = true;
									}
								} else if (j == 1) {
									if (contaRecuperada.equals(valores[1])) {
										confirmacoes[1] = true;
									}
								} else if (j == 2) {
									if (contaRecuperada.equals(valores[2])) {
										confirmacoes[2] = true;
									}
								} else if (j == 3) {
									if (valores[3].equals(contaRecuperada)) {
										confirmacoes[3] = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(contaRecuperada)) {
										confirmacoes[4] = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(contaRecuperada)) {
										confirmacoes[5] = true;
									}
								} else {
									if (valores[6].equals(contaRecuperada)) {
										confirmacoes[6] = true;
									}
								}
							} else if (atributos[j].equals("matricula")) {
								Long matriculaRecuperada = membroAuxiliar.getMatricula();
								if (j == 0) {
									if (matriculaRecuperada.equals(valores[0])) {
										confirmacoes[0] = true;
									}
								} else if (j == 1) {
									if (matriculaRecuperada.equals(valores[1])) {
										confirmacoes[1] = true;
									}
								} else if (j == 2) {
									if (matriculaRecuperada.equals(valores[2]))
										confirmacoes[2] = true;
								} else if (j == 3) {
									if (valores[3].equals(matriculaRecuperada)) {
										confirmacoes[3] = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(matriculaRecuperada)) {
										confirmacoes[4] = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(matriculaRecuperada)) {
										confirmacoes[5] = true;
									}
								} else {
									if (valores[6].equals(matriculaRecuperada)) {
										confirmacoes[6] = true;
									}
								}
							} else if (atributos[j].equals("ativo")) {
								Boolean ativo = membroAuxiliar.getAtivo();
								if (j == 0) {
									if (ativo.equals(valores[0])) {
										confirmacoes[0] = true;
									}
								} else if (j == 1) {
									if (ativo.equals(valores[1])) {
										confirmacoes[1] = true;
									}
								} else if (j == 2) {
									if (ativo.equals(valores[2])) {
										confirmacoes[2] = true;
									}
								} else if (j == 3) {
									if (ativo.equals(valores[3])) {
										confirmacoes[3] = true;
									}
								} else if (j == 4) {
									if (ativo.equals(valores[4])) {
										confirmacoes[4] = true;
									}
								} else if (j == 5) {
									if (ativo.equals(valores[5])) {
										confirmacoes[5] = true;
									}
								} else {
									if (ativo.equals(valores[6])) {
										confirmacoes[6] = true;
									}
								}
							}
						}

					}
				}
				int aux = 0;
				for (int j = 0; j < confirmacoes.length; j++) {
					if (confirmacoes[j] == true) {
						aux++;
					}
				}
				if (aux == atributos.length) {
					auxiliar.add(membroAuxiliar);
				}
			}
		}
		return auxiliar;
	}

	/*
	 * metodo usado para cosultar se existe um determinado membro no hashset de
	 * persistidos, que eh como o BD eh construido, isso eh feito por meio da
	 * disponibilizacao dos atribuos que vao ser consultados via array de string, e
	 * os seus respectivos valoress via um array de object, esse metodo retorna um
	 * set com todos os membros correspondentes a pelo menos um dos atributos
	 * consultados,
	 * 
	 * @params atributos, valores
	 */
	public Set<Membro> consultarOr(String[] atributos, Object[] valores) {
		this.persistidos = this.carregarXML();
		Set<Membro> auxiliar = new HashSet<Membro>();
		Set<Long> chaves = persistidos.keySet();
		boolean adicionar = false;
		Membro membroAuxiliar = null;
		for (Long chave : chaves) {
			if (chave != null) {
				membroAuxiliar = persistidos.get(chave);
				if (atributos != null) {
					for (int j = 0; j < atributos.length; j++) {
						if (valores != null) {
							if (atributos[j].equals("nome")) {
								String nomeRecuperado = membroAuxiliar.getNome();
								if (j == 0) {
									if (valores[0].equals(nomeRecuperado)) {
										adicionar = true;
									}
								} else if (j == 1) {
									if (valores[1].equals(nomeRecuperado)) {
										adicionar = true;
									}
								} else if (j == 2) {
									if (valores[2].equals(nomeRecuperado))
										adicionar = true;
								} else if (j == 3) {
									if (valores[3].equals(nomeRecuperado)) {
										adicionar = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(nomeRecuperado)) {
										adicionar = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(nomeRecuperado)) {
										adicionar = true;
									}
								} else if (valores[6].equals(nomeRecuperado)) {
									adicionar = true;
								}
							} else if (atributos[j].equals("email") && adicionar == false) {
								String emailRecuperado = membroAuxiliar.getEmail();
								if (j == 0) {
									if (emailRecuperado.equals(valores[0])) {
										adicionar = true;
									}
								} else if (j == 1) {
									if (emailRecuperado.equals(valores[1])) {
										adicionar = true;
									}
								} else if (j == 2) {
									if (emailRecuperado.equals(valores[2]))
										adicionar = true;
								} else if (j == 3) {
									if (valores[3].equals(emailRecuperado)) {
										adicionar = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(emailRecuperado)) {
										adicionar = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(emailRecuperado)) {
										adicionar = true;
									}
								} else {
									if (valores[6].equals(emailRecuperado)) {
										adicionar = true;
									}
								}
							} else if (atributos[j].equals("senha") && adicionar == false) {
								String senhaRecuperada = membroAuxiliar.getSenha();
								if (j == 0) {
									if (senhaRecuperada.equals(valores[0])) {
										adicionar = true;
									}
								} else if (j == 1) {
									if (senhaRecuperada.equals(valores[1])) {
										adicionar = true;
									}
								} else if (j == 2) {
									if (senhaRecuperada.equals(valores[2]))
										adicionar = true;
								} else if (j == 3) {
									if (valores[3].equals(senhaRecuperada)) {
										adicionar = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(senhaRecuperada)) {
										adicionar = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(senhaRecuperada)) {
										adicionar = true;
									}
								} else {
									if (valores[6].equals(senhaRecuperada)) {
										adicionar = true;
									}
								}
							} else if (atributos[j].equals("conta") && adicionar == false) {
								ContaEmail contaRecuperada = membroAuxiliar.getConta();
								if (j == 0) {
									if (contaRecuperada.equals(valores[0])) {
										adicionar = true;
									}
								} else if (j == 1) {
									if (contaRecuperada.equals(valores[1])) {
										adicionar = true;
									}
								} else if (j == 2) {
									if (contaRecuperada.equals(valores[2])) {
										adicionar = true;
									}
								} else if (j == 3) {
									if (valores[3].equals(contaRecuperada)) {
										adicionar = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(contaRecuperada)) {
										adicionar = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(contaRecuperada)) {
										adicionar = true;
									}
								} else {
									if (valores[6].equals(contaRecuperada)) {
										adicionar = true;
									}
								}
							} else if (atributos[j].equals("matricula") && adicionar == false) {
								Long matriculaRecuperada = membroAuxiliar.getMatricula();
								if (j == 0) {
									if (matriculaRecuperada.equals(valores[0])) {
										adicionar = true;
									}
								} else if (j == 1) {
									if (matriculaRecuperada.equals(valores[1])) {
										adicionar = true;
									}
								} else if (j == 2) {
									if (matriculaRecuperada.equals(valores[2]))
										adicionar = true;
								} else if (j == 3) {
									if (valores[3].equals(matriculaRecuperada)) {
										adicionar = true;
									}
								} else if (j == 4) {
									if (valores[4].equals(matriculaRecuperada)) {
										adicionar = true;
									}
								} else if (j == 5) {
									if (valores[5].equals(matriculaRecuperada)) {
										adicionar = true;
									}
								} else {
									if (valores[6].equals(matriculaRecuperada)) {
										adicionar = true;
									}
								}
							} else if (atributos[j].equals("ativo") && adicionar == false) {
								Boolean ativo = membroAuxiliar.getAtivo();
								if (j == 0) {
									if (ativo.equals(valores[0])) {
										adicionar = true;
									}
								} else if (j == 1) {
									if (ativo.equals(valores[1])) {
										adicionar = true;
									}
								} else if (j == 2) {
									if (ativo.equals(valores[2])) {
										adicionar = true;
									}
								} else if (j == 3) {
									if (ativo.equals(valores[3])) {
										adicionar = true;
									}
								} else if (j == 4) {
									if (ativo.equals(valores[4])) {
										adicionar = true;
									}
								} else if (j == 5) {
									if (ativo.equals(valores[5])) {
										adicionar = true;
									}
								} else {
									if (ativo.equals(valores[6])) {
										adicionar = true;
									}
								}
							}
						}

					}
				}

			}
			if (adicionar) {
				auxiliar.add(membroAuxiliar);
			}
		}
		return auxiliar;
	}

	/*
	 * salva o hashmap de persistidos em XML
	 * 
	 * @params persistidos
	 */
	private void salvarXML(HashMap<Long, Membro> persistidos) {

		String xml = xstream.toXML(persistidos);

		try {
			arquivoColecao.createNewFile();
			PrintWriter gravar = new PrintWriter(arquivoColecao);
			gravar.print(xml);
			gravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Caso o XML j� exista, apenas atualiza o mesmo, caso nao, um novo XML eh
	 * criado
	 */
	private HashMap<Long, Membro> carregarXML() {

			try {
				FileInputStream r = new FileInputStream(arquivoColecao);
				return (HashMap<Long, Membro>) xstream.fromXML(r);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		return new HashMap<Long, Membro>();
	}

}
