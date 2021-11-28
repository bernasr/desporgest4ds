package business;

import java.time.LocalDate;

/**
 * Enumerado que representa a periodicidade das fases de uma prova
 * @author Nuno
 *
 */
public enum Periodicidade {

	SEMENAL {
		public LocalDate next(LocalDate atual) {
			return atual.plusDays(7);
		}
	},
	MENSAL {
		public LocalDate next(LocalDate atual) {
			return atual.plusMonths(1);
		}
	},
	SEMESTRAL {
		public LocalDate next(LocalDate atual) {
			return atual.plusMonths(6);
		}
	};

	/**
	 * Calcula a data da proxima fase de uma prova
	 * @param atual - Data da fase "atual"
	 * @return A data da proxima fase de uma prova
	 */
	public abstract LocalDate next(LocalDate atual);
}
