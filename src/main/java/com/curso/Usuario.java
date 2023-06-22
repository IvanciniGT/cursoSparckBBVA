package com.curso;


import lombok.Data;

import java.io.Serializable;

@Data
public class Usuario implements Serializable {

    private String nombre;
    private String apellidos;
    private String email;
    private String dni;
    private int edad;
    private boolean mayorDeEdad;
    private boolean dniValido;
    private boolean emailValido;

    public boolean getMayorDeEdad(){
        return edad>18;
    }

    public void setEmail(String email){
        this.email=email;
        //Validacion del email
        this.emailValido = email.contains("@");
    }
    public void setDni(String dni){
        this.dni=dni;
        //Validacion del dni
        this.dniValido = ! dni.matches("^[a-zA-Z].*");
    }

    public boolean isValid(){
        return dniValido && emailValido;
    }

    public static Usuario readUsuario(String linea){
        String[] partes = linea.split(",");
        Usuario p=new Usuario();
        p.setNombre(partes[0]);
        p.setApellidos(partes[1]);
        p.setEdad(Integer.parseInt(partes[2]));
        p.setEmail(partes[3]);
        p.setDni(partes[4]);
        return p;
    }

}
