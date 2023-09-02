package com.alura.dao;

import com.alura.conexionFactory.ConnectionFactory;
import com.alura.vo.ReservasVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre_hk4s7fk
 */
public class ReservasDAO {

    final private Connection con;

    public ReservasDAO(Connection con) {
        this.con = con;
    }

    public List<ReservasVO> listar(ReservasVO reservasVO) {
        List<ReservasVO> resultado = new ArrayList<>();
        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();

        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT id, FechaEntrada, FechaSalida, Valor, FormaPago FROM hotelalura.reservas");

            boolean hasConditions = false; // To control whether conditions are added

            if (reservasVO.getId() != null) {
                queryBuilder.append(" WHERE id = ?");
                hasConditions = true;
            }

            if (reservasVO.getFechaEntrada() != null) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" FechaEntrada = ?");
                hasConditions = true;
            }

            if (reservasVO.getFechaSalida() != null) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" FechaSalida = ?");
                hasConditions = true;
            }

            if (reservasVO.getValor() != 0) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" Valor = ?");
                hasConditions = true;
            }

            if (reservasVO.getFormaPago() != null) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" FormaPago = ?");
                hasConditions = true;
            }

            final PreparedStatement statement = con.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;

            if (reservasVO.getId() != null) {
                statement.setInt(parameterIndex++, reservasVO.getId());
            }

            if (reservasVO.getFechaEntrada() != null) {
                statement.setDate(parameterIndex++, java.sql.Date.valueOf(reservasVO.getFechaEntrada()));
            }

            if (reservasVO.getFechaSalida() != null) {
                statement.setDate(parameterIndex++, java.sql.Date.valueOf(reservasVO.getFechaSalida()));
            }

            if (reservasVO.getValor() != 0) {
                statement.setDouble(parameterIndex++, reservasVO.getValor());
            }

            if (reservasVO.getFormaPago() != null) {
                statement.setString(parameterIndex++, reservasVO.getFormaPago());
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReservasVO fila = new ReservasVO(
                            resultSet.getInt("id"),
                            resultSet.getDate("FechaEntrada").toLocalDate(),
                            resultSet.getDate("FechaSalida").toLocalDate(),
                            resultSet.getDouble("Valor"),
                            resultSet.getString("FormaPago")
                    );
                    resultado.add(fila);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public int guardar(ReservasVO reservasVO) {
        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
        int id = 0;
        boolean resultado;
        try (con) {
            final PreparedStatement statement = con.prepareStatement("INSERT INTO hotelalura.reservas (FechaEntrada,FechaSalida,Valor,FormaPago)"
                    + "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                statement.setDate(1, java.sql.Date.valueOf(reservasVO.getFechaEntrada()));
                statement.setDate(2, java.sql.Date.valueOf(reservasVO.getFechaSalida()));
                statement.setDouble(3, reservasVO.getValor());
                statement.setString(4, reservasVO.getFormaPago());
                resultado = statement.execute();
                final ResultSet resultSet = statement.getGeneratedKeys();
                try (resultSet) {
                    while (resultSet.next()) {
                        reservasVO.setId(resultSet.getInt(1));
                        id = reservasVO.getId();
                        System.out.println(
                                String.format(
                                        "Fue insertado el producto de ID %s ",
                                        reservasVO));
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la reserva en la base de datos", e);
        }
        return id;
    }

    

    public int eliminarReserva(Integer id) {
        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM hotelalura.reservas WHERE id = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();
                int updateCount = statement.getUpdateCount();
                return updateCount;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
      public int actualizarReserva(Object obj) {
        ConnectionFactory factory = new ConnectionFactory();
        ReservasVO reservasVO=(ReservasVO)obj;
        final Connection con = factory.recuperaConexion();
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE hotelalura.reservas SET "
                    + " FechaEntrada            = ?, "
                    + " FechaSalida             = ?, "
                    + " Valor                   = ?, "
                    + " FormaPago               = ?  "
                    + " WHERE id           = ?");

            try (statement) {
                
                statement.setDate(1, java.sql.Date.valueOf(reservasVO.getFechaEntrada()));
                statement.setDate(2, java.sql.Date.valueOf(reservasVO.getFechaSalida()));
                statement.setDouble(3, reservasVO.getValor());
                statement.setString(4, reservasVO.getFormaPago());
                statement.setInt(5, reservasVO.getId());
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
