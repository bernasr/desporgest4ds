package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;

import facade.exceptions.ApplicationException;
import facade.handlers.IProvaServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.NovaProvaModel;
import presentation.fx.model.Periodicidade;
import presentation.fx.model.TipoProva;

public class NovaProvaController extends BaseController{

    @FXML
    private DatePicker dataRealizacao;

    @FXML
    private TextField designacao;

    @FXML
    private ComboBox<TipoProva> tipoDaProva;

    @FXML
    private TextField numParticipantes;

    @FXML
    private TextField numArbitrosPorEncontro;

    @FXML
    private ComboBox<Periodicidade> periodicidade;
    
    private NovaProvaModel model;
    
    private IProvaServiceRemote provaService;
    
    public void setProvaService(IProvaServiceRemote provaService) {
    	this.provaService = provaService;
    }
    
    public void setModel(NovaProvaModel model) {
    	this.model = model;
    	
    	designacao.textProperty().bindBidirectional(model.designacaoProperty());
    	numParticipantes.textProperty().bindBidirectional(model.numParticipantesProperty(), new NumberStringConverter());
    	numArbitrosPorEncontro.textProperty().bindBidirectional(model.numArbitrosPorEncontroProperty(), new NumberStringConverter());
    	
    	tipoDaProva.setItems(model.getTipoProvas());
    	tipoDaProva.setValue(model.getTipoProvaSelecionada());
    	
    	periodicidade.setItems(model.getPeriodicidades());
    	periodicidade.setValue(model.getPeriodicidadeSelecionada());
    	
    	dataRealizacao.valueProperty().bindBidirectional(model.getDataRealizacaoProperty());
    }
    
    @FXML
    private void initialize() {
    	UnaryOperator<Change> integerFilter = change -> {
    		String newText = change.getControlNewText();
    		if (newText.matches("[0-9]*"))
    			return change;
    		else
    			return null;
    	};
    	
    	numParticipantes.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
    	numArbitrosPorEncontro.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
    }

    @FXML
    public void tipoDaProvaSelecionado(ActionEvent event) {
    	model.setTipoProvaSelecionada(tipoDaProva.getValue());
    }

    @FXML
    public void criarProvaAction(ActionEvent event) {
    	String erro = validarInput();
    	
    	if (erro.length() == 0) {
    		try {
				provaService.criarProva(
						model.getTipoProvaSelecionada().getIndex(), 
						model.getDesignacao(),
						model.getNumParticipantes(),
						model.getNumArbitrosPorEncontro(),
						model.getDataRealizacao().toString(),
						model.getPeriodicidadeSelecionada().getIndex());
				model.clearProperties();
				showInfo(i18nBundle.getString("nova.prova.sucesso"));
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("nova.prova.erro.adicionar") + ":\n" + e.getMessage());
			}
    	} else {
    		showError(i18nBundle.getString("nova.prova.erro.adicionar") + ":\n" + erro);
    	}
    }

    private String validarInput() {
		StringBuilder sb = new StringBuilder();
		String designacao = model.getDesignacao();
		if (designacao == null || designacao.length() == 0)
			sb.append(i18nBundle.getString("nova.prova.designacao.invalida"));
		if (model.getNumParticipantes() <= 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("nova.prova.numPart.invalido"));
		}
		
		if (model.getNumArbitrosPorEncontro() <= 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("nova.prova.numArb.invalido"));
		}
		
		if (model.getPeriodicidadeSelecionada() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("nova.prova.periodicidade.invalida"));
		}
		
		if (model.getTipoProvaSelecionada() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("nova.prova.tipoProva.invalida"));
		}
		
		if (model.getDataRealizacao() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("nova.prova.dataRealizacao.invalida"));
		}
	
		return sb.toString();
	}

	@FXML
    public void dataRealizacaoAlterada(ActionEvent event) {
		model.setDataRealizacao(dataRealizacao.getValue());
    }

    @FXML
    public void periodicidadeSelecionada(ActionEvent event) {
    	model.setPeriodicidadeSelecionada(periodicidade.getValue());
    }
    
    

}
