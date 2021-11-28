package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TipoProva {
	private final StringProperty designacao;
	private final int index;
	
	public TipoProva(String designacao, int index) {
		this.designacao = new SimpleStringProperty();
		this.designacao.set(designacao);
		this.index = index;
	}
	
	public final StringProperty designacaoProperty() {
		return designacao;
	}
	
	public final String getDesignacao() {
		return designacao.get();
	}
	
	public final int getIndex() {
		return index;
	}
	
	public final void setDesignacao(final String designacao) {
		this.designacao.set(designacao);
	}

	@Override
	public String toString() {
		return getDesignacao();
	}
	
	
}
