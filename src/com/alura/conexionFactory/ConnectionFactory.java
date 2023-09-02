package com.alura.conexionFactory;
   import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author andre_hk4s7fk
 */
public class ConnectionFactory {
    
    public Connection recuperaConexion() {
        try {
                  return DriverManager.getConnection(
                            "jdbc:mysql://localhost/hotelalura?useTimeZone=true&serverTimeZone=UTC",
                            "root",
                            "renault9"); 
            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}

