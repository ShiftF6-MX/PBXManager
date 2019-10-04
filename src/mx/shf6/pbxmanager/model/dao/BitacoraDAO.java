package mx.shf6.pbxmanager.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import mx.shf6.pbxmanager.model.Bitacora;
import mx.shf6.pbxmanager.utilities.Notificacion;

public class BitacoraDAO {

	//METODO PARA LEER LOS DATOS DE LA BD
	public static ArrayList<Bitacora> readTodos(Connection conexion, String status, Date fechaInicio, Date fechaFinal, String cdrOrigen, String numero){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<Bitacora> listaCDR = new ArrayList<Bitacora>();
		String consulta = " SELECT calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, uniqueid, cnum, cnam, comentario FROM cdr WHERE disposition LIKE'%" + status + "%' AND (calldate BETWEEN '" + simpleDateFormat.format(fechaInicio) + " 00:00:00" +"' AND '"+ simpleDateFormat.format(fechaFinal) + " 23:59:59" + "') AND src LIKE '%" + cdrOrigen + "%' AND dst LIKE '%" + numero + "%'";
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(consulta);		
			while (rs.next()) {				
				Bitacora cdr = new Bitacora();
				cdr.setCalldate(rs.getDate(1));
				cdr.setClid(rs.getString(2));
				cdr.setsrc(rs.getString(3));
				cdr.setDst(rs.getString(4));
				cdr.setdcontext(rs.getString(5));
				cdr.setChannel(rs.getString(6));
				cdr.setdstChannel(rs.getString(7));
				cdr.setLastApp(rs.getString(8));
				cdr.setLastData(rs.getString(9));
				cdr.setDuration(rs.getInt(10));
				cdr.setBillsec(rs.getInt(11));
				cdr.setDisposition(rs.getString(12));
				cdr.setAmaFlags(rs.getInt(13));
				cdr.setUniqueid(rs.getString(14));
				cdr.setCnum(rs.getString(15));
				cdr.setCnam(rs.getString(16));
				cdr.setComentario(rs.getString(17));	
				listaCDR.add(cdr);
			}//FIN WHILE
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN CATCH
		return listaCDR;
	}//FIN METODO
	
	//METODO PARA LEER LOS DATOS DE LA BD
	public static ResultSet readTodosResultSet(Connection conexion, String status, Date fechaInicio, Date fechaFinal, String cdrOrigen, String numero){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String consulta = "SELECT calldate AS Fecha, src AS Origen, cnam AS Usuario, dst AS Destino, duration AS Duracion, disposition AS Status, uniqueid AS UNIQUEID, comentario AS Comentarios FROM cdr WHERE disposition LIKE'%" + status + "%' AND (calldate BETWEEN '" + simpleDateFormat.format(fechaInicio) + " 00:00:00" +"' AND '"+ simpleDateFormat.format(fechaFinal) + " 23:59:59" + "') AND src LIKE '%" + cdrOrigen + "%' AND dst LIKE '%" + numero + "%'";
		ResultSet resultados = null;
		try {
			Statement st = conexion.createStatement();
			resultados = st.executeQuery(consulta);		
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
		}//FIN CATCH
		return resultados;
	}//FIN METODO
	//METODO PARA MODIFICAR LOS DATOS DE LA BD
	public static final boolean update(Connection connection, Bitacora cdr) {
		String query = "UPDATE cdr SET comentario = ? WHERE uniqueid = ? ";
		try {
			PreparedStatement sentenciaPreparada = connection.prepareStatement(query);
			sentenciaPreparada.setString(1, cdr.getComentario());
			sentenciaPreparada.setString(2, cdr.getUniqueid());
			sentenciaPreparada.execute();
			return true;
		}catch(SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}//FIN TRY-CATCH
	}//FIN METODO
}//FIN CLASE
