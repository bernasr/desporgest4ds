package business;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import business.exceptions.AplicationException;

/**
 * Classe que representa um encontro
 * 
 * @author Grupo 2
 *
 */
@Entity
@NamedQuery(name = Encontro.FIND_BY_NUMBER, query = "SELECT e FROM Encontro e WHERE e.numero = :" + Encontro.NUMERO)
public class Encontro {

	public static final String FASE = "fase";
	public static final String ARBITROS = "arbitros";
	public static final String FIND_BY_NUMBER = "Encontro.findByNumero";
	public static final String NUMERO = "numero";

	@Id
	@GeneratedValue
	private int numero;
	
	@Column(nullable = false)
	private Date dataRealizacao;

	@ManyToMany
	@JoinTable
	private Set<Arbitro> arbitros;

	@ManyToOne
	private Participante participante1;
	@ManyToOne
	private Participante participante2;

	@ManyToOne
	private Fase fase;

	protected Encontro() {
	}

	/**
	 * Cria um novo encontro
	 * @param participante1 - Paritipante que joga em casa
	 * @param participante2 - Participante que joga fora
	 * @param dataRealizacao - Data de realiza��o do encontro
	 * @param fase - Fase em que est� inserido este encontro
	 */
	public Encontro(Participante participante1, Participante participante2,
			LocalDate dataRealizacao, Fase fase) {
		arbitros = new HashSet<>();
		this.participante1 = participante1;
		this.participante2 = participante2;
		this.dataRealizacao = new Date(LocalDate.now().toEpochDay());
		this.fase = fase;
	}

	/**
	 * Retorna o numero de arbitros do encontr
	 * @return O numero de arbitros do encontr
	 */
	public int numeroDeArbitros() {
		return arbitros.size();
	}

	/**
	 * Veririca este encontro � arbitrado por um determinado arbitro
	 * @param arbitro Arbitro a verificar
	 * @return True se o encontro � arbitro pelo arbitro, false caso contrario
	 */
	public boolean ehArbitrado(Arbitro arbitro) {
		return arbitros.contains(arbitro);
	}

	/**
	 * Adiciona arbitro a este encontro
	 * @param arbitro Arbitro a adicionar
	 */
	public void adicionarArbitro(Arbitro arbitro) {
		arbitros.add(arbitro);
	}

	/**
	 * Retorna o numero do encontro
	 * @return O numero do encontro
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Retorna a data de realiza��o do encontro
	 * @return A data de realiza��o do encontro
	 */
	public LocalDate getDataRealizacao() {
		return dataRealizacao.toLocalDate();
	}

	/**
	 * Retorna o nome particpante que joga em casa
	 * @return O nome particpante que joga em casa
	 */
	public String participante1() {
		return getParticipanteName(participante1);
	}

	/**
	 * Retorna o nome particpante que joga fora
	 * @return O nome particpante que joga fora
	 */
	public String participante2() {
		return getParticipanteName(participante2);
	}

	/**
	 * Retorna o nome do participante part
	 * @param part Particpante
	 * @return O nome do participante part
	 */
	private String getParticipanteName(Participante part) {
		return part == null ? "Sem participante" : part.nome();
	}

	/**
	 * Retorna a fase deste encontro
	 * @return A fase deste encontro
	 */
	public int getFase() {
		return fase.getFase();
	}
	
	public Prova getProva() {
		return fase.getProva();
	}

	/**
	 * Retorn a designa��o da prova no qual este encontro est� inserido
	 * @return A designa��o da prova no qual este encontro est� inserido
	 */
	public String getDesignacaoDaProva() {
		return fase.getDesignacaoDaProva();
	}

	/**
	 * Retorna uma cole��o iteravel com os arbitros que arbitram este encontro
	 * @return uma cole��o iteravel com os arbitros que arbitram este encontro
	 * @throws AplicationException - Se nenhum arbitro arbitrar este encontro
	 */
	public Iterable<Arbitro> getArbitros() throws AplicationException {
		if (arbitros.isEmpty())
			throw new AplicationException("N�o h� arbitros neste encontro");
		return arbitros;
	}

	@Override
	public int hashCode() {
		return numero;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Encontro other = (Encontro) obj;
		if (numero != 0 && other.numero != 0)
			return numero == other.numero;
		if (participante1 == null || other.participante1 == null)
			return false;
		if (fase.equals(other.fase) && participante1.equals(other.participante1)
				&& participante2.equals(other.participante2))
			return true;
		return false;
	}

}
