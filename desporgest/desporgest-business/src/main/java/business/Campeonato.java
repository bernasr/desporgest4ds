package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import business.exceptions.DataInicioInvalidaException;
import business.exceptions.NumeroParticipantesInvalidoException;

/**
 * Classe que representa um prova do tipo campeonato
 * 
 * @author Grupo 2
 *
 */
@Entity
public class Campeonato extends Prova {

	protected Campeonato() {}

	/**
	 * Cria um novo campeonato
	 * @param designacao - Designa��o da prova
	 * @param numeroParticipantes - Numero de participantes
	 * @param numeroArbitrosPorEncontro - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova, isto �, dos jogos da sua
	 * primeira fase
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws NumeroParticipantesInvalidoException - Se o numero de
	 * participantes n�o for valido para um campeonato
	 * @throws DataInicioInvalidaException - Se a data de inicio do campeonato
	 * n�o for a um sabado ou domingo
	 */
	public Campeonato(String designacao, int numeroParticipantes,
			int numeroArbitrosPorEncontro, LocalDate dataInicio,
			Periodicidade periodicidade)
			throws NumeroParticipantesInvalidoException,
			DataInicioInvalidaException {
		super(designacao, numeroParticipantes, numeroArbitrosPorEncontro,
				dataInicio, periodicidade);
	}

	/**
	 * Cria as varias fazes do campeonato e os seus respetivos encontros
	 */
	protected void criarEncontro() {
		List<Participante> list = new ArrayList<Participante>();
		int n = getNumeroParticipantes();
		for (int i = 1; i <= n; i++)
			list.add(new Participante(i, this));
		addParticipantes(list);
		LocalDate date = getDataInicio();
		for (int i = 1; i <= 2 * (n - 1); i++) {
			Fase fase = criarFase(i);
			for (int j = 0; j < n / 2; j++) {
				Participante p1 = list.get(j);
				Participante p2 = list.get(list.size() - 1 - j);
				if (i <= n)
					fase.addEncontro(new Encontro(p1, p2, date, fase));
				else
					fase.addEncontro(new Encontro(p2, p1, date, fase));
			}
			date = getPeriodicidade().next(date);
			Participante p = list.remove(list.size() - 1);
			list.add(1, p);
		}
	}

}
