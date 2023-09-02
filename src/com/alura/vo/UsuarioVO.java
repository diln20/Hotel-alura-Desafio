
package com.alura.vo;

/**
 *
 * @author andre_hk4s7fk
 */
public class UsuarioVO {
    
    String Usuario;
    
    String password;
    
    boolean  isAuthenticated;

    public boolean isIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public UsuarioVO() {
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioVO(String Usuario, String password) {
        this.Usuario = Usuario;
        this.password = password;
    }
    
    
    
    
    
}
