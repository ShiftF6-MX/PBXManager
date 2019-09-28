package mx.shf6.pbxmanager.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mx.shf6.pbxmanager.MainApp;

public class DialogoIngresarBitacora {

	//PROPIEDADES
	private MainApp mainApp;
	
	//VARIABLES
	Double cantidadProgramar;
	Double existencia;
	Double cantidadPorSalir;
	
	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoExtension;
	@FXML private TextField campoTextoNombre;
	@FXML private PasswordField campoTextoPIN;
	
	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
	}//FIN METODO
	
	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}//FIN METODO
	
	//MANEJADORES COMPONENTES	
	@FXML private void vmanejadorBotonAceptar() {
		this.mainApp.openPantallaCDR();
		this.mainApp.getEscenarioDos().close();
	}//FIN METODO
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDos().close();
	}//FIN METODO
	
}//FIN CLASE
