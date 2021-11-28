package facade.dto;

import java.io.Serializable;

public class TipoProvaDTO implements Serializable {
	
	private static final long serialVersionUID = 42;
	
	private String designacao;
	private int index;
	
	public TipoProvaDTO(int index, String designacao) {
		this.index = index;
		this.designacao = designacao;
	}

	public String getDesignacao() {
		return designacao;
	}

	public int getIndex() {
		return index;
	}
}
