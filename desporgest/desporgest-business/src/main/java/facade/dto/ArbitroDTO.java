package facade.dto;

import java.io.Serializable;

public class ArbitroDTO implements Serializable{

	private static final long serialVersionUID = 42;
	
	private int numeroFederativo;

	public ArbitroDTO(int numeroFederativo) {
		this.numeroFederativo = numeroFederativo;
	}

	public int getNumeroFederativo() {
		return numeroFederativo;
	}
}
