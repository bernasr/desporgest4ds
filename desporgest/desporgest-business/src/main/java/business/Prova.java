package business;

import static javax.persistence.EnumType.STRING;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.exceptions.DataInicioInvalidaException;
import business.exceptions.NumeroParticipantesInvalidoException;

/**
 * Classe abstrata que representa uma prova
 * 
 * @author Grupo 2
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Prova.FIND_BY_DESIGNACAO, query = "SELECT p FROM Prova p WHERE p.designacao = :" + Prova.DESIGNACAO),
	@NamedQuery(name = Prova.FIND_ALL, query = "SELECT p FROM Prova p")
})
public abstract class Prova {

	public static final String ID = "id";
	public static final String FIND_BY_DESIGNACAO = "Prova.findByDesignacao";
	public static final String DESIGNACAO = "designacao";
	public static final String FIND_ALL = "Prova.findAll";

	@Id
	@GeneratedValue
	private int id;

	@Column(unique = true, nullable = false)
	private String designacao;

	@Column(nullable = false)
	private int numeroParticipantes;

	@Column(nullable = false)
	private int numeroArbitrosPorEncontro;

	@Column(nullable = false)
	private Date dataInicio;

	@Enumerated(STRING)
	@Column(nullable = false)
	private Periodicidade periodicidade;

	@OneToMany(mappedBy = Participante.PROVA, cascade = CascadeType.PERSIST)
	private Set<Participante> participantes;

	@OneToMany(mappedBy = Fase.PROVA, cascade = CascadeType.PERSIST)
	private Set<Fase> fases;

	protected Prova() {}

	/**
	 * Cria uma nova prova
	 * @param designacao - Designa��o da prova
	 * @param numeroParticipantes - Numero de participantes
	 * @param numeroArbitrosPorEncontro - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova, isto �, dos jogos da sua
	 * primeira fase
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws NumeroParticipantesInvalidoException - Se o numero de
	 * participantes n�o for valido para a prova especifica
	 * @throws DataInicioInvalidaException - Se a data de inicio da prova
	 * n�o for a um sabado ou domingo
	 */
	public Prova(String designacao, int numeroParticipantes,
			int numeroArbitrosPorEncontro, LocalDate dataInicio,
			Periodicidade periodicidade)
			throws NumeroParticipantesInvalidoException,
			DataInicioInvalidaException {
		this.designacao = designacao;
		this.numeroParticipantes = numeroParticipantes;
		this.numeroArbitrosPorEncontro = numeroArbitrosPorEncontro;
		verificarData(dataInicio);
		this.dataInicio = new Date(LocalDate.now().toEpochDay()); //a bug lives here
		this.periodicidade = periodicidade;
		participantes = new HashSet<>();
		fases = new HashSet<>();
		criarEncontro();
	}

	/**
	 * Verifica se a data de inicio da prova � correta, isto �, se � a um
	 * sabado ou domingo
	 * @param data - Data de inicio da prova
	 * @throws DataInicioInvalidaException - Se a data da prova n�o for a um
	 * sabado ou domingo
	 */
	private void verificarData(LocalDate data)
			throws DataInicioInvalidaException {
		if (data.getDayOfWeek() != DayOfWeek.SATURDAY
				&& data.getDayOfWeek() != DayOfWeek.SUNDAY)
			throw new DataInicioInvalidaException(
					"Data de in�cio tem de ser um s�bado ou domingo");
	}

	/**
	 * Retorna o numero de participantes da prova
	 * @return O numero de participantes da prova
	 */
	protected int getNumeroDeParticipantes() {
		return numeroParticipantes;
	}

	/**
	 * Adiciona os participantes a esta prova
	 * @param list - Lista com os participante a adicionar
	 */
	protected void addParticipantes(List<Participante> list) {
		for (Participante p : list)
			participantes.add(p);
	}

	/**
	 * Cria uma fase desta prova
	 * @param n - Numero da fase
	 * @return A fase criada
	 */
	protected Fase criarFase(int n) {
		Fase f = new Fase(n, this);
		fases.add(f);
		return f;
	}

	/**
	 * Cria as varias fazes da prova e os seus respetivos encontros
	 */
	protected abstract void criarEncontro();

	/**
	 * Retorna a designa��o da prova
	 * @return A designa��o da prova
	 */
	public String getDesignacao() {
		return designacao;
	}

	/**
	 * Retorna o id da prova
	 * @return O id da prova
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retorna a data de inicio da prova
	 * @return A data de inicio da prova
	 */
	public LocalDate getDataInicio() {
		return dataInicio.toLocalDate();
	}

	/**
	 * Retorna o numero de participantes desta prova
	 * @return O numero de participantes desta prova
	 */
	public int getNumeroParticipantes() {
		return numeroParticipantes;
	}

	/**
	 * Retorna a periodiciodade desta prova
	 * @return A periodiciodade desta prova
	 */
	public Periodicidade getPeriodicidade() {
		return periodicidade;
	}
	
	public int numeroArbitrosPorEncontro() {
	    return numeroArbitrosPorEncontro;
	}

	/**
	 * Retorna os encontros com falta de arbitros desta prova
	 * @return uma cole��o iteravel com os encontros com falta de arbitros
	 */
	public Iterable<Encontro> encontrosComFaltaDeArbitros() {
		List<Encontro> list = new ArrayList<Encontro>();
		for (Fase f : fases)
			for (Encontro e : f.getEncontros())
				if (e.numeroDeArbitros() < numeroArbitrosPorEncontro)
					list.add(e);
		return list;
	}
	
	public Iterable<Fase> getFases() {
	    return fases;
	}

	/**
	 * Verifica se um determinado arbitro arbitra na mesma fase que este encontro
	 * @param a - Arbitro a verificar 
	 * @param e - Encontro no qual se verifica a fase
	 * @return True se um arbitrar nessa fase, false caso contrario
	 */
	public boolean arbitraNestaFase(Arbitro a, Encontro e) {
		Fase fase = getFase(e);
		for (Encontro e2 : fase.getEncontros())
			if (e2.ehArbitrado(a))
				return true;
		return false;
	}

	/**
	 * Verifica a fase do encontro nesta prova
	 * @param e Encontro a procurar
	 * @return A fase do encontro nesta prova
	 */
	private Fase getFase(Encontro e) {
		for (Fase f : fases)
			if (f.containsEncontro(e))
				return f;
		return null;
	}

	/**
	 * Verifica se o arbitro arbitra a outra mao de um encontro nesta prova
	 * @param a Arbitro a verificar
	 * @param e Encontro a verificar
	 * @return True se o arbitro arbitra a outra mao de um encontro, false caso
	 * contrario
	 */
	public boolean arbitraOutraMao(Arbitro a, Encontro e) {
		for (Fase f : fases)
			for (Encontro e2 : f.getEncontros())
				if (e.participante1().equals(e2.participante2())
						&& e.participante2().equals(e2.participante1()))
					return e2.ehArbitrado(a);
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Prova other = (Prova) obj;
		if (designacao != other.designacao)
			return false;
		return true;
	}

}
