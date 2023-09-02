package com.alura.conexionFactory;
   import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * @author andre_hk4s7fk
 */
//public class ConnectionFactory {
//    
//    public Connection recuperaConexion() {
//        try {
//                  return DriverManager.getConnection(
//                            "jdbc:mysql://localhost/hotelalura?useTimeZone=true&serverTimeZone=UTC",
//                            "root",
//                            "renault9"); 
//            
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//   
//}


public class ConnectionFactory {

	public DataSource dataSource;

	public ConnectionFactory() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotelalura?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("renault9");
		

		this.dataSource = comboPooledDataSource;
	}

	public Connection recuperaConexion() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}



