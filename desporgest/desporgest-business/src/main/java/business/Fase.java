package business;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Classe que representa uma fase ou jornada de uma prova
 * 
 * @author Grupo 2
 *
 */
@Entity
public class Fase {

	public static final String PROVA = "prova";
	@Id
	@GeneratedValue
	private int id;
	private int fase;
	@OneToMany(mappedBy = Encontro.FASE, cascade = CascadeType.PERSIST)
	private Set<Encontro> encontros;

	@ManyToOne
	private Prova prova;

	protected Fase() {}

	/**
	 * Cria uma nova fase
	 * @param fase Numero da fase na prova
	 * @param prova Prova da fase
	 */
	public Fase(int fase, Prova prova) {
		this.fase = fase;
		this.prova = prova;
		encontros = new HashSet<>();
	}

	/**
	 * Adiciona um encontro a esta fase
	 * @param e Encontro a adicionar
	 */
	public void addEncontro(Encontro e) {
		encontros.add(e);
	}

	/**
	 * Retorna uma cole��o iteravel com todos os encontros desta fase
	 * @return uma cole��o iteravel com todos os encontros desta fase
	 */
	public Iterable<Encontro> getEncontros() {
		return encontros;
	}

	/**
	 * Verifica se um determinado encontro se encontra nesta fase
	 * @param e Encontro a verificar
	 * @return True se o encontro for desta fase, false caso contrario
	 */
	public boolean containsEncontro(Encontro e) {
		return encontros.contains(e);
	}

	/**
	 * Retorna o numero desta fase
	 * @return O numero desta fase
	 */
	public int getFase() {
		return fase;
	}
	
	public Prova getProva() {
		return prova;
	}

	/**
	 * Retorna a desina��o da prova no qual esta fase esta inserida
	 * @return A desina��o da prova no qual esta fase esta inserida
	 */
	public String getDesignacaoDaProva() {
		return prova.getDesignacao();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fase;
		result = prime * result + ((prova == null) ? 0 : prova.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fase other = (Fase) obj;
		if (fase != other.fase)
			return false;
		if (prova == null) {
			if (other.prova != null)
				return false;
		} else if (!prova.equals(other.prova))
			return false;
		return true;
	}

}
