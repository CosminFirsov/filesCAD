/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

import java.io.Serializable;

/**
 * Clase cliente. Sirve para almacenar los datos de un cliente
 *
 * @author Cosmin Firsov
 */
public class Cliente implements Serializable{

    int clienteId;
    String nombre;
    String apellido1;
    String apellido2;
    String email;
    String telefono;
    String dni;

    /**
     * Constructor vacío de la clase
     */
    public Cliente() {
    }

    /**
     * Constructor completo de la clase
     *
     * @param clienteId el identificador de cliente.
     * @param nombre El nombre del cliente. Campo no alphanumerico
     * @param apellido1 El primer apellido del cliente. Obligatorio y campo no
     * alphanumerico
     * @param apellido2 El segundo apellido del cliente. Campo no alphanumerico
     * @param email El email del cliente. Este valor debe ser único para todos
     * los clientes
     * @param telefono El telefono del cliente. Debe comenzar por 6 o 9. Campo
     * no alphanumerico
     * @param dni El dni del cliente. Debe tener una longitud de 9 caracteres
     * maximo
     */
    public Cliente(int clienteId, String nombre, String apellido1, String apellido2, String email, String telefono, String dni) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.telefono = telefono;
        this.dni = dni;
    }
    
    //****************************************
    //Métodos getter y setter a partir de aqui
    //****************************************
    

    /**
     * Método getter de Nombre
     *
     * @return El nombre del cliente
     */
    public int getClienteId() {
        return clienteId;
    }

    /**
     * El metodo seter de clienteId
     *
     * @param clienteId el id del cliente
     */
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * El metodo getter de cliente
     *
     * @return el id del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * El metodo setter de Nombre
     *
     * @param nombre El nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * El metodo getter de Apellidos
     *
     * @return El apellido del cliente
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * El metodo setter de Apellidos
     *
     * @param apellido1 El primer apellido del cliente
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * El método getter del segundo apellido
     *
     * @return el segundo apellido. Puede devolver null
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Metodo setter del segundo apellido
     *
     * @param apellido2 el segundo apellido
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * El metodo getter de email.
     *
     * @return El email del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * El metodo setter de Email
     *
     * @param email El email del cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * El metodo getter de Telefono
     *
     * @return El telefono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * El metodo setter de Telefono
     *
     * @param telefono El telefono del cliente
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * El metodo getter de dni
     *
     * @return El dni del cliente
     */
    public String getDni() {
        return dni;
    }

    /**
     * El metodo setter de dni
     *
     * @param dni El dni del cliente
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

}
