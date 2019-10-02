package mx.shf6.pbxmanager.view;

import javafx.fxml.FXML;
import mx.shf6.pbxmanager.MainApp;

public class PantallaMenu {
	
	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES
	
	//INICIA COMPONENTES INTERFAZ
	@FXML private void initialize() {
		
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void manejadorBotonBitacoraLlamadas() {
		this.mainApp.openPantallaCDR();
	}//FIN METODO
	
	@FXML private void manejadorBotonUsuarios() {
		this.mainApp.openPantallaUsuario();
	}//FIN METODO
	
}//FIN CLASE
