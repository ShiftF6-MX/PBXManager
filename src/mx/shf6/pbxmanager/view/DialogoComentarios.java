package mx.shf6.pbxmanager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import mx.shf6.pbxmanager.MainApp;

public class DialogoComentarios {

	//PROPIEDADES
	private MainApp mainApp;
	private String unicoID;
	
	//COMPONENTES UI
	@FXML TextArea areaTextoComentarios;
	@FXML Label etiquetaTituloFormulario;
	
	//METODOS
	@FXML private void initialize() {
		this.areaTextoComentarios.clear();
	}//FIN METODO
	
	public void setMainApp(MainApp mainApp, String unicoID, String destino) {
		this.mainApp = mainApp;
		this.unicoID = unicoID;
		this.etiquetaTituloFormulario.setText("CALL ID: " + unicoID + " | DESTINO: " + destino);
	}//FIN METODO
	
	public String getComentarios() {
		return this.areaTextoComentarios.getText();
	}//FIN METODO
	
	//MANEJADORES
	@FXML private void manejadorBotonAceptar() {
		this.mainApp.getEscenarioDos().close();
	}//FIN MANEJADOR
	
	@FXML private void manejadorCopiarUniqueID() {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent contenido = new ClipboardContent();
		contenido.putString(this.unicoID);
		clipboard.setContent(contenido);
	}//FIN MANEJADOR
	
}//FIN CLASE
