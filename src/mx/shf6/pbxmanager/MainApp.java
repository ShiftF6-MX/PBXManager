package mx.shf6.pbxmanager;

import java.io.IOException;
import java.sql.Connection;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mx.shf6.pbxmanager.utilities.ConnectionDB;
import mx.shf6.pbxmanager.utilities.LeerArchivo;
import mx.shf6.pbxmanager.utilities.Notificacion;
import mx.shf6.pbxmanager.view.DialogoIngresarBitacora;
import mx.shf6.pbxmanager.view.PantallaCDR;
import mx.shf6.pbxmanager.view.PantallaCabecera;
import mx.shf6.pbxmanager.view.PantallaMenu;

public class MainApp extends Application {

	//PROPIEDADES
	private Connection conexion;
	private ConnectionDB conexionBD;
	public String nombreImpresora;

	//VARIABLES
	private double xOffset = 0.0;
	private double yOffset = 0.0;

	//ESCENARIOS DE SISTEMA
	private Stage escenarioPrincipal;
	private Stage escenarioSecundario;

	//INTERFACES DEL SISTEMA
	private BorderPane pantallaBase;
	private AnchorPane pantallaCabecera;
	private AnchorPane pantallaMenu;
	private AnchorPane pantallaCDR;

	//DIALOGOS DEL SISTEMA
	private AnchorPane dialogoIngresarBitacora;

	@Override
	public void start(Stage primaryStage) {
		cargarFuentes();
		iniciarConexionBD();
		configurarEscenarioPrincipal(primaryStage);
		configurarEscenarioSecundario();
		iniciarEscenarioPrincipal();
		iniciarPantallaCabecera();
		iniciarPantallaMenu();
	}//FIN METODO

	private void cargarFuentes() {
		//INSTALACIÓN FUENTES
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Light.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Medium.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Bold.ttf").toExternalForm(), 10);
		Font.loadFont(MainApp.class.getResource("utilities/fonts/Roboto-Black.ttf").toExternalForm(), 10);
	}//FIN METODO

	private void iniciarConexionBD() {
		//INICIA CONCEXIÓN BASE DATOS
		//this.conexionBD = new ConnectionDB("demomr5","192.168.0.216", "conn01", "Simons83Mx");
		//this.conexionBD = new ConnectionDB("demomr5","25.64.166.16", "conn01", "Simons83Mx");
		LeerArchivo.leerArchivo();
		this.conexionBD = new ConnectionDB(LeerArchivo.nameDB, LeerArchivo.hostDB, LeerArchivo.userDB, LeerArchivo.passwordDB);
		this.conexion = conexionBD.conectarMySQL();
		if (this.conexion == null) {
			Notificacion.dialogoAlerta(AlertType.ERROR, "Conectar base de datos", "Error al conectar con la base de datos");
			System.exit(0);
		}//FIN IF
	}//FIN METODO

	private void configurarEscenarioPrincipal(Stage primaryStage) {
		//INICIA ESCENARIO PRINCIPAL
		this.escenarioPrincipal = primaryStage;
		this.escenarioPrincipal.setMaximized(true);
		this.escenarioPrincipal.setResizable(true);
		this.escenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
		this.escenarioPrincipal.setAlwaysOnTop(false);
	}//FIN METODO

	public Stage getEscenarioPrincipal() {
		return this.escenarioPrincipal;
	}//FIN METODO
	
	private void configurarEscenarioSecundario() {
		//INICIA ESCENARIO DIALOGOS
		this.escenarioSecundario = new Stage();
		this.escenarioSecundario.setResizable(false);
		this.escenarioSecundario.setMaximized(false);
		this.escenarioSecundario.initModality(Modality.WINDOW_MODAL);
		this.escenarioSecundario.initStyle(StageStyle.TRANSPARENT);
		this.escenarioSecundario.initOwner(this.escenarioPrincipal);
	}//FIN METODO

	public Stage getEscenarioSecundario() {
		return this.escenarioSecundario;
	}//FIN METODO

