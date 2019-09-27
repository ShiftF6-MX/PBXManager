package mx.shf6.pbxmanager.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.shf6.pbxmanager.model.Usuario;
import mx.shf6.pbxmanager.utilities.Notificacion;

public class UsuarioDAO {

	// METODO PARA AGREGAR UN USUARIO
	public static final boolean createUsuario(Connection conexion, Usuario usuario) {
		String consulta = "INSERT INTO ut_usuarios (Usuario, PIN, Extension, Status) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement ps = (PreparedStatement) conexion.prepareStatement(consulta);
			ps.setString(1, usuario.getUsuario());
			ps.setString(2, usuario.getPin());
			ps.setString(3, usuario.getExtension());
			ps.setInt(4, usuario.getStatus());
			ps.execute();
			return true;
		} // FIN TRY
		catch (Exception e) {
			Notificacion.dialogoException(e);
			return false;
		} // FIN CATCH
	}

	// METODO PARA OBTENER UN REGISTRO
	public static Usuario readOneUsuario(Connection connection, int sysPK) {
		Usuario usuario = new Usuario();
		String consulta = "SELECT Sys_PK, Usuario, PIN, Extension, Status FROM ut_usuarios WHERE Sys_PK = " + sysPK;
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(consulta);
			while (result.next()) {
				usuario.setSysPk(result.getInt(1));
				usuario.setUsuario(result.getString(2));
				usuario.setPin(result.getString(3));
				usuario.setStatus(result.getInt(4));
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return usuario;
	}

	public static final ArrayList<Usuario> readTodosUsuarios(Connection conexion) {
		ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
		String query = "SELECT Sys_PK, Usuario, PIN, Extension, Status FROM ut_usuarios ORDER BY Sys_PK";
		try {
			Statement statement = conexion.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				Usuario usuario = new Usuario();
				usuario.setSysPk(result.getInt(1));
				usuario.setPin(result.getString(2));
				usuario.setExtension(result.getString(3));
				usuario.setStatus(result.getInt(4));
				listaUsuario.add(usuario);
			} // FIN WHILE
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
		} // FIN TRY/CATCH
		return listaUsuario;
	}

	// METODO PARA ACTUALIZAR UN USUARIO
	public static final boolean updateUsuario(Connection connection, Usuario usuario) {
		String query = "UPDATE ut_usuarios SET Usuario = ?,PIN = ?, Extension = ?, Status = ? WHERE Sys_PK = ?";
		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ps.setString(1, usuario.getUsuario());
			ps.setString(2, usuario.getPin());
			ps.setString(3, usuario.getExtension());
			ps.setInt(5, usuario.getStatus());
			ps.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

	// METODO PARA ELIMINAR UN USUARIO
	public static final boolean eliminar(Connection connection, Usuario usuario) {
		String query = "DELETE FROM ut_usuarios WHERE Sys_PK= ?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, usuario.getSysPk());
			ps.execute();
			return true;
		} catch (SQLException ex) {
			Notificacion.dialogoException(ex);
			return false;
		} // FIN TRY/CATCH
	}// FIN METODO

}
