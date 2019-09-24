package mx.shf6.pbxmanager.utilities;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    
    //VARIABLES
    private Connection conexion;
    private final String nombreBD;
    private final String hostBD;
    private final String usuarioBD;
    private final String contrasenaBD;
    
    public ConnectionDB(String nBD, String hBD, String uBD, String cBD) {
        this.nombreBD = nBD;
        this.hostBD = hBD;
        this.usuarioBD = uBD;
        this.contrasenaBD = cBD;
    }//END METHOD
    
    public Connection conectarMySQL(){
        try{
            Class.forName("java.sql.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + hostBD + "/" + nombreBD + "?useSSL=true", usuarioBD, contrasenaBD);
            //System.out.println("Conexion exitosa");
            return conexion;
        }catch(ClassNotFoundException | SQLException e){
            Notificacion.dialogoException(e);
        	//System.out.println("Conexion rechazada: " + e.toString());
            return null;
        }//END TRY-CATCH
    }//END CONECTARMYSQL
    
    /* Método de cerrar concexion */
    public void terminarConexion(Connection connection){
        try{
        	connection.close();
            //System.out.println("Conexion finalizada");
        }catch(SQLException sqle){
            Notificacion.dialogoException(sqle);
        }//END TRY-CATCH
    }//END TERMINAR CONEXION
    
}//END CLASS
