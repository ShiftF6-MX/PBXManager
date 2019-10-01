package mx.shf6.pbxmanager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mx.shf6.pbxmanager.MainApp;

public class DialogoComentarios {

	//PROPIEDADES
	private MainApp mainApp;
	
	//COMPONENTES UI
	@FXML TextArea areaTextoComentarios;
	@FXML Label etiquetaTituloFormulario;
	
	//METODOS
	@FXML private void initialize() {
		this.areaTextoComentarios.clear();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, String unicoID) {
		this.mainApp = mainApp;
		this.etiquetaTituloFormulario.setText("CALL ID: " + unicoID);
	}//FIN METODO
	
	public String getComentarios() {
		return this.areaTextoComentarios.getText();
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		this.mainApp.getEscenarioDos().close();
	}//FIN MANEJADOR
	
}//FIN CLASE
