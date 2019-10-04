package mx.shf6.pbxmanager.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.pbxmanager.MainApp;
import mx.shf6.pbxmanager.model.dao.Seguridad;
import mx.shf6.pbxmanager.utilities.Notificacion;

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
		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFK(), "rUsuarios")) {
			this.mainApp.openPantallaUsuario();	
		}else
			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		
	}//FIN METODO
	
}//FIN CLASE
