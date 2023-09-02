package Mediador;

import com.alura.conexionFactory.ConnectionFactory;
import com.alura.dao.HuespedDAO;
import com.alura.dao.ReservasDAO;
import com.alura.dao.UsuarioDAO;
import com.alura.vo.HuespedVO;
import com.alura.vo.ReservasVO;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author andre_hk4s7fk
 */
public class HotelMDTR {
    private HuespedDAO huespedDAO;
    private UsuarioDAO usuarioDAO;
    private ReservasDAO reservasDAO;
            
    public HotelMDTR() {
        Connection con = new ConnectionFactory().recuperaConexion();
        this.huespedDAO = new HuespedDAO(con);
        this.usuarioDAO = new UsuarioDAO(con);
        this.reservasDAO = new ReservasDAO(con);
    }

    public List<HuespedVO> listar(Object obj) {
        System.out.println("mediador ");
        List lista = null;
        System.out.println(lista);
        lista = huespedDAO.listar((HuespedVO) obj);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        System.out.println(huespedDAO);
        return lista;
    }

    public boolean verificarUsuario(Object obj) {
        System.out.println("mediador ");
        boolean verificar = usuarioDAO.verificarUsuario(obj);
        usuarioDAO.cerrarConexion();
        usuarioDAO = null;
        return verificar;
    }

    public List<ReservasVO> listarReserva(Object obj) {
        System.out.println("mediador ");
        List lista = null;
        lista = reservasDAO.listar((ReservasVO) obj);
        reservasDAO.cerrarConexion();
        reservasDAO = null;
        return lista;
    }

    public int guardar(Object obj) {
        System.out.println("mediador ");
        int resultado = reservasDAO.guardar((ReservasVO) obj);
        reservasDAO.cerrarConexion();
        reservasDAO = null;
        return resultado;
    }

    public boolean guardar(Object obj, int idReserva) {
        System.out.println("mediador ");
        boolean resultado = huespedDAO.guardar((HuespedVO) obj, idReserva);
        System.out.println("res med" + resultado);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        return resultado;
    }

    public int actualizar(Object obj) {
        System.out.println("mediador ");
        int resultado = huespedDAO.actualizar((HuespedVO) obj);
        System.out.println("res med" + resultado);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        return resultado;
    }

    public int eliminar(int id) {
        System.out.println("mediador ");
        int resultado = huespedDAO.eliminar(id);
        System.out.println("res med" + resultado);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        return resultado;
    }

    public int eliminarReserva(int id) {
        System.out.println("mediador ");
        int resultado = reservasDAO.eliminarReserva(id);
        System.out.println("res med" + resultado);
        reservasDAO.cerrarConexion();
        reservasDAO = null;
        return resultado;
    }

    public int actualizarReserva(Object obj) {
        System.out.println("mediador ");
        int resultado = reservasDAO.actualizarReserva((ReservasVO) obj);
        reservasDAO.cerrarConexion();
        reservasDAO = null;
        return resultado;
    }

}
