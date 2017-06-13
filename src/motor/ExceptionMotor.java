/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

/**Clase que maneja las excepciones que provoca la base de datos. Guarda informacion
 * del error para mandar al usuario (String mensajeErrorUsuario) y mensaje de error
 * para el dba (String mensajeErrorOracle) y el codigo de error (int codigoErrorOracle)
 *
 * @author Cosmin Firsov
 */
public class ExceptionMotor extends Exception{
    int codigoErrorAdministrador;
    String mensajeErrorAdministrador;
    String mensajeErrorUsuario;
    String sentenciaSQL;

    /**
     * Constructor vacio de la clase
     */
    public ExceptionMotor() {
    }

    /**
     * Constructor de la clase
     * @param codigoErrorOracle El condigo de error que devuelve oracle
     * @param mensajeErrorOracle El mensaje de error que devuelve oracle
     * @param mensajeErrorUsuario El mensaje de error que se le pasa al usuario
     * @param sentenciaSQL La sentencia de Oracleque produjo la excepcion
     */
    public ExceptionMotor(int codigoErrorOracle, String mensajeErrorOracle, String mensajeErrorUsuario, String sentenciaSQL) {
        this.codigoErrorAdministrador = codigoErrorOracle;
        this.mensajeErrorAdministrador = mensajeErrorOracle;
        this.mensajeErrorUsuario = mensajeErrorUsuario;
        this.sentenciaSQL = sentenciaSQL;
    }

    //****************************************
    //Métodos setter y getter a partir de aquí
    //****************************************
    
    /**
     * Metodo getter del codigo del error
     * @return el codigo de error de la base de datos
     */
    public int getCodigoErrorAdministrador() {
        return codigoErrorAdministrador;
    }

    /**
     * Metodo setter del codigo del error
     * @param codigoErrorAdministrador el codigo de error de la base de datos
     */
    public void setCodigoErrorAdministrador(int codigoErrorAdministrador) {
        this.codigoErrorAdministrador = codigoErrorAdministrador;
    }

    /**
     * El metodo getter del mensaje para el dba
     * @return el mensaje de error para el dba
     */
    public String getMensajeErrorAdministrador() {
        return mensajeErrorAdministrador;
    }

    /**
     * El metodo setter del mensaje de error para el dba
     * @param mensajeErrorAdministrador el mensaje de error del dba
     */
    public void setMensajeErrorAdministrador(String mensajeErrorAdministrador) {
        this.mensajeErrorAdministrador = mensajeErrorAdministrador;
    }

    /**
     * Metodo getter del mensaje de error para el usuario
     * @return el mensaje de error del usuario
     */
    public String getMensajeErrorUsuario() {
        return mensajeErrorUsuario;
    }

    /**
     * El metodo setter del mensaje de error para el susuario
     * @param mensajeErrorUsuario el mensaje de error para el usuario
     */
    public void setMensajeErrorUsuario(String mensajeErrorUsuario) {
        this.mensajeErrorUsuario = mensajeErrorUsuario;
    }

    /**
     * El metodo getter de la sentencia del sql
     * @return la sentencia sql
     */
    public String getSentenciaSQL() {
        return sentenciaSQL;
    }

    /**
     * El metodo setter de la sentencia sql
     * @param sentenciaSQL la sentencia sql
     */
    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }
}
