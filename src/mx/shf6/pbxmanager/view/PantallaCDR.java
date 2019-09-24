package mx.shf6.pbxmanager.view;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import mx.shf6.pbxmanager.MainApp;
import mx.shf6.pbxmanager.model.CDR;
import mx.shf6.pbxmanager.model.dao.CDRDao;
import mx.shf6.pbxmanager.utilities.Notificacion;
import mx.shf6.pbxmanager.utilities.PTableColumn;


public class PantallaCDR  extends Thread{
	
	//PROPIEDADES
	private MainApp mainApp;
	private Connection connection;
	private ArrayList<CDR> listaCDR;
		
	//COMPONENTES DE LA INTERFAZ
	@FXML private TableView<CDR> tablaCDR;
	
	@FXML private PTableColumn<CDR,Date> columnaFecha;
	@FXML private PTableColumn<CDR, String> columnaClid;
	@FXML private PTableColumn<CDR, String> columnaSRC;
	@FXML private PTableColumn<CDR, String> columnaDST;
	@FXML private PTableColumn<CDR, String> columnaDContext;
	@FXML private PTableColumn<CDR, String> columnaChannel;
	@FXML private PTableColumn<CDR, String> columnaDSTChannel;
	@FXML private PTableColumn<CDR, String> columnaLastApp;
	@FXML private PTableColumn<CDR, String> columnaLastData;
	@FXML private PTableColumn<CDR, Integer> columnaDuration;
	@FXML private PTableColumn<CDR, Integer> columnaBillSec;
	@FXML private PTableColumn<CDR, String> columnaDisposition;
	@FXML private PTableColumn<CDR, Integer> columnaAmaFlags;
	@FXML private PTableColumn<CDR, String> columnaUniqueid;
	@FXML private PTableColumn<CDR, String> columnaCnum;
	@FXML private PTableColumn<CDR, String> columnaCnam;
	@FXML private PTableColumn<CDR, String> columnaComentarios;
	
	//METODOS	
	@FXML private void initialize() {

	}//FIN METODO
	
	//METODO SET MAIN
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.connection = this.mainApp.getConnection();
		this.listaCDR = CDRDao.leerTodos(connection);	
		inicializarTabla();
		//actualizarTabla();	
	}//FIN METODO
	
	@Override
	public void run() {
		try {			
			while(true) {
				actualizarTabla();
				for(CDR cdr : this.listaCDR) {
					if (/*cdr.getSRC().equals("200") && */cdr.getDisposition().equals("ANSWERED") && cdr.getComentario().equals("")) {
						Platform.runLater(() -> {
							cdr.setComentario(Notificacion.dialogoEntradaTexto("", "Ingrese sus comentarios", ""));
							if (CDRDao.update(this.connection, cdr)) {
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
	
	//METODO INICIALIZAR TABLA
	private void inicializarTabla() {
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
	
	
	private void actualizarTabla() {
		//this.tablaCDR.setItems(null);
		this.listaCDR.clear();
		this.listaCDR = CDRDao.leerTodos(this.connection);
		this.tablaCDR.setItems(FXCollections.observableArrayList(listaCDR));
	}// FIN METODO
	
	@FXML
	private void cerrarPantalla() {
		this.mainApp.iniciarPantallaMenu();
	}//FIN METODO
}//FIN CLASE
