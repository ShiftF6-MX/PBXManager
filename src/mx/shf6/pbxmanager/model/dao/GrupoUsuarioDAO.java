package mx.shf6.pbxmanager.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.shf6.pbxmanager.model.GrupoUsuario;
import mx.shf6.pbxmanager.utilities.Notificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class GrupoUsuarioDAO {

	// METODO PARA HACER CREATE EN TABLA GRUPOUSUARIO
	public static final boolean create(Connection connection, GrupoUsuario grupoUsuario) {
		String query = " INSERT INTO ut_gruposusuario (Nombre, Descripcion) values ( ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, grupoUsuario.getNombre());
			preparedStatement.setString(2, grupoUsuario.getDescripcion());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		}
	}// FIN METODO

	// METODO PARA OBTENER REGISTROS DE LA TABLA GRUPOUSUARIO
	public static final ArrayList<GrupoUsuario> readTodos(Connection connection) {
		String query = "SELECT Sys_PK, Nombre, Descripcion FROM ut_gruposusuario ORDER BY Nombre ASC";
		ArrayList<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				GrupoUsuario grupoUsuario = new GrupoUsuario();
				grupoUsuario.setSysPk(resultSet.getInt(1));
				grupoUsuario.setNombre(resultSet.getString(2));
				grupoUsuario.setDescripcion(resultSet.getString(3));
				listaGrupoUsuario.add(grupoUsuario);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY-CATCH
		return listaGrupoUsuario;
	}// FIN METODO

	// METODO PARA HACER SELECT EN LA TABLA GRUPOSUSUARIO
	public static final GrupoUsuario readPorSysPK(Connection connection, int sysPK) {
		String query = "SELECT Sys_PK, Nombre, Descripcion FROM ut_gruposusuario WHERE Sys_PK = " + sysPK;
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultSet = sentencia.executeQuery(query);
			while (resultSet.next()) {
				grupoUsuario.setSysPk(resultSet.getInt(1));
				grupoUsuario.setNombre(resultSet.getString(2));
				grupoUsuario.setDescripcion(resultSet.getString(3));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRYcATCH
		return grupoUsuario;
	}// FIN METODO

	// METODO PARA OBTENER REGISTROS SEGUN SU NOMBRE O DESCRIPCION
	public static final ArrayList<GrupoUsuario> readPorNombreDescripcionLike(Connection connection, String like) {
		ArrayList<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		String query = "SELECT Sys_PK, Nombre, Descripcion FROM ut_gruposusuario WHERE Nombre LIKE '%" + like
				+ "%' OR Descripcion LIKE '%" + like + "%'";
		try {
			Statement sentencia = connection.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);
			while (resultados.next()) {
				GrupoUsuario grupoUsuario = new GrupoUsuario();
				grupoUsuario.setSysPk(resultados.getInt(1));
				grupoUsuario.setNombre(resultados.getString(2));
				grupoUsuario.setDescripcion(resultados.getString(3));
				listaGrupoUsuario.add(grupoUsuario);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRYCATch
		return listaGrupoUsuario;
	}// FIN METODO

	// METODO PARA HACER UPDATE EN LA TABLA GRUPOSUSUARIO
	public static final boolean update(Connection connection, GrupoUsuario grupoUsuario) {
		String query = "UPDATE ut_gruposusuario SET Nombre = ?, descripcion= ? WHERE Sys_PK= ?;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, grupoUsuario.getNombre());
			preparedStatement.setString(2, grupoUsuario.getDescripcion());
			preparedStatement.setInt(3, grupoUsuario.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

	// METODO PARA HACER DELETE EN LA TABLA GRUPOSUSUARIO
	public static final boolean delete(Connection connection, GrupoUsuario grupoUsuario) {
		String query = " DELETE FROM ut_gruposusuario WHERE Sys_PK= ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, grupoUsuario.getSysPk());
			preparedStatement.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY-CATCH
	}// FIN METODO

}// FIN CLASE