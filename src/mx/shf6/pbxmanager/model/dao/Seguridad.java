package mx.shf6.pbxmanager.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mx.shf6.pbxmanager.utilities.Notificacion;




public class Seguridad {
	
	public static boolean verificarAcceso(Connection connection, int grupoUsuario, String codigoItem) {
		
		//METODO PARA VERIFICAR EL ACCESO A LOS COMPONENTES SEGUN EL USUARIO
		String query="SELECT * "
				+ "FROM ut_rolgruposusuario "
				+ "INNER JOIN ut_roles ON ut_rolgruposusuario.RolFK = ut_roles.Sys_PK "
				+ "WHERE ut_rolgruposusuario.GrupoUsuarioFK = " + grupoUsuario + " "
				+ "AND ut_roles.CodigoItem = '" + codigoItem + "';";			
		try {	
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {					
				return true;
			}//FIN WHILE
			return false;
		} catch (SQLException e) {
			Notificacion.dialogoException(e);
			return false;
		}//FIN TRY-CATCH	
	}//FIN METODO
	
}//FIN CLASE
