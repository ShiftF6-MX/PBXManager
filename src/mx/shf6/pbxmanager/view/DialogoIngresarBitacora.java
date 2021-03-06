package mx.shf6.pbxmanager.view;

import java.sql.Connection;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mx.shf6.pbxmanager.MainApp;
import mx.shf6.pbxmanager.model.Usuario;
import mx.shf6.pbxmanager.model.dao.UsuarioDAO;
import mx.shf6.pbxmanager.utilities.Notificacion;

public class DialogoIngresarBitacora {

	//PROPIEDADES
	private MainApp mainApp;
	private Connection connetion;
	private Usuario usuario;

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
		this.usuario = new Usuario();
	}//FIN METODO

	private boolean validarCampos() {
		if (this.campoTextoNombre.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "�Debes introducir un nombre de usuario!");
			return false;
		} else if (this.campoTextoPIN.getText().isEmpty()) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "�Debes introducir tu PIN!");
			return false;
		}//FIN IF/ELSE
		return true;
	}//FIN METODO

	private void validarUsuario() {
		if(validarCampos()){
			int accesoUsuario = UsuarioDAO.validarUsuario(this.connetion, campoTextoNombre.getText(), campoTextoPIN.getText());
			if(accesoUsuario == UsuarioDAO.ACCESO_CORRECTO){
				this.usuario = UsuarioDAO.readPorNombreUsuario(connetion, campoTextoNombre.getText());
				this.mainApp.setUsuario(this.usuario);
				this.mainApp.getEscenarioDos().close();
			}else if (accesoUsuario == UsuarioDAO.CONRASENA_INCORRECTA)
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "�Contrase�a Incorrecta!");
			else if (accesoUsuario == UsuarioDAO.NO_REGISTRADO)
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "�Usuario no Registrado!");
			else if (accesoUsuario == UsuarioDAO.USUARIO_BLOQUEADO)
				Notificacion.dialogoAlerta(AlertType.ERROR, "", "�Usuario Bloqueado!");
		}//FIN IF
	}//FIN METODO

	//MANEJADORES COMPONENTES
	@FXML private void vmanejadorBotonAceptar() {
		validarUsuario();
	}//FIN METODO

	@FXML private void manejadorBotonCerrar() {
		this.mainApp.getEscenarioDos().close();
		this.mainApp.getEscenarioUno().close();
	}//FIN METODO

}//FIN CLASE
