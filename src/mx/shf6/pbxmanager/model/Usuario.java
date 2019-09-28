package mx.shf6.pbxmanager.model;

import java.sql.Connection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.shf6.pbxmanager.model.dao.GrupoUsuarioDAO;

public class Usuario {

	//PROPIEDADES
	private ObjectProperty<Integer> sysPK;
	private StringProperty usuario;
	private StringProperty pin;
	private StringProperty extension;
	private ObjectProperty<Integer> status;
	private ObjectProperty<Integer> grupoUsuarioFK;
	//CONSTANTES
	public static final int BLOQUEADO = 0;
	public static final int ACTIVO = 1;
	public static final int BAJA = 2;

	//CONSTRUCTOR SIN PARAMETROS
	public Usuario() {
		this(-1, "", "", "", -1, -1);
	}//FIN CONSTRUCTOR

	//CONSTRUCTOR CON PARAMETROS
	public Usuario(int sysPK, String usuario, String pin, String extension, int status, int grupoUsuarioFK) {
		this.sysPK = new SimpleObjectProperty<Integer>(sysPK);
		this.usuario = new SimpleStringProperty(usuario);
		this.pin = new SimpleStringProperty(pin);
		this.extension = new SimpleStringProperty(extension);
		this.status = new SimpleObjectProperty<Integer>(status);
		this.grupoUsuarioFK = new SimpleObjectProperty<Integer>(grupoUsuarioFK);
	}//FIN CONSTRUCTOR

	//METODOS PARA ACCESO A "SYSPK"
	public void setSysPK(Integer sysPK) {
		this.sysPK.set(sysPK);
	}//FIN METODO

	public Integer getSysPK() {
		return this.sysPK.get();
	}//FIN METODO

	public ObjectProperty<Integer> sysPKProperty() {
		return this.sysPK;
	}//FIN METODO
	//FINT METODOS "SYSPK"

	//METODOS PARA ACCESO A "USUARIO"
	public void setUsuario(String usuario) {
		this.usuario.set(usuario);
	}//FIN METODO

	public String getUsuario() {
		return this.usuario.get();
	}//FIN METODO

	public StringProperty usuarioProperty() {
		return this.usuario;
	}//FIN METODO
	//FIN METODOS "USUARIO"
	
	//METODOS PARA ACCESO A "PIN"
	public void setPin(String pin) {
		this.pin.set(pin);
	}//FIN METODO

	public String getPin() {
		return this.pin.get();
	}//FIN METODO

	public StringProperty pinProperty() {
		return this.pin;
	}//FIN METODO
	
	//METODOS PARA ACCESO A "EXTENSION"
	public void setExtension(String extension) {
		this.extension.set(extension);
	}//FIN METODO

	public String getExtension() {
		return this.extension.get();
	}//FIN METODO

	public StringProperty extensionProperty() {
		return this.extension;
	}//FIN METODO
	
	//METODOS PARA ACCESO A "STATUS"
	public void setStatus(Integer status) {
		this.status.set(status);
	}//FIN METODO

	public Integer getStatus() {
		return this.status.get();
	}//FIN METODO

	public ObjectProperty<Integer> statusProperty() {
		return this.status;
	}//FIN METODO

	public String getDescripcionStatus() {
		switch (this.getStatus()) {
		case 0:
			return "Bloqueado";
		case 1:
			return "Activo";
		case 2:
			return "Baja";
		}//FIN WTITCH
		return "";
	}//FIN METODO

	public void setNumeroStatus(String status) {
		switch (status) {
		case "Bloqueado":
			this.setStatus(0);
			break;
		case "Activo":
			this.setStatus(1);
			break;
		case "Baja":
			this.setStatus(2);
			break;
		}//FIN WTITCH
	}//FIN METODO

	public StringProperty descripcionStatusProperty() {
		switch (this.getStatus()) {
		case 0:
			return new SimpleStringProperty("Bloqueado");
		case 1:
			return new SimpleStringProperty("Activo");
		case 2:
			return new SimpleStringProperty("Baja");
		}//FIN WTITCH
		return new SimpleStringProperty();
	}//FIN METODO
	//FIN METODO "STATUS"
	
	public void setGrupoUsuarioFK(int grupoUsuarioFK) {
		this.grupoUsuarioFK.set(grupoUsuarioFK);
	}//FIN METODO
	
	public int getGrupoUsuarioFK() {
		return this.grupoUsuarioFK.get();
	}//FIN METODO
	
	public ObjectProperty<Integer> grupoUsuarioFKProperty(){
		return this.grupoUsuarioFK;
	}//FIN METODO
	
	public GrupoUsuario getGrupoUsuario(Connection connection) {
		return GrupoUsuarioDAO.readPorSysPK(connection, this.getGrupoUsuarioFK());
	}//FIN METODO
	
}//FIN CLASE
