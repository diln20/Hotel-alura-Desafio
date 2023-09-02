package com.alura.vo;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author andre_hk4s7fk
 */
public class HuespedVO {
    
    private Integer id;
    
    private String Nombre;

    public HuespedVO(Integer id, String Nombre, String Apellido, LocalDate FechaNacimiento, String Nacionalidad, String Telefono, Integer idReserva) {
        this.id = id;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.FechaNacimiento = FechaNacimiento;
        this.Nacionalidad = Nacionalidad;
        this.Telefono = Telefono;
        this.idReserva = idReserva;
    }

    public HuespedVO() {
    }
    
    
    
    
    private String Apellido;
    
    private LocalDate FechaNacimiento;
    
    private String Nacionalidad;
    
    private String Telefono;
    
    private Integer idReserva;
    
     private List<HuespedVO> huespedes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String Nacionalidad) {
        this.Nacionalidad = Nacionalidad;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }
    
   
    @Override
    public String toString() {
        return "HuespedVO:" +
                "\n  id=" + id +
                "\n  nombre='" + Nombre + '\'' +
                "\n  apellido='" + Apellido + '\'' +
                "\n  fechaNacimiento=" + FechaNacimiento +
                "\n  nacionalidad='" + Nacionalidad + '\'' +
                "\n  telefono='" + Telefono + '\'' +
                "\n  idReserva=" + idReserva +
                "\n";
    }
    
}
