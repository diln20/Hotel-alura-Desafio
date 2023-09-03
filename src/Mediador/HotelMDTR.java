package Mediador;

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

    final private Connection con;

    public HotelMDTR() {
        this.con = null;
    }

    public HotelMDTR(Connection con) {
        this.con = con;
    }

    public List<HuespedVO> listar(Object obj) {
        System.out.println("mediador ");
        List lista = null;
        HuespedDAO huespedDAO = new HuespedDAO(con);
        lista = huespedDAO.listar((HuespedVO) obj);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        return lista;
    }

    public boolean verificarUsuario(Object obj) {
        System.out.println("mediador ");
        UsuarioDAO usuarioDAO = new UsuarioDAO(con);
        boolean verificar = usuarioDAO.verificarUsuario(obj);
        usuarioDAO.cerrarConexion();
        usuarioDAO = null;
        return verificar;
    }

    public List<ReservasVO> listarReserva(Object obj) {
        System.out.println("mediador ");
        List lista = null;
        ReservasDAO reservaDAO = new ReservasDAO(con);
        lista = reservaDAO.listar((ReservasVO) obj);
        reservaDAO.cerrarConexion();
        reservaDAO = null;
        return lista;
    }

    public int guardar(Object obj) {
        System.out.println("mediador ");
        ReservasDAO reservaDAO = new ReservasDAO(con);
        int resultado = reservaDAO.guardar((ReservasVO) obj);
        reservaDAO.cerrarConexion();
        reservaDAO = null;
        return resultado;
    }

    public boolean guardar(Object obj, int idReserva) {
        System.out.println("mediador ");
        HuespedDAO huespedDAO = new HuespedDAO(con);
        boolean resultado = huespedDAO.guardar((HuespedVO) obj, idReserva);
        System.out.println("res med" + resultado);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        return resultado;
    }

    public int actualizar(Object obj) {
        System.out.println("mediador ");
        HuespedDAO huespedDAO = new HuespedDAO(con);
        int resultado = huespedDAO.actualizar((HuespedVO) obj);
        System.out.println("res med" + resultado);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        return resultado;
    }

    public int eliminar(int id) {
        System.out.println("mediador ");
        HuespedDAO huespedDAO = new HuespedDAO(con);
        int resultado = huespedDAO.eliminar(id);
        System.out.println("res med" + resultado);
        huespedDAO.cerrarConexion();
        huespedDAO = null;
        return resultado;
    }
    
    
     public int eliminarReserva(int id) {
        System.out.println("mediador ");
        ReservasDAO reservasDAO = new ReservasDAO(con);
        int resultado = reservasDAO.eliminarReserva(id);
        System.out.println("res med"+resultado);
        reservasDAO.cerrarConexion();
        reservasDAO = null;
        return resultado;
    }
     
      
       public int actualizarReserva(Object obj) {
        System.out.println("mediador ");
        ReservasDAO reservaDAO = new ReservasDAO(con);
        int resultado = reservaDAO.actualizarReserva((ReservasVO) obj);
        reservaDAO.cerrarConexion();
        reservaDAO = null;
        return resultado;
    }
     

    
      
    
}
