package mx.shf6.pbxmanager.view;

import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.pbxmanager.MainApp;
import mx.shf6.pbxmanager.model.Usuario;
import mx.shf6.pbxmanager.model.dao.UsuarioDAO;
import mx.shf6.utilities.Notificacion;

public class DialogoIngresarBitacora {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection connetion;

	//VARIABLES
	Double cantidadProgramar;
	Double existencia;
	Double cantidadPorSalir;

	//COMPONENTES INTERFAZ
	@FXML private TextField campoTextoNombre;
	@FXML private PasswordField campoTextoPIN;

	//INICIA COMPONENTES INTERFAZ USUARIO
	@FXML private void initialize() {
	}//FIN METODO

	//ACCESO CLASE PRINCIPAL
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connetion = this.mainApp.getConnection();
	}//FIN METODO

	private boolean validarCampos() {
		if (this.campoTextoNombre.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "¡Debes introducir un nombre de usuario!");
			return false;
		} else if (this.campoTextoPIN.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "¡Debes introducir tu PIN!");
			return false;
		}//FIN IF/ELSE
		return true;
	}//FIN METODO

	private void validarUsuario() {
		if(validarCampos()){
			int accesoUsuario = UsuarioDAO.validarUsuario(this.connetion, campoTextoNombre.getText(), campoTextoPIN.getText());
			if(accesoUsuario == UsuarioDAO.ACCESO_CORRECTO){
				Usuario usuario = new Usuario();
				usuario.setUsuario(campoTextoNombre.getText());
				usuario.setPin(campoTextoPIN.getText());
				this.mainApp.setUsuario(usuario);
				this.mainApp.getEscenarioDos().close();
			}else if (accesoUsuario == UsuarioDAO.CONRASENA_INCORRECTA)
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "¡Contraseña Incorrecta!");
			else if (accesoUsuario == UsuarioDAO.NO_REGISTRADO)
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "¡Usuario no Registrado!");
			else if (accesoUsuario == UsuarioDAO.USUARIO_BLOQUEADO)
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "¡Usuario Bloqueado!");
		}//FIN IF
	}//FIN METODO

	//MANEJADORES COMPONENTES
	@FXML private void vmanejadorBotonAceptar() {
		validarUsuario();
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDos().close();
	}//FIN METODO

}//FIN CLASE
