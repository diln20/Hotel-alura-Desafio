package com.alura.dao;

import com.alura.conexionFactory.ConnectionFactory;
import com.alura.vo.UsuarioVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre_hk4s7fk
 */
public class UsuarioDAO {

    final private Connection con;

    public UsuarioDAO(Connection con) {
        this.con = con;
    }

    public boolean verificarUsuario(Object obj) {
        UsuarioVO usuarioVO = (UsuarioVO) obj;
        String usuario = usuarioVO.getUsuario();
        String password = usuarioVO.getPassword();
        boolean verificar = false;
        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
        String querySelect = "SELECT Usuario "
                + " FROM USUARIOS "
                + " WHERE Usuario = ? AND Password = ?";

        try (PreparedStatement statement = con.prepareStatement(querySelect)) {
            statement.setString(1, usuario);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            verificar = resultSet.next(); // Si hay un resultado en el ResultSet, significa que el usuario existe.
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println("Usuario No encontrado");
        }

        return verificar;
    }

    public void cerrarConexion() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexión", e);
        }
    }

}
