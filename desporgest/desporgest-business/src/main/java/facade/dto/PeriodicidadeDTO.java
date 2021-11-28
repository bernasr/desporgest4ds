package facade.dto;

import java.io.Serializable;

public class PeriodicidadeDTO implements Serializable {
	
	private static final long serialVersionUID = 42;
	
	private int index;
	private String descricao;
	
	public PeriodicidadeDTO(int index, String descricao) {
		super();
		this.index = index;
		this.descricao = descricao;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
