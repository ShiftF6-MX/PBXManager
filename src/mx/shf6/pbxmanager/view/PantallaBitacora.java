package mx.shf6.pbxmanager.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import mx.shf6.pbxmanager.MainApp;
import mx.shf6.pbxmanager.model.Bitacora;
import mx.shf6.pbxmanager.model.GrupoUsuario;
import mx.shf6.pbxmanager.model.dao.BitacoraDAO;
import mx.shf6.pbxmanager.model.dao.Seguridad;
import mx.shf6.pbxmanager.utilities.Notificacion;
import mx.shf6.pbxmanager.utilities.PTableColumn;


public class PantallaBitacora  extends Thread{
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection conexion;
	private ArrayList<Bitacora> listaBitacora;
	private String extensionOrigen;
	private Boolean opcion;
	private File file;
	
	//VARIABLES
	private String RUTA = "resources/";
	private String status;
		
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
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.conexion = this.mainApp.getConnection();
		
		extensionOrigen = this.mainApp.getUsuario().getExtension();
		opcion = true;
		if (this.mainApp.getUsuario().getGrupoUsuarioFK() == GrupoUsuario.ADMINISTRADOR) {
			this.extensionOrigen = "";
			this.opcion = false;
		}//FIN IF
			
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
		
		status = this.botonMenuStatus.getText();
		if (status.equals("TODOS"))
			status = "";
		
		this.listaBitacora = BitacoraDAO.readTodos(this.conexion, status, Date.valueOf(this.datePickerFechaInicio.getValue()), Date.valueOf(this.datePickerFechaFinal.getValue()), extensionOrigen, this.campoTextoBuscar.getText());
		this.tablaBitacora.setItems(FXCollections.observableArrayList(listaBitacora));
	}// FIN METODO
	
	@Override
	public void run() {
		try {			
			while(this.opcion) {
				updateTabla();
				for(Bitacora cdr : this.listaBitacora) {
					if (/*cdr.getSRC().equals("200") && */cdr.getDisposition().equals("ANSWERED") && cdr.getComentario().equals("")) {
						Platform.runLater(() -> {
							cdr.setComentario(this.mainApp.openDialogoComentarios(cdr));
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
	
	private void exportarExcel() throws SQLException {
		ResultSet resultados = BitacoraDAO.readTodosResultSet(this.conexion, status, Date.valueOf(this.datePickerFechaInicio.getValue()), Date.valueOf(this.datePickerFechaFinal.getValue()), extensionOrigen, this.campoTextoBuscar.getText());
		this.file = new File (this.RUTA + resultados.getMetaData().getTableName(1) + ".xls");
		int row = 0;
		//FORMATO FUENTE DEL CONTENIDO
		WritableFont fuente = new WritableFont( WritableFont.ARIAL, 8, WritableFont.NO_BOLD );
		WritableCellFormat formatoCelda = new WritableCellFormat(fuente);
		
		WritableFont fTitulo = new WritableFont( WritableFont.ARIAL, 9, WritableFont.BOLD );
		WritableCellFormat formatoCelda2 = new WritableCellFormat(fTitulo);
		
		//INTERFAZ PARA LA HOJA DE CALCULO
		WritableSheet hojaExcel =  null;
		WritableWorkbook libro = null;
		
		//CONFIGURACION PARA GENERAR LA HOJA DE CALCULO
		WorkbookSettings configuracion = new WorkbookSettings();
		configuracion.setLocale(new Locale("es", "MX"));
		
		try {
			libro = Workbook.createWorkbook(file, configuracion);
			//HOJA CON NOMBRE DE LA TABLA
			libro.createSheet("Consulta", 0);
			hojaExcel = libro.getSheet(0);
			
				
			try {
				Label fechaTitulo = new Label(1, 0, "Fecha", formatoCelda2);
				Label origenTitulo = new Label(2, 0, "Origen", formatoCelda2);
				Label usuarioTitulo = new Label(3, 0, "Usuario", formatoCelda2);
				Label destinoTitulo = new Label(4, 0, "Destino", formatoCelda2);
				Label duracionTitulo = new Label(5, 0, "Tiempo (s)", formatoCelda2);
				Label statusTitulo = new Label(6, 0, "Status", formatoCelda2);
				Label idTitulo = new Label(7, 0, "UNIQ ID", formatoCelda2);
				Label comentarioTitulo = new Label(8, 0, "Comentarios", formatoCelda2);
				try {
					hojaExcel.addCell(fechaTitulo);
					hojaExcel.addCell(origenTitulo);
					hojaExcel.addCell(usuarioTitulo);
					hojaExcel.addCell(destinoTitulo);
					hojaExcel.addCell(duracionTitulo);
					hojaExcel.addCell(statusTitulo);
					hojaExcel.addCell(idTitulo);
					hojaExcel.addCell(comentarioTitulo);
				}catch (WriteException ex) {
					Notificacion.dialogoException(ex);
				}//FIN TRY-CATCH
				
			
				while (resultados.next()) {
					for (int i = 1; i <= resultados.getMetaData().getColumnCount(); i++) {
						Label registro = new Label (i, row + 1, resultados.getString(i), formatoCelda);
						try {
							hojaExcel.addCell(registro);
						}catch (WriteException ex) {
						Notificacion.dialogoException(ex);
						}//FIN TRY-CATCH
					}//FIN FOR
					row ++;
				}//FIN WHILE
				resultados.close();
			}catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY/CATCH
		
			//GUARDANDO EN LA RUTA
			try {
				libro.write();
				libro.close();
				Desktop.getDesktop().open(file);
			}catch (WriteException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY-CATCH
		
		}catch (IOException ex) {
			Notificacion.dialogoAlerta(AlertType.WARNING, "", "EL archivo se encuentra actualmente abierto, ciérralo para poder exportar");
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
	
	@FXML private void manejadorBotonExportarExcel() {
		if(Seguridad.verificarAcceso(mainApp.getConnection(), mainApp.getUsuario().getGrupoUsuarioFK(), "cReporte")) {
			try {
				this.exportarExcel();
			} catch (SQLException ex) {
				Notificacion.dialogoException(ex);
			}//FIN TRY-CATCH
		}else
			Notificacion.dialogoAlerta(AlertType.WARNING, "Error", "No tienes permiso para realizar esta acción.");		
	}//FIN MANEJADOR
	
}//FIN CLASE
