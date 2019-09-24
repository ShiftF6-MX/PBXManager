package mx.shf6.pbxmanager.view;

import javafx.fxml.FXML;
import mx.shf6.pbxmanager.MainApp;

public class PantallaCabecera {

	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES INTERFAZ 
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
		
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.stop();
	}//FIN METODO
	
	@FXML private void manejadorBotonMinimizar() {
		this.mainApp.minimizar();
	}//FIN METODO
	
}//FIN CLASE
