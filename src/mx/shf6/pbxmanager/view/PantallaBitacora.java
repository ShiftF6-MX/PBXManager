package mx.shf6.pbxmanager.view;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mx.shf6.pbxmanager.MainApp;
import mx.shf6.pbxmanager.model.Bitacora;
import mx.shf6.pbxmanager.model.dao.BitacoraDAO;
import mx.shf6.pbxmanager.utilities.Notificacion;
import mx.shf6.pbxmanager.utilities.PTableColumn;


public class PantallaBitacora  extends Thread{
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ArrayList<Bitacora> listaBitacora;
	private String cdrOrigen;
		
	//COMPONENTES DE LA INTERFAZ
	@FXML private MenuButton botonMenuStatus;
	@FXML private ToggleGroup toggleGrupoMenus;
	@FXML private DatePicker datePickerFechaInicio;
	@FXML private DatePicker datePickerFechaFinal;
	@FXML private TextField campoTextoBuscar;
	@FXML private TableView<Bitacora> tablaBitacora;	
	@FXML private PTableColumn<Bitacora,Date> columnaFecha;
	@FXML private PTableColumn<Bitacora, String> columnaClid;
	@FXML private PTableColumn<Bitacora, String> columnaSRC;
	@FXML private PTableColumn<Bitacora, String> columnaDST;
	@FXML private PTableColumn<Bitacora, String> columnaDContext;
	@FXML private PTableColumn<Bitacora, String> columnaChannel;
	@FXML private PTableColumn<Bitacora, String> columnaDSTChannel;
	@FXML private PTableColumn<Bitacora, String> columnaLastApp;
	@FXML private PTableColumn<Bitacora, String> columnaLastData;
	@FXML private PTableColumn<Bitacora, Integer> columnaDuration;
	@FXML private PTableColumn<Bitacora, Integer> columnaBillSec;
	@FXML private PTableColumn<Bitacora, String> columnaDisposition;
	@FXML private PTableColumn<Bitacora, Integer> columnaAmaFlags;
	@FXML private PTableColumn<Bitacora, String> columnaUniqueid;
	@FXML private PTableColumn<Bitacora, String> columnaCnum;
	@FXML private PTableColumn<Bitacora, String> columnaCnam;
	@FXML private PTableColumn<Bitacora, String> columnaComentarios;
	
	//METODOS	
	@FXML private void initialize() {
		this.initComponentes();
		this.loadMenu();
	}//FIN METODO
	
