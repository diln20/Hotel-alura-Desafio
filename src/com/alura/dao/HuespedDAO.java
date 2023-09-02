package com.alura.dao;

import com.alura.vo.HuespedVO;
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
public class HuespedDAO {

    final private Connection con;

    public HuespedDAO(Connection con) {
        this.con = con;
    }

    public List<HuespedVO> listar(HuespedVO huespedVO) {
        List<HuespedVO> resultado = new ArrayList<>();

        try (con) {
            StringBuilder queryBuilder = new StringBuilder("SELECT id, Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono, idReserva FROM hotelalura.huespedes");

            boolean hasConditions = false; // To control whether conditions are added

            if (huespedVO.getFechaNacimiento() != null) {
                queryBuilder.append(" WHERE FechaNacimiento = ?");
                hasConditions = true;
            }

            if (huespedVO.getNombre() != null && !"".equals(huespedVO.getNombre())) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" Nombre LIKE ?");
                hasConditions = true;
            }

            if (huespedVO.getApellido() != null && !"".equals(huespedVO.getApellido())) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" Apellido LIKE ?");
                hasConditions = true;
            }

            if (huespedVO.getIdReserva() != null) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" idReserva = ?");
                hasConditions = true;
            }

            if (huespedVO.getNacionalidad() != null && !"".equals(huespedVO.getNacionalidad())) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" Nacionalidad LIKE ?");
                hasConditions = true;
            }

            if (huespedVO.getTelefono() != null && !"".equals(huespedVO.getTelefono())) {
                queryBuilder.append(hasConditions ? " AND" : " WHERE")
                        .append(" Telefono LIKE ?");
            }

            final PreparedStatement statement = con.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            if (huespedVO.getFechaNacimiento() != null) {
                statement.setDate(parameterIndex++, java.sql.Date.valueOf(huespedVO.getFechaNacimiento()));
            }
            if (huespedVO.getNombre() != null && !"".equals(huespedVO.getNombre())) {
                statement.setString(parameterIndex++, "%" + huespedVO.getNombre() + "%");
            }
            if (huespedVO.getApellido() != null && !"".equals(huespedVO.getApellido())) {
                statement.setString(parameterIndex++, "%" + huespedVO.getApellido() + "%");
            }
            if (huespedVO.getIdReserva() != null) {
                statement.setInt(parameterIndex++, huespedVO.getIdReserva());
            }
            if (huespedVO.getNacionalidad() != null && !"".equals(huespedVO.getNacionalidad())) {
                statement.setString(parameterIndex++, "%" + huespedVO.getNacionalidad() + "%");
            }
            if (huespedVO.getTelefono() != null && !"".equals(huespedVO.getTelefono())) {
                statement.setString(parameterIndex, "%" + huespedVO.getTelefono() + "%");
            }

            try (statement) {
                statement.execute();

                ResultSet resulSet = statement.getResultSet();
                System.out.println("sql" + statement);
                try (resulSet) {
                    while (resulSet.next()) {
                        HuespedVO fila = new HuespedVO(resulSet.getInt("id"),
                                resulSet.getString("Nombre"),
                                resulSet.getString("Apellido"),
                                resulSet.getDate("FechaNacimiento").toLocalDate(),
                                resulSet.getString("Nacionalidad"),
                                resulSet.getString("Telefono"),
                                resulSet.getInt("idReserva"));
                        resultado.add(fila);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public boolean guardar(HuespedVO huespedVO, int idReserva) {
        boolean resultado = false;
        try (con) {

            final PreparedStatement statement = con.prepareStatement("INSERT INTO hotelalura.huespedes (Nombre,Apellido,FechaNacimiento,Nacionalidad,Telefono,idReserva)"
                    + "VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                statement.setString(1, huespedVO.getNombre());
                statement.setString(2, huespedVO.getApellido());
                statement.setDate(3, java.sql.Date.valueOf(huespedVO.getFechaNacimiento()));
                statement.setString(4, huespedVO.getNacionalidad());
                statement.setString(5, huespedVO.getTelefono());
                statement.setInt(6, idReserva);
                statement.execute();

                System.out.println("r" + resultado);
                final ResultSet resultSet = statement.getGeneratedKeys();
                try (resultSet) {
                    while (resultSet.next()) {
                        huespedVO.setId(resultSet.getInt(1));
                        System.out.println(
                                String.format(
                                        "Fue insertado el producto de ID %s ",
                                        huespedVO));
                    }
                    if (huespedVO.getId() != null) {
                        resultado = true;
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la reserva en la base de datos", e);
        }
        System.out.println("rs" + resultado);
        return resultado;
    }

    public int actualizar(Object obj) {
        HuespedVO huespedVO = (HuespedVO) obj;
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE hotelalura.huespedes SET "
                    + " Nombre             = ?, "
                    + " Apellido           = ?, "
                    + " FechaNacimiento    = ?, "
                    + " Nacionalidad       = ?, "
                    + " Telefono           = ?, "
                    + " idReserva          = ?  "
                    + " WHERE ID           = ?");

            try (statement) {

                statement.setString(1, huespedVO.getNombre());
                statement.setString(2, huespedVO.getApellido());
                statement.setDate(3, java.sql.Date.valueOf(huespedVO.getFechaNacimiento()));
                statement.setString(4, huespedVO.getNacionalidad());
                statement.setString(5, huespedVO.getTelefono());
                statement.setInt(6, huespedVO.getIdReserva());
                statement.setInt(7, huespedVO.getId());
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM hotelalura.huespedes WHERE id = ?");

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
