package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.handlers.IProvaServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.NovaProvaController;
import presentation.fx.model.NovaProvaModel;

public class Startup extends Application {
    
	private static IProvaServiceRemote provaService;

	@Override 
    public void start(Stage stage) throws IOException {
		// This line to resolve keys against Bundle.properties
		// ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
    	FXMLLoader createProvaLoader = new FXMLLoader(getClass().getResource("/fxml/novaProva.fxml"), i18nBundle);
    	Parent root = createProvaLoader.load();
    	NovaProvaController novaProvaController = createProvaLoader.getController();    	
    	
    	NovaProvaModel novaProvaModel = new NovaProvaModel(provaService);
    	novaProvaController.setModel(novaProvaModel);
    	novaProvaController.setProvaService(provaService);
    	novaProvaController.setI18NBundle(i18nBundle);
    	
        Scene scene = new Scene(root, 600, 400);
       
        stage.setTitle(i18nBundle.getString("prova.title"));
        stage.setScene(scene);
        stage.show();
    }
	
	public static void startGUI(IProvaServiceRemote addProvaHandler) {
		Startup.provaService = addProvaHandler;
        launch();
	}
}
