/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

import java.io.Serializable;
import java.sql.Date;

/**
 * Clase coche. Sirve para almacenar los datos de un coche.
 *
 * @author Cosmin Firsov
 */
public class Coche implements Serializable{

    int cocheId;
    String marca;
    String modelo;
    String matricula;
    Date itv;
    Cliente cliente;

    /**
     * Constructor vacio de la clase
     */
    public Coche() {
    }

    /**
     * Constructor de la clase. Inicializa todas los atributos
     *
     * @param cocheId el identificador de coche. Parametro obligatorio
     * @param marca la marca del coche. Parametro obligatorio
     * @param modelo el modelo del coche
     * @param matricula la matricula del coche. Es un cambpo obliatorio y único
     * @param itv la fecha de la itv del coche. "yyyy-mm-dd"
     * @param cliente el objeto cliente
     */
    public Coche(int cocheId, String marca, String modelo, String matricula, Date itv, Cliente cliente) {
        this.cliente = cliente;
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.itv = itv;
        this.cocheId = cocheId;
    }

    //****************************************
    //Métodos getter y setter a partir de aqui
    //****************************************
    
    
    /**
     * Metodo getter de marca
     *
     * @return la marca del coche
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Metodo setter de marca
     *
     * @param marca la marca del coche
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Metodo getter de modelo
     *
     * @return el modelo del coche
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * El metodo setter de modelo
     *
     * @param modelo El modelo del coche
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * El metodo getter de matricula
     *
     * @return la matricula del coche
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * El metodo setter de matricula
     *
     * @param matricula la matricula del coche
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * El metodo getter de itv
     *
     * @return la itv del coche
     */
    public Date getItv() {
        return itv;
    }

    /**
     * El metodo setter de itv
     *
     * @param itv la itv del coche
     */
    public void setItv(Date itv) {
        this.itv = itv;
    }

    /**
     * El metodo getter de cliente
     *
     * @return el cliente de coche
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * El metodo setter de cliente
     *
     * @param cliente el cliente de coche
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * El metodo getter de cocheId
     *
     * @return el identificador de coche
     */
    public int getCocheId() {
        return cocheId;
    }

    /**
     * El metodo setter de coche
     *
     * @param cocheId el identificador de coche
     */
    public void setCocheId(int cocheId) {
        this.cocheId = cocheId;
    }

}