	//METODO SET MAIN
	public void setMainApp(MainApp mainApp, String cdrOrigen) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		this.cdrOrigen = cdrOrigen;
		this.listaBitacora = new ArrayList<Bitacora>();
		this.initTabla();
		this.updateTabla();	
	}//FIN METODO
	
	private void initComponentes() {
		campoTextoBuscar.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					updateTabla();
				}//FIN IF
			}//FIN METODO
		});//FIN SENTENCIA
		
		this.datePickerFechaInicio.setEditable(false);
		this.datePickerFechaInicio.setValue(new Date(System.currentTimeMillis()).toLocalDate());
		this.datePickerFechaInicio.setOnAction(e -> this.updateTabla());

		this.datePickerFechaFinal.setEditable(false);
		this.datePickerFechaFinal.setValue(new Date(System.currentTimeMillis()).toLocalDate());
		this.datePickerFechaFinal.setOnAction(e-> this.updateTabla());
	}//FIN METODO
	
	private void loadMenu() {
		this.toggleGrupoMenus = new ToggleGroup();	
		
		RadioMenuItem menuContestado = new RadioMenuItem(Bitacora.CONTESTADO);
		menuContestado.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				botonMenuStatus.setText(Bitacora.CONTESTADO);
				updateTabla();
			}//FIN METODO
		});//FIN SENTENCIA
		menuContestado.setToggleGroup(toggleGrupoMenus);

		RadioMenuItem menuNoContestado = new RadioMenuItem(Bitacora.NO_CONTESTADO);
		menuNoContestado.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				botonMenuStatus.setText(Bitacora.NO_CONTESTADO);
				updateTabla();
			}//FIN METODO
		});//FIN SENTENCIA
		menuNoContestado.setToggleGroup(toggleGrupoMenus);

		RadioMenuItem menuOcupado = new RadioMenuItem(Bitacora.OCUPADO);
		menuOcupado.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				botonMenuStatus.setText(Bitacora.OCUPADO);
				updateTabla();
			}//FIN METODO
		});//FIN SENTENCIA
		menuOcupado.setToggleGroup(toggleGrupoMenus);

		RadioMenuItem menuTodos = new RadioMenuItem("TODOS");
		menuTodos.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				botonMenuStatus.setText("TODOS");
				updateTabla();
			}//FIN METODO
		});//FIN SENTENCIA
		menuTodos.setSelected(true);
		menuTodos.setToggleGroup(toggleGrupoMenus);

		botonMenuStatus.getItems().add(menuContestado);
		botonMenuStatus.getItems().add(menuNoContestado);
		botonMenuStatus.getItems().add(menuOcupado);
		botonMenuStatus.getItems().add(menuTodos);
		botonMenuStatus.setText("TODOS");
	}//FIN METODO
	
	private void initTabla() {
		this.columnaFecha.setCellValueFactory(CellData -> CellData.getValue().callDateProperty());
		this.columnaClid.setCellValueFactory(CellData -> CellData.getValue().clidProperty());
		this.columnaSRC.setCellValueFactory(CellData -> CellData.getValue().SCRProperty());
		this.columnaDST.setCellValueFactory(CellData -> CellData.getValue().dstProperty());
		this.columnaDContext.setCellValueFactory(CellData -> CellData.getValue().dcontectProperty());
		this.columnaChannel.setCellValueFactory(CellData -> CellData.getValue().channelProperty());
		this.columnaDSTChannel.setCellValueFactory(CellData -> CellData.getValue().dstChannelProperty());
		this.columnaLastApp.setCellValueFactory(CellData -> CellData.getValue().lastAppProperty());
		this.columnaLastData.setCellValueFactory(CellData -> CellData.getValue().lastDataProperty());
		this.columnaDuration.setCellValueFactory(CellData -> CellData.getValue().durationProperty());
		this.columnaBillSec.setCellValueFactory(CellData -> CellData.getValue().billsecProperty());
		this.columnaDisposition.setCellValueFactory(CellData -> CellData.getValue().dispositionProperty());
		this.columnaAmaFlags.setCellValueFactory(CellData -> CellData.getValue().amaflagsProperty());
		this.columnaUniqueid.setCellValueFactory(CellData -> CellData.getValue().uniqueidProperty());
		this.columnaCnum.setCellValueFactory(CellData -> CellData.getValue().cnumProperty());
		this.columnaCnam.setCellValueFactory(CellData -> CellData.getValue().cnamProperty());
		this.columnaComentarios.setCellValueFactory(cellData -> cellData.getValue().comentarioProperty());
	}//FIN METODO
	
	private void updateTabla() {
		//this.tablaBitacora.setItems(null);
		this.listaBitacora.clear();
		
		String status = this.botonMenuStatus.getText();
		if (status.equals("TODOS"))
			status = "";
		
		this.listaBitacora = BitacoraDAO.leerTodos(this.conexion, status, Date.valueOf(this.datePickerFechaInicio.getValue()), Date.valueOf(this.datePickerFechaFinal.getValue()), this.cdrOrigen, this.campoTextoBuscar.getText());
		this.tablaBitacora.setItems(FXCollections.observableArrayList(listaBitacora));
	}// FIN METODO
	
	@Override
	public void run() {
		try {			
			while(true) {
				updateTabla();
				for(Bitacora cdr : this.listaBitacora) {
					if (/*cdr.getSRC().equals("200") && */cdr.getDisposition().equals("ANSWERED") && cdr.getComentario().equals("")) {
						Platform.runLater(() -> {
							cdr.setComentario(Notificacion.dialogoEntradaTexto("", "Ingrese sus comentarios", ""));
							if (BitacoraDAO.update(this.conexion, cdr)) {
							    synchronized (this) {
									this.notify();
								}//FIN SINCRONIZACION
							}//FIN IF
						});//FIN SENTENCIA
						synchronized (this) {
							this.wait();
						}//FIN SINCRONIZACION
					}//FIN IF
				}//FIN FOR
				Thread.sleep(2000);
			}//FIN WHILE
		}catch(InterruptedException ex) {
			Notificacion.dialogoException(ex);
		}//FIN TRY-CATCH
	}//FIN METODO
	
	//MANEJADORES
	
	@FXML private void manejadorBotonBuscar() {
		this.updateTabla();
	}//FIN MANEJADOR
	
	@FXML private void manejadorBotonActualizar() {
		this.updateTabla();
	}//FIN MANEJADOR
	
	@FXML private void manejadorBotonCerrar() {
		this.mainApp.openPantallaMenu();
	}//FIN METODO
	
}//FIN CLASE
