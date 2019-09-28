package mx.shf6.pbxmanager.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.shf6.pbxmanager.model.CDR;
import mx.shf6.pbxmanager.utilities.Notificacion;

public class CDRDao {

	//METODO PARA LEER LOS DATOS DE LA BD
	public static ArrayList<CDR> leerTodos(Connection conexion){
		ArrayList<CDR> listaCDR = new ArrayList<CDR>();
		String consulta = " SELECT calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, uniqueid, cnum, cnam, comentario FROM cdr";
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(consulta);		
			while (rs.next()) {				
				CDR cdr = new CDR();
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
	
	//METODO PARA MODIFICAR LOS DATOS DE LA BD
	public static final boolean update(Connection connection, CDR cdr) {
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
