package presentation.fx.model;

import java.time.LocalDate;

import facade.handlers.IProvaServiceRemote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NovaProvaModel {

	private final StringProperty designacao;
	private final ObjectProperty<TipoProva> tipoProvaSelecionada;
	private final ObservableList<TipoProva> tipoProvas;
	private final IntegerProperty numParticipantes;
	private final IntegerProperty numArbitrosPorEncontro;
	private final ObjectProperty<LocalDate> dataRealizacao;
	private final ObjectProperty<Periodicidade> periodicidadeSelecionada;
	private final ObservableList<Periodicidade> periodicidades;
	
	public NovaProvaModel(IProvaServiceRemote provaService) {
		designacao = new SimpleStringProperty();
		numParticipantes = new SimpleIntegerProperty();
		numArbitrosPorEncontro = new SimpleIntegerProperty();
		dataRealizacao = new SimpleObjectProperty<>(null);
		tipoProvaSelecionada = new SimpleObjectProperty<>(null);
		tipoProvas = FXCollections.observableArrayList();
		periodicidadeSelecionada = new SimpleObjectProperty<>(null);
		periodicidades = FXCollections.observableArrayList();
		
		provaService.getPeriodicidades().forEach(p -> periodicidades.add(new Periodicidade(p.getDescricao(), p.getIndex())));
		provaService.getTiposDeProvas().forEach(t -> tipoProvas.add(new TipoProva(t.getDesignacao(), t.getIndex())));
	}

	public TipoProva getTipoProvaSelecionada() {
		return tipoProvaSelecionada.get();
	}
	
	public ObservableList<TipoProva> getTipoProvas() {
		return tipoProvas;
	}
	
	
	public void setTipoProvaSelecionada(TipoProva tipo) {
		tipoProvaSelecionada.set(tipo);
	}
	
	public Periodicidade getPeriodicidadeSelecionada() {
		return periodicidadeSelecionada.get();
	}
	
	public ObservableList<Periodicidade> getPeriodicidades() {
		return periodicidades;
	}
	
	public void setPeriodicidadeSelecionada(Periodicidade periodicidade) {
		periodicidadeSelecionada.set(periodicidade);
	}
	
	public void clearProperties() {
		designacao.set("");
		tipoProvaSelecionada.set(null);
		numParticipantes.set(0);
		numArbitrosPorEncontro.set(0);
		dataRealizacao.set(null);
		periodicidadeSelecionada.set(null);
	}

	public StringProperty designacaoProperty() {
		return designacao;
	}
	
	public String getDesignacao() {
		return designacao.get();
	}

	public IntegerProperty numParticipantesProperty() {
		return numParticipantes;
	}
	
	public int getNumParticipantes() {
		return numParticipantes.get();
	}
	
	public IntegerProperty numArbitrosPorEncontroProperty() {
		return numArbitrosPorEncontro;
	}
	
	public int getNumArbitrosPorEncontro() {
		return numArbitrosPorEncontro.get();
	}

	public ObjectProperty<LocalDate> getDataRealizacaoProperty() {
		return dataRealizacao;
	}
	
	public void setDataRealizacao(LocalDate data) {
		dataRealizacao.set(data);
	}
	
	public LocalDate getDataRealizacao() {
		return dataRealizacao.get();
	}
}
