package business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Classe que representa um participante de uma determinada prova.
 * O participante 'só faz sentido' dentro de uma prova.
 * 
 * @author Grupo 2
 *
 */
@Entity
public class Participante {

	public static final String PROVA = "prova";
	@Id
	@GeneratedValue
	private int id;
	private int numeroDaEquipa;
	private static String designacao = "Participante";

	@ManyToOne
	private Prova prova;

	protected Participante() {}

	/**
	 * Cria um novo participante da prova
	 * @param numeroDaEquipa Numero do participante na prova
	 * @param prova Prova do participante
	 */
	public Participante(int numeroDaEquipa, Prova prova) {
		this.numeroDaEquipa = numeroDaEquipa;
		this.prova = prova;
	}

	/**
	 * Retorna o numero do partipante na prova
	 * @return O numero do partipante na prova
	 */
	public int getNumeroDaEquipa() {
		return numeroDaEquipa;
	}

	/**
	 * Retorna o nome simbolico do participante
	 * @return O nome simbolico do participante
	 */
	public String nome() {
		return designacao + " " + numeroDaEquipa;
	}

	@Override
	public int hashCode() {
		return numeroDaEquipa;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participante other = (Participante) obj;
		if (numeroDaEquipa != other.numeroDaEquipa)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome();
	}
}
