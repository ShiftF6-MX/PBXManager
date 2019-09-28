package mx.shf6.pbxmanager.model;

import java.sql.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CDR {
	
	//PROPIEDADES
	private ObjectProperty<Date> callDate;
	private StringProperty clid;
	private StringProperty src;
	private StringProperty dst;
	private StringProperty dcontext;
	private StringProperty channel;
	private StringProperty dstchannel;
	private StringProperty lastapp;
	private StringProperty lastdata;
	private ObjectProperty<Integer> duration;
	private ObjectProperty<Integer> billsec;
	private StringProperty disposition;
	private ObjectProperty<Integer> amaflags;
	private StringProperty accountCode;
	private StringProperty uniqueid;
	private StringProperty userfield;
	private StringProperty recordingFile;
	private StringProperty cnum;
	private StringProperty cnam;
	private StringProperty outbound_cnum;
	private StringProperty outbound_cnam;
	private StringProperty dst_canm;
	private StringProperty did;
	private StringProperty comentarios; //CAMPO COMENTARIOS. EMMANUEL OSTRIA 03/08/2019
	
	//CONSTRUCTOR VACIO
	//SE INICIALIZA LA PROPIEDAD COMENTARIOS. EMMANUEL OSTRIA 03/08/2019
	public CDR() {
		this(new Date(System.currentTimeMillis())," "," "," "," "," "," "," "," ",0, 0," ",0," "," "," "," "," "," "," ", " "," "," ","");
	}//FIN COSTRUCTOR

	//CONSTRUCTOR
	public CDR(Date callDate, String clid, String src, String dst,
			String dcontext, String channel, String dstchannel, String lastapp,
			String lastdata, int duration, int billsec,
			String disposition, int amaflags, String accountCode,
			String uniqueid, String userfield, String recordingFile, String cnum,
			String cnam, String outbound_cnum, String outbound_cnam, String dst_canm,
			String did, String comentarios) {

		this.callDate = new SimpleObjectProperty<Date>(callDate);
		this.clid = new SimpleStringProperty(clid);
		this.src = new SimpleStringProperty(src);
		this.dst = new SimpleStringProperty(dst);
		this.dcontext = new SimpleStringProperty(dcontext);
		this.channel = new SimpleStringProperty(channel);
		this.dstchannel = new SimpleStringProperty(dstchannel);
		this.lastapp = new SimpleStringProperty(lastapp);
		this.lastdata = new SimpleStringProperty(lastdata);
		this.duration = new SimpleObjectProperty<Integer>(duration);
		this.billsec = new SimpleObjectProperty<Integer>(billsec);
		this.disposition = new SimpleStringProperty(disposition);
		this.amaflags = new SimpleObjectProperty<Integer>(amaflags);
		this.accountCode = new SimpleStringProperty(accountCode);
		this.uniqueid = new SimpleStringProperty(uniqueid);
		this.userfield = new SimpleStringProperty(userfield);
		this.recordingFile = new SimpleStringProperty(recordingFile);
		this.cnum = new SimpleStringProperty(cnum);
		this.cnam = new SimpleStringProperty(cnam);
		this.outbound_cnum = new SimpleStringProperty(outbound_cnum);
		this.outbound_cnam = new SimpleStringProperty(outbound_cnam);
		this.dst_canm = new SimpleStringProperty(dst_canm);
		this.did = new SimpleStringProperty(did);
		this.comentarios = new SimpleStringProperty(comentarios);//SE INICIALIZA LA PROPIEDAD COMENTARIOS. EMMANUEL OSTRIAM 03/08/2019
	}//FIN CONSTRUCTOR
	

	//METODOS PARA ACCEDER A CALLDATE	
	public void setCalldate(Date callDate){
		this.callDate.set(callDate);
	}//FIN METODO

	public Date getCallDate(){
		return callDate.get();
	}//FIN METODO	

	public ObjectProperty<Date> callDateProperty(){
		return callDate;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A CLID
	public void setClid(String clid){
		this.clid.set(clid);
	}//FIN METODO
	
	public String getClid(){
		return clid.get();
	}//FIN METODO
	
	public StringProperty clidProperty(){
		return clid;
	}//FIN METODO

	//METODOS PARA ACCERDER A SRC
	public void setsrc(String src){
		this.src.set(src);
	}//FIN METODO

	public String getSRC(){
		return src.get();
	}//FIN METODO

	public StringProperty SCRProperty(){
	return src;
	}//FIN METODO

	//METODOS PARA ACCEDER A DST
	public void setDst(String dst) {
		this.dst.set(dst);
	}//FIN METODO

	public String getDst() {
		return dst.get();
	}//FIN METODO

	public StringProperty dstProperty() {
		return dst;
	}//FIN METODO

	//METODOS PARA ACCEDER DCONTEXT
	public void setdcontext(String dcontext) {
		this.dcontext.set(dcontext);
	}//FIN METODO

	public String getdcontect() {
		return dcontext.get();
	}//FIN METODO

	public StringProperty dcontectProperty() {
		return dcontext;
	}//FIN METODO

	//METODOS PARA ACCEDER A CHANNEL
	public void setChannel(String channel) {
		this.channel.set(channel);	
	}//FIN METODO
 
	public String getChannel() {
		return channel.get();
	}//FIN METODO

	public StringProperty channelProperty() {
		return channel;
	}//FIN METODO

	//METODOS PARA ACCEDER A DSTCHANNEL
	public void setdstChannel(String dstchannel) {
		this.dstchannel.set(dstchannel);
	}//FIN METODO

	public String getdstChannel() {
		return dstchannel.get();
	}//FIN METODO

	public StringProperty dstChannelProperty() {
		return dstchannel;
	}//FIN METODO

	//METODOS PARA ACCEDER A LASTAPP
	public void setLastApp(String lastapp) {
		this.lastapp.set(lastapp);
	}//FIN METODO

	public String getLastApp() {
		return lastapp.get();
	}//FIN METODO

	public StringProperty lastAppProperty() {
		return lastapp;
	}//FIN METODO

	//METODOS PARA ACCEDER A LASTDATA
	public void setLastData(String lastdata) {
		this.lastdata.set(lastdata);
	}//FIN METODO

	public String getLastData() {
		return lastdata.get();
	}//FIN METODO

	public StringProperty lastDataProperty() {
		return lastdata;
	}//FIN METODO

	//METODOS PARA ACCEDER A DURATION
	public void setDuration(Integer duration) {
		this.duration.set(duration);
	}//FIN METODO

	public Integer getDuration() {
		return duration.get();
	}//FIN METODO

	public ObjectProperty<Integer> durationProperty(){
		return duration;
	}//FIN METODO

	//METODOS PARA ACCEDER A BILLSEC
	public void setBillsec(Integer billsec) {
		this.billsec.set(billsec);
	}//FIN METODO

	public Integer getBillSec() {
		return billsec.get();
	}//FIN METODO

	public ObjectProperty<Integer> billsecProperty(){
		return billsec;
	}//FIN METODO

	//METODOS PARA ACCEDER A DISPOSITION
	public void setDisposition(String disposition) {
		this.disposition.set(disposition);
	}//FIN METODO
	
	public String getDisposition() {
		return disposition.get();
	}//FIN METODO

	public StringProperty dispositionProperty(){
		return  disposition;
	}//FIN METODO

	//METODOS PARA ACCEDER AMAFLAGS
	public void setAmaFlags(Integer amaflags) {
		this.amaflags.set(amaflags);
	}//FIN METODO

	public Integer getAmaflag() {
		return amaflags.get();
	}//FIN METODO

	public ObjectProperty<Integer> amaflagsProperty(){
		return amaflags;
	}//FIN METODO

	//METODOS PARA ACCEDER A ACCOUNTCODE
	public void setAccountCode(String accountCode) {
		this.accountCode.set(accountCode);
	}//FIN METODO

	public String getaccountCode() {
		return accountCode.get();
	}//FIN METODO

	public StringProperty accountCodeProperty() {
		return accountCode;
	}//FIN METODO

	//METODOS PARA ACCEDER A UNIQUEID
	public void setUniqueid(String uniqueid) {
		this.uniqueid.set(uniqueid);
	}//FIN METODO

	public String getUniqueid() {
		return uniqueid.get();
	}//FIN METODO

	public StringProperty uniqueidProperty() {
		return uniqueid;
	}//FIN METODO

	//METODOS PARA ACCEDER A USERFIELD
	public void setUserField(String userfield) {
		this.userfield.set(userfield);
	}//FIN METODO

	public String getUserField() {
		return userfield.get();
	}//FIN METODO

	public StringProperty userFieldProperty() {
		return userfield;
	}//FIN METODO

	//METODOS PARA ACCEDER A RECORDINGFILE
	public void setRecordingFile(String recordingFile) {
		this.recordingFile.set(recordingFile);
	}//FIN METODO

	public String getRecordingFile() {
		return recordingFile.get();
	}//FIN METODO

	public StringProperty recordingProperty() {
		return recordingFile;
	}//FIN METODO

	//METODOS PARA ACCEDER A CNUM
	public void setCnum(String cnum) {
		this.cnum.set(cnum);
	}//FIN METODO

	public String getCnum() {
		return cnum.get();
	}//FIN METODO

	public StringProperty cnumProperty() {
		return cnum;
	}//FIN METODO

	//METODOS PARA ACCEDER A CNAM
	public void setCnam(String cnam) {
		this.cnam.set(cnam);
	}//FIN METODO

	public String getCnam() {
		return cnam.get();
	}//FIN METODO

	public StringProperty cnamProperty() {
		return cnam;
	}//FIN METODO

	//METODOS PARA ACCEDER A OUT_CNUM
	public void setOut_cnum(String aoutbound_cnum) {
		this.outbound_cnum.set(aoutbound_cnum);
	}//FIN METODO

	public String getOut_cnum() {
		return outbound_cnum.get();
	}//FIN METODO

	public StringProperty out_cnumProperty() {
		return outbound_cnum;
	}//FIN METODO

	//METODOS PARA ACCEDER A OUT_CNAM
	public void setOut_cnam(String outbound_cnam) {
		this.outbound_cnam.set(outbound_cnam);
	}//FIN METODO

	public String getOut_cnam() {
		return outbound_cnam.get();
	}//FIN METODO

	public StringProperty out_CnamProperty() {
		return outbound_cnam;
	}//FIN METODO

	//METODOS PARA ACCEDER A DST_CANM
	public void setdst_canm(String dst_canm) {
		this.dst_canm.set(dst_canm);
	}//FIN METODO

	public String getDst_Canm() {
		return dst_canm.get();
	}//FIN METODO

	public StringProperty dst_CanmProperty() {
		return dst_canm;
	}//FIN METODO

	//METODOS PARA ACCEDER A DID
	public void setDid(String did) {
		this.did.set(did);
	}//FIN METODO

	public String getDis() {
		return did.get();
	}//FIN METODO

	public StringProperty didProperty() {
		return did;
	}//FIN METODO
	
	//METODOS PARA ACCEDER A COMENTARIOS. EMMANUEL OSTRIA 03/08/2019
	public void setComentario(String comentarios) {
		this.comentarios.set(comentarios);
	}//FIN METODO
	
	public String getComentario() {
		return this.comentarios.get();
	}//FIN METODO
	
	public StringProperty comentarioProperty() {
		return this.comentarios;
	}//FIN METODO
}//FIN CLASE