	private void iniciarEscenarioPrincipal() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaBase.fxml"));
			this.pantallaBase = (BorderPane) fxmlLoader.load();
			Scene escenaPrincipal = new Scene(this.pantallaBase);
			escenaPrincipal.setFill(Color.TRANSPARENT);
			this.escenarioPrincipal.setScene(escenaPrincipal);
			this.escenarioPrincipal.show();


			//CENTRAR VENTANA EN PANTALLA
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			this.escenarioPrincipal.setX(primaryScreenBounds.getMinX());
			this.escenarioPrincipal.setY(primaryScreenBounds.getMinY());

			this.escenarioPrincipal.setMaxWidth(primaryScreenBounds.getWidth());
			this.escenarioPrincipal.setMinWidth(primaryScreenBounds.getWidth());

			this.escenarioPrincipal.setMaxHeight(primaryScreenBounds.getHeight());
			this.escenarioPrincipal.setMinHeight(primaryScreenBounds.getHeight());

		} catch(IOException | IllegalStateException ex ) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO
	
	private Scene iniciarEscenarioSecundario(Parent parent) {
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
	
	private void iniciarPantallaCabecera() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaCabecera.fxml"));
			this.pantallaCabecera = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setTop(this.pantallaCabecera);

			PantallaCabecera pantallaCabecera = fxmlLoader.getController();
			pantallaCabecera.setMainApp(this);

			this.moverPantalla();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO

	public void iniciarPantallaMenu() {
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

	public void iniciarPantallaCDR() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/PantallaCDR.fxml"));
			this.pantallaCDR = (AnchorPane) fxmlLoader.load();
			this.pantallaBase.setCenter(null);
			this.pantallaBase.setCenter(this.pantallaCDR);
			
			PantallaCDR pantallaCDR = fxmlLoader.getController();
			pantallaCDR.setMainApp(this);
			pantallaCDR.start();
		}catch(IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
	}//FIN METODO
	
	public void iniciarDialogoIngresarBitacora() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(MainApp.class.getResource("view/DialogoIngresarBitacora.fxml"));
			this.dialogoIngresarBitacora = (AnchorPane) fxmlLoader.load();

			Scene escenaDialogoIngresarBitacora = this.iniciarEscenarioSecundario(this.dialogoIngresarBitacora);
			this.escenarioSecundario.setScene(escenaDialogoIngresarBitacora);

			DialogoIngresarBitacora dialogoIngresarBitacora = fxmlLoader.getController();
			dialogoIngresarBitacora.setMainApp(this);

			this.escenarioSecundario.showAndWait();
		} catch (IOException | IllegalStateException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY/CATCH
	}//FIN METODO

	public Connection getConnection() {
		return this.conexion;
	}//FIN METODO

	public String getNombreImpresora() {
		return nombreImpresora;
	}

	public void setNombreImpresora(String nombreImpresora) {
		this.nombreImpresora = nombreImpresora;
	}

	public static void main(String[] args) {
		launch(args);
	}//FIN METODO

	public void minimizar() {
		this.escenarioPrincipal.setIconified(true);
	}//FIN METODO

	private void moverPantalla() {
		//SELECCIONAR PANTALLA PARA MOVER
		this.pantallaCabecera.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = escenarioPrincipal.getX() - event.getScreenX();
				yOffset = escenarioPrincipal.getY() - event.getScreenY();
			}//FIN METODO
		});//FIN MOUSEHANDLER

		//MOVER VENTAN ARRASTRANDO
		this.pantallaCabecera.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				escenarioPrincipal.setX(event.getScreenX() + xOffset);
				escenarioPrincipal.setY(event.getScreenY() + yOffset);
			}//FIN METODO
		});//FIN MOUSEHANDLER
	}//FIN METODO

	@Override
	public void stop() {
		boolean opcion = Notificacion.dialogoPreguntar("", "Estas a punto de salir del sistema...\n ¿Deseas cerrar la aplicación?");
		if (opcion) {
			this.conexionBD.terminarConexion(this.getConnection());
			System.exit(0);
		}//FIN IF
	}//FIN METODO
}//FIN CLASE
