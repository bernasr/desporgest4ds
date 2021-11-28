package facade.dto;

import java.io.Serializable;
import java.util.List;

public class ProvaDTO implements Serializable {

	private static final long serialVersionUID = 42;

	private String designacao;
	private int numeroParticiapantes;
	private int numeroArbitroPorEncontro;
	private List<EncontroDTO> encontros;

	public ProvaDTO(String designacao, int numeroParticiapantes,
			int numeroArbitroPorEncontro,
			List<EncontroDTO> encontros) {
		this.designacao = designacao;
		this.numeroParticiapantes = numeroParticiapantes;
		this.numeroArbitroPorEncontro = numeroArbitroPorEncontro;
		this.encontros = encontros;
	}

	public String getDesignacao() {
		return designacao;
	}

	public int getNumeroParticiapantes() {
		return numeroParticiapantes;
	}

	public int getNumeroArbitroPorEncontro() {
		return numeroArbitroPorEncontro;
	}

	public Iterable<EncontroDTO> getEncontros() {
		return encontros;
	}   
}
