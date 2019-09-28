package mx.shf6.pbxmanager;

import java.io.IOException;
import java.sql.Connection;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import mx.shf6.pbxmanager.model.Usuario;
import mx.shf6.pbxmanager.utilities.ConnectionDB;
import mx.shf6.pbxmanager.utilities.LeerArchivo;
import mx.shf6.pbxmanager.utilities.Notificacion;
import mx.shf6.pbxmanager.view.DialogoIngresarBitacora;
import mx.shf6.pbxmanager.view.DialogoUsuario;
import mx.shf6.pbxmanager.view.PantallaBitacora;
import mx.shf6.pbxmanager.view.PantallaMenu;
import mx.shf6.pbxmanager.view.PantallaUsuario;

public class MainApp extends Application {

	//PROPIEDADES
	private Connection conexion;
	private ConnectionDB conexionDB;
	private Usuario usuario;

	//ESCENARIOS DE SISTEMA
	private Stage escenarioUno;
	private Stage escenarioDos;

	//PANTALLAS DEL SISTEMA
	private BorderPane pantallaBase;
	private AnchorPane pantallaMenu;
	private AnchorPane pantallaCDR;
	private AnchorPane pantallaUsuario;

	//DIALOGOS DEL SISTEMA
	private AnchorPane dialogoIngresarBitacora;
	private AnchorPane dialogoUsuario;

	@Override
	public void start(Stage primaryStage) {
		this.loadFuentes();
		this.configConexionBD();
		this.configEscenarioUno(primaryStage);
		this.configEscenarioDos();
		this.initEscenarioUno();
		this.openPantallaMenu();
		this.openDialogoIngresarBitacora();
	}//FIN METODO

	private void loadFuentes() {
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Light.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Medium.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Bold.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Black.ttf").toExternalForm(), 10);
	}//FIN METODO

	private void configConexionBD() {
		LeerArchivo.leerArchivo();
		this.conexionDB = new ConnectionDB(LeerArchivo.nameDB, LeerArchivo.hostDB, LeerArchivo.userDB, LeerArchivo.passwordDB);
		this.conexion = conexionDB.conectarMySQL();
		if (this.conexion == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "Conectar base de datos", "Error al conectar con la base de datos");
			System.exit(0);
		}//FIN IF
	}//FIN METODO	

	public Connection getConnection() {
		return this.conexion;
	}//FIN METODO
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}//FIN METODO
	
	public Usuario getUsuario() {
		return this.usuario;
	}//FIN METODO

	private void configEscenarioUno(Stage escenarioUno) {
		this.escenarioUno = escenarioUno;
		this.escenarioUno.setTitle("PBXManager | Herramienta para la gestión de PBX Issabel");
		this.escenarioUno.getIcons().add(new Image(MainApp.class.getResource("LogoKey.png").toString()));
		this.escenarioUno.initStyle(StageStyle.DECORATED);
		this.escenarioUno.setMaximized(true);
		this.escenarioUno.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				boolean opcion = Notificacion.dialogoPreguntar("", "Estas a punto de salir del sistema...\n ¿Deseas cerrar la aplicación?");
				if (opcion) {
					conexionDB.terminarConexion(conexion);
					Platform.exit();
					System.exit(0);
				} else
					event.consume();
			}//FIN METODO INTERNO
			
		});//FIN SENTENCIA
	}//FIN METODO

	public Stage getEscenarioUno() {
		return this.escenarioUno;
	}//FIN METODO
	
	public void initEscenarioUno() {
		FXMLLoader cargadorFXML = new FXMLLoader();
		cargadorFXML.setLocation(MainApp.class.getResource("view/PantallaBase.fxml"));
		
		try {
			this.pantallaBase = (BorderPane) cargadorFXML.load();
		} catch (IOException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
		
		Scene escenaUno = new Scene(this.pantallaBase);
		this.escenarioUno.setScene(escenaUno);
		this.escenarioUno.show();
	}//FIN METODO
	
	private void configEscenarioDos() {
		//INICIA ESCENARIO DIALOGOS
		this.escenarioDos = new Stage();
		this.escenarioDos.setResizable(false);
		this.escenarioDos.setMaximized(false);
		this.escenarioDos.initModality(Modality.WINDOW_MODAL);
		this.escenarioDos.initStyle(StageStyle.TRANSPARENT);
		this.escenarioDos.initOwner(this.escenarioUno);
	}//FIN METODO

	public Stage getEscenarioDos() {
		return this.escenarioDos;
	}//FIN METODO
	
	private Scene initEscenarioDos(Parent parent) {
		VBox marcoVentana = new VBox();
		marcoVentana.getChildren().add(parent);
		marcoVentana.setPadding(new Insets(10.0d));
		marcoVentana.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0,0), new CornerRadii(0), new Insets(0))));
		parent.setEffect(new DropShadow());
		((AnchorPane) parent).setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0,0), new CornerRadii(0), new Insets(0))));
		Scene escena = new Scene(marcoVentana);
		escena.setFill(Color.TRANSPARENT);
		return escena;
	}//FIN METODO

	public void openPantallaMenu() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaMenu.fxml"));
			this.pantallaMenu = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(null);
			this.pantallaBase.setCenter(this.pantallaMenu);

			PantallaMenu pantallaMenu = fxmlLoader.getController();
			pantallaMenu.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO

	public void openPantallaCDR(String cdrOrigen) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaBitacora.fxml"));
			this.pantallaCDR = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(null);
			this.pantallaBase.setCenter(this.pantallaCDR);
			
			PantallaBitacora pantallaCDR = fxmlLoader.getController();
			pantallaCDR.setMainApp(this, cdrOrigen);
			pantallaCDR.start();
		}catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
	}//FIN METODO
	
	public void openPantallaUsuario() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaUsuario.fxml"));
			this.pantallaUsuario = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(null);
			this.pantallaBase.setCenter(this.pantallaUsuario);
			
			PantallaUsuario pantallaUsuario = fxmlLoader.getController();
			pantallaUsuario.setMainApp(this);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void openDialogoIngresarBitacora() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoIngresarBitacora.fxml"));
			this.dialogoIngresarBitacora = (AnchorPane) fxmlLoader.load();

			Scene escenaDialogoIngresarBitacora = this.initEscenarioDos(this.dialogoIngresarBitacora);
			this.escenarioDos.setScene(escenaDialogoIngresarBitacora);

			DialogoIngresarBitacora dialogoIngresarBitacora = fxmlLoader.getController();
			dialogoIngresarBitacora.setMainApp(this);

			this.escenarioDos.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	public void openDialogoUsuario(Usuario usuario, int opcion) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoUsuario.fxml"));
			this.dialogoUsuario = (AnchorPane) fxmlLoader.load();
			
			Scene escenaDialogoUsuario = this.initEscenarioDos(this.dialogoUsuario);
			this.escenarioDos.setScene(escenaDialogoUsuario);
			
			DialogoUsuario dialogoUsuario = fxmlLoader.getController();
			dialogoUsuario.setMainApp(this, usuario, opcion);
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO

	public static void main(String[] args) {
		launch(args);
	}//FIN METODO

}//FIN CLASE
