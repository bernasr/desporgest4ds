package business;

public enum TipoProva {
	TACA {
		public String toString() {
			return "Taça";
		}
	},
	COMPETICAO {
		public String toString() {
			return "Competicao";
		}
	}
}
