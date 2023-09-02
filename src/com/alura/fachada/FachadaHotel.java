package com.alura.fachada;

import Mediador.HotelMDTR;
import com.alura.vo.HuespedVO;
import com.alura.vo.ReservasVO;
import java.util.List;

/**
 *
 * @author andre_hk4s7fk
 */
public class FachadaHotel {
    
    private HotelMDTR hotelMDTR;

    public FachadaHotel() {
        this.hotelMDTR = new HotelMDTR();
    }
    
   
    public List<HuespedVO> listar(Object obj) {
        return this.hotelMDTR.listar(obj);
    }
    
    
    public int guardar(Object obj) {
        return this.hotelMDTR.guardar(obj);
    }
    
    public boolean guardar(Object obj,int idReserva) {
        return this.hotelMDTR.guardar(obj,idReserva);
    }
    
    
    
     public boolean verificarUsuario(Object obj) {
        return this.hotelMDTR.verificarUsuario(obj);
    } 
     
     
    public List<ReservasVO> listarReserva(Object obj) {
        return this.hotelMDTR.listarReserva(obj);
    }
     
    public int actualizar(Object obj) {
        return this.hotelMDTR.actualizar(obj);
    }
    
    public int eliminar(int id) {
        return this.hotelMDTR.eliminar(id);
    }
    
    public int eliminarReserva(int id) {
        return this.hotelMDTR.eliminarReserva(id);
    }
    
    
     public int actualizarReserva(Object obj) {
        return this.hotelMDTR.actualizarReserva(obj);
    }
}
