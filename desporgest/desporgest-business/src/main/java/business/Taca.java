package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;

import business.exceptions.DataInicioInvalidaException;
import business.exceptions.NumeroParticipantesInvalidoException;

/**
 * Classe que representa um prova do tipo ta�a
 * 
 * @author Grupo 2
 *
 */
@Entity
public class Taca extends Prova {

	protected Taca() {}

	/**
	 * Cria uma nova ta�a
	 * @param designacao - Designa��o da prova
	 * @param numeroParticipantes - Numero de participantes
	 * @param numeroArbitrosPorEncontro - Numero de arbitros por encontro
	 * @param dataInicio - Data de inicio da prova, isto �, dos jogos da sua
	 * primeira fase
	 * @param periodicidade - Periodicidade das fases da prova
	 * @throws NumeroParticipantesInvalidoException - Se o numero de
	 * participantes n�o for valido para uma ta�a
	 * @throws DataInicioInvalidaException - Se a data de inicio da ta�a
	 * n�o for a um sabado ou domingo
	 */
	public Taca(String designacao, int numeroParticipantes,
			int numeroArbitrosPorEncontro, LocalDate dataInicio,
			Periodicidade periodicidade)
			throws NumeroParticipantesInvalidoException,
			DataInicioInvalidaException {
		super(designacao, numeroParticipantes, numeroArbitrosPorEncontro,
				dataInicio, periodicidade);
	}

	@Override
	protected void criarEncontro() {
		List<Participante> list = new ArrayList<Participante>();
		int n = getNumeroParticipantes();
		for (int i = 1; i <= n; i++)
			list.add(new Participante(i, this));
		addParticipantes(list);
		Random rand = new Random();
		Fase fase = criarFase(1);
		for (int i = 0; i < n / 2; i++) {
			int a = rand.nextInt(list.size());
			Participante p1 = list.remove(a);
			a = rand.nextInt(list.size());
			Participante p2 = list.remove(a);
			Encontro e = new Encontro(p1, p2, getDataInicio(), fase);
			fase.addEncontro(e);
		}
		LocalDate date = getDataInicio();
		for (int i = n / 2, f = 2; i != 1; i /= 2, f++) {
			fase = criarFase(f);
			date = getPeriodicidade().next(date);
			for (int j = 0; j < i / 2; j++)
				fase.addEncontro(new Encontro(null, null, date, fase));
		}
	}

}
