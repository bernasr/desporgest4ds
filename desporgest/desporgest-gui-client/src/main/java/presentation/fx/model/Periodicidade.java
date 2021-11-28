package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Periodicidade {
	private final StringProperty descricao;
	private final int index;
	
	public Periodicidade(String descricao, int index) {
		this.descricao = new SimpleStringProperty();
		this.descricao.set(descricao);
		this.index = index;
	}
	
	public final StringProperty descricaoProperty() {
		return descricao;
	}
	
	public final String getDescricao() {
		return descricao.get();
	}
	
	public final void setDescricao(final String descricao) {
		this.descricao.set(descricao);
	}
	
	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return getDescricao();
	}
	
	
}
