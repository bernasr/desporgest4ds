package business;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.exceptions.AplicationException;
import javax.persistence.Column;

/**
 * Classe que representa um arbitro
 * 
 * @author Grupo 2
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Arbitro.FIND_BY_NUMERO, query = "SELECT a FROM Arbitro a WHERE a.numeroFederativo = :" + Arbitro.NUMBER),
	@NamedQuery(name = Arbitro.FIND_ALL, query = "SELECT a FROM Arbitro a")
})
public class Arbitro {

	public static final String FIND_BY_NUMERO = "Arbitro.findByNumero";
	public static final String NUMBER = "numeroFederativo";
	public static final String FIND_ALL = "Arbitro.findAll";
	@Id
	@GeneratedValue
	private int id;
	@Column(unique = true, nullable = false)
	private int numeroFederativo;
	@ManyToMany(mappedBy = Encontro.ARBITROS)
	private Set<Encontro> encontros;

	protected Arbitro() {}

	/**
	 * Cria um novo arbitro a partir do numero federativo
	 * @param numeroFederativo - Numero federativo do arbitro
	 */
	public Arbitro(int numeroFederativo) {
		this.numeroFederativo = numeroFederativo;
		Comparator<Encontro> c = (e1, e2) -> { 
			if (e1.equals(e2))
				return 0;
			if (e1.getDataRealizacao().isBefore(e2.getDataRealizacao()))
				return -1;
			return 1;
		};
		encontros = new TreeSet<Encontro>(c);
	}

	/**
	 * Retorna o numero federativo do arbitro
	 * @return numero federativo do arbitro
	 */
	public int getNumeroFederativo() {
		return numeroFederativo;
	}

	/**
	 * Verifica se o arbitro est� a arbitrar num determinado dia
	 * @param dia - Dia a verificar
	 * @return True se estiver a arbitrar, false caso contrario
	 */
	public boolean estaArbitrar(LocalDate dia) {
		return encontros.stream()
				.anyMatch(e -> e.getDataRealizacao().equals(dia));
	}

	/**
	 * Adiciona um encontro ao arbitro
	 * @param e - Encontro a adicionar 
	 */
	public void adicionarEncontro(Encontro e) {
		encontros.add(e);
	}

	/**
	 * Retorna o encontro do dia do arbitro. O Arbitro s� pode ter um econtro
	 * por dia.
	 * @param date Dia do encontro
	 * @return Unico encontro do dia date
	 * @throws AplicationException - Quando n�o existe encontro nesse dia
	 */
	public Encontro encontroDoDia(LocalDate date) throws AplicationException {
		for (Encontro enc : encontros)
			if (enc.getDataRealizacao().equals(date))
				return enc;
		throw new AplicationException(
				"N�o h� encontro no dia " + date.toString());
	}

	/**
	 * Retorna uma cole��o iteravel com os encontro do arbitro ordenados pelo
	 * dia de forma ascendente
	 * @return uma cole��o iteravel com os encontro do arbitro
	 */
	public Iterable<Encontro> getEncontrosOrdenadosPorData() {
		return encontros;
	}

	@Override
	public int hashCode() {
		return numeroFederativo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arbitro other = (Arbitro) obj;
		if (numeroFederativo != other.numeroFederativo)
			return false;
		return true;
	}
	
	
}
