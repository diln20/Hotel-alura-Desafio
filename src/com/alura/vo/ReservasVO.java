
package com.alura.vo;

import static java.lang.Math.abs;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author andre_hk4s7fk
 */
public class ReservasVO {
    
    private Integer id;
    private LocalDate FechaEntrada;
    private LocalDate FechaSalida;
    private double Valor;
    private String FormaPago;

    public ReservasVO(Integer id, LocalDate FechaEntrada, LocalDate FechaSalida, double Valor, String FormaPago) {
        this.id = id;
        this.FechaEntrada = FechaEntrada;
        this.FechaSalida = FechaSalida;
        this.Valor = Valor;
        this.FormaPago = FormaPago;
    }

    public ReservasVO() {
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFechaEntrada() {
        return FechaEntrada;
    }

    public void setFechaEntrada(LocalDate FechaEntrada) {
        this.FechaEntrada = FechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return FechaSalida;
    }

    public void setFechaSalida(LocalDate FechaSalida) {
        this.FechaSalida = FechaSalida;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double Valor) {
        this.Valor = Valor;
    }

    public String getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(String FormaPago) {
        this.FormaPago = FormaPago;
    }
    
    
    
     @Override
    public String toString() {
        return id + "\t" + FechaEntrada + "\t" + FechaSalida + "\t" + Valor + "\t" + FormaPago;
    }
    
  public int calcularCantidadDias(LocalDate FechaEntrada, LocalDate FechaSalida) {
        if (FechaEntrada != null && FechaSalida != null) {
             int dias=(int) ChronoUnit.DAYS.between(FechaEntrada, FechaSalida);
             return abs(dias);
        }
        return 0;
    }
    
}
