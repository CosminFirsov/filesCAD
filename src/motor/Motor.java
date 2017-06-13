/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que mediante el uso de ficheros, y modifica, inserta borrar registros
 * en datos.txt. Los campos únicos los almacena cada uno en un fichero de
 * índices guardando el objeto HashMap serializado
 *
 * @author Cosmin Firsov
 */
public class Motor {

    private int ultimoClienteID = 1;
    //Connection conexion;

    /**
     * Metodo que se crea los directorios.
     *
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos.
     */
    public void CrearDirectoriosFicheros() throws ExceptionMotor {
        File directorio = new File(".\\maniobra");
        File ficheroDatos = new File(".\\maniobra\\datos.txt");
        File indice1 = new File(".\\maniobra\\indice.txt");
        File indice2 = new File(".\\maniobra\\indice1.txt");

    }

    /**
     * Método que inserta una registro en la tabla coche.
     *
     * @param cliente el coche a insertar
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos.
     */
    public void insertarCliente(Cliente cliente) throws ExceptionMotor {
        CrearDirectoriosFicheros();
        File ficheroDatos = new File(".\\maniobra\\datos.txt");
        RandomAccessFile manejador;
        File ficheroIndice = new File(".\\maniobra\\indice.txt");
        File ficheroIndice1 = new File(".\\maniobra\\indice1.txt");
        FileInputStream lector;
        FileInputStream lector1;
        FileOutputStream escritor;
        FileOutputStream escritor1;
        BufferedInputStream lectorB;
        BufferedInputStream lectorB1;
        BufferedOutputStream escritorB;
        BufferedOutputStream escritorB1;
        ObjectInputStream lectorO = null;
        ObjectInputStream lectorO1 = null;
        ObjectOutputStream escritorO;
        ObjectOutputStream escritorO1;
        HashMap<Integer, Integer> indice = new HashMap();
        HashMap<Integer, String> indice1 = new HashMap();

        try {
            lector = new FileInputStream(ficheroIndice);
            lector1 = new FileInputStream(ficheroIndice1);
            lectorB = new BufferedInputStream(lector);
            lectorB1 = new BufferedInputStream(lector1);
            ultimoClienteID = 1;

            //leer los indices
            lectorO = new ObjectInputStream(lectorB);
            lectorO1 = new ObjectInputStream(lectorB1);
            indice = (HashMap) lectorO.readObject();
            indice1 = (HashMap) lectorO1.readObject();
            ultimoClienteID = determinarUltimoClienteID(indice);
        } catch (FileNotFoundException ex) {
            //Si los indices están vacíos
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
        try {
            //comprobar las reglas de negocio
            comprobarNulosCliente(cliente);
            comprobarCaposVacios(cliente);
            comprobarEmail(cliente);
            comprobarLongitudCampos(cliente);

            if (!indice1.containsValue(cliente.getDni().trim())) {

                manejador = new RandomAccessFile(ficheroDatos, "rw");
                StringBuilder nombre, apellido1, apellido2, email;

                //Lee el ultimo identificador de cliente y usa el siguiente
                //entero para almacenar el siguiente cliente
                
                indice.put(ultimoClienteID + 1, (ultimoClienteID) * 200);
                indice1.put(ultimoClienteID + 1, cliente.getDni());
                manejador.seek((ultimoClienteID * 200));

                nombre = new StringBuilder(cliente.getNombre());
                nombre.setLength(30);
                manejador.writeChars(nombre.toString());
                apellido1 = new StringBuilder(cliente.getApellido1());
                apellido1.setLength(30);
                manejador.writeChars(apellido1.toString());
                apellido2 = new StringBuilder(cliente.getApellido2());
                apellido2.setLength(30);
                manejador.writeChars(apellido2.toString());
                email = new StringBuilder(cliente.getEmail());
                email.setLength(30);
                manejador.writeChars(email.toString());

                manejador.close();
                escritor = new FileOutputStream(ficheroIndice);
                escritor1 = new FileOutputStream(ficheroIndice1);
                escritorB = new BufferedOutputStream(escritor);
                escritorB1 = new BufferedOutputStream(escritor1);
                escritorO = new ObjectOutputStream(escritorB);
                escritorO1 = new ObjectOutputStream(escritorB1);
                escritorO.writeObject(indice);
                escritorO1.writeObject(indice1);
                escritorO.close();
                escritorO1.close();
                escritorB.close();
                escritorB1.close();
                escritor.close();
                escritor1.close();
                indice.clear();
                indice1.clear();
            } else {
                throw new ExceptionMotor(0, "Violacion de unique contraint", "Existe un cliente con ese DNI", "");
            }

        } catch (FileNotFoundException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        } catch (IOException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        }

    }

    /**
     * Método que lee texto escrito en el fichero datos.txt y borra los
     * registros deseados. Los campos únicos son almacenados cada uno en un
     * indice mediante un HashMap
     *
     * @param clienteId: el identificador del coche a eliminar
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos
     */
    public void eliminarClientes(int clienteId) throws ExceptionMotor {
        CrearDirectoriosFicheros();
        File ficheroDatos = new File(".\\maniobra\\datos.txt");
        RandomAccessFile manejador;
        File ficheroIndice = new File(".\\maniobra\\indice.txt");
        File ficheroIndice1 = new File(".\\maniobra\\indice1.txt");
        FileInputStream lector;
        FileInputStream lector1;
        FileOutputStream escritor;
        FileOutputStream escritor1;
        BufferedInputStream lectorB;
        BufferedInputStream lectorB1;
        BufferedOutputStream escritorB;
        BufferedOutputStream escritorB1;
        ObjectInputStream lectorO = null;
        ObjectInputStream lectorO1 = null;
        ObjectOutputStream escritorO;
        ObjectOutputStream escritorO1;
        HashMap<Integer, Integer> indice = new HashMap();
        HashMap<Integer, String> indice1 = new HashMap();

        try {
            lector = new FileInputStream(ficheroIndice);
            lector1 = new FileInputStream(ficheroIndice1);
            lectorB = new BufferedInputStream(lector);
            lectorB1 = new BufferedInputStream(lector1);

            lectorO = new ObjectInputStream(lectorB);
            lectorO1 = new ObjectInputStream(lectorB1);
            indice = (HashMap) lectorO.readObject();
            indice1 = (HashMap) lectorO1.readObject();
        } catch (FileNotFoundException ex) {
            //Si los indices están vacíos
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
        try {
            if (indice.containsKey(clienteId)) {
                manejador = new RandomAccessFile(ficheroDatos, "rw");
                StringBuilder nombre, apellido1, apellido2, email;

                //Actualizo los dos indices eliminando los que ya no hacen falta
                int posicion = indice.get(clienteId);
                indice.remove(clienteId);
                indice1.remove(clienteId);
               

                manejador.seek(posicion);
                nombre = new StringBuilder("");
                nombre.setLength(30);
                manejador.writeChars(nombre.toString());
                apellido1 = new StringBuilder("");
                apellido1.setLength(30);
                manejador.writeChars(apellido1.toString());
                apellido2 = new StringBuilder("");
                apellido2.setLength(30);
                manejador.writeChars(apellido2.toString());
                email = new StringBuilder("");
                email.setLength(30);
                manejador.writeChars(email.toString());

                manejador.close();
                escritor = new FileOutputStream(ficheroIndice);
                escritor1 = new FileOutputStream(ficheroIndice1);
                escritorB = new BufferedOutputStream(escritor);
                escritorB1 = new BufferedOutputStream(escritor1);
                escritorO = new ObjectOutputStream(escritorB);
                escritorO1 = new ObjectOutputStream(escritorB1);
                escritorO.writeObject(indice);
                escritorO1.writeObject(indice1);
                escritorO.close();
                escritorO1.close();
                escritorB.close();
                escritorB1.close();
                escritor.close();
                escritor1.close();
                indice.clear();
                indice1.clear();
            } else {
                throw new ExceptionMotor(0, "No hay clientes con ese ese identificador de cliente", "No hay clientes con ese identificador de cliente", "");
            }

        } catch (FileNotFoundException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        } catch (IOException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        }

    }

    /**
     * Método que lee texto escrito en el fichero datos.txt y los modifica. Los
     * campos únicos son almacenados cada uno en un indice mediante un HashMap
     *
     * @param cliente los datos del coche a modificar
     * @return: el numero de filas afectadas
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos
     */
    public void modificarCliente(Cliente cliente) throws ExceptionMotor {
        CrearDirectoriosFicheros();
        File ficheroDatos = new File(".\\maniobra\\datos.txt");
        RandomAccessFile manejador;
        File ficheroIndice = new File(".\\maniobra\\indice.txt");
        File ficheroIndice1 = new File(".\\maniobra\\indice1.txt");
        FileInputStream lector;
        FileInputStream lector1;
        FileOutputStream escritor;
        FileOutputStream escritor1;
        BufferedInputStream lectorB;
        BufferedInputStream lectorB1;
        BufferedOutputStream escritorB;
        BufferedOutputStream escritorB1;
        ObjectInputStream lectorO = null;
        ObjectInputStream lectorO1 = null;
        ObjectOutputStream escritorO;
        ObjectOutputStream escritorO1;
        HashMap<Integer, Integer> indice = new HashMap();
        HashMap<Integer, String> indice1 = new HashMap();

        try {
            lector = new FileInputStream(ficheroIndice);
            lector1 = new FileInputStream(ficheroIndice1);
            lectorB = new BufferedInputStream(lector);
            lectorB1 = new BufferedInputStream(lector1);

            lectorO = new ObjectInputStream(lectorB);
            lectorO1 = new ObjectInputStream(lectorB1);
            indice = (HashMap) lectorO.readObject();
            indice1 = (HashMap) lectorO1.readObject();
        } catch (FileNotFoundException ex) {
            //Si los indices están vacíos
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
        try {
            //comprobar las reglas de negocio
            comprobarNulosCliente(cliente);
            comprobarCaposVacios(cliente);
            comprobarEmail(cliente);
            comprobarLongitudCampos(cliente);

            if (indice.containsKey(cliente.getClienteId())) {
                if (!indice1.containsValue(cliente.getDni().trim())) {
                    manejador = new RandomAccessFile(ficheroDatos, "rw");
                    StringBuilder nombre, apellido1, apellido2, email;

                    //Actualizo el indice de dni eliminando el viejo e insertando el nuevo
                    //con el mismo cliente_id
                    int posicion = indice.get(cliente.getClienteId());
                    indice1.remove(cliente.getClienteId());
                    indice1.put(cliente.getClienteId(), cliente.getDni());

                    manejador.seek(posicion);
                    nombre = new StringBuilder(cliente.getNombre());
                    nombre.setLength(30);
                    manejador.writeChars(nombre.toString());
                    apellido1 = new StringBuilder(cliente.getApellido1());
                    apellido1.setLength(30);
                    manejador.writeChars(apellido1.toString());
                    apellido2 = new StringBuilder(cliente.getApellido2());
                    apellido2.setLength(30);
                    manejador.writeChars(apellido2.toString());
                    email = new StringBuilder(cliente.getEmail());
                    email.setLength(30);
                    manejador.writeChars(email.toString());

                    manejador.close();
                    escritor = new FileOutputStream(ficheroIndice);
                    escritor1 = new FileOutputStream(ficheroIndice1);
                    escritorB = new BufferedOutputStream(escritor);
                    escritorB1 = new BufferedOutputStream(escritor1);
                    escritorO = new ObjectOutputStream(escritorB);
                    escritorO1 = new ObjectOutputStream(escritorB1);
                    escritorO.writeObject(indice);
                    escritorO1.writeObject(indice1);
                    escritorO.close();
                    escritorO1.close();
                    escritorB.close();
                    escritorB1.close();
                    escritor.close();
                    escritor1.close();
                    indice.clear();
                    indice1.clear();
                } else {
                throw new ExceptionMotor(0, "Ya existe un cliente con ese dni", "Ya existe un cliente con ese dni", "");
            }
            } else {
                throw new ExceptionMotor(0, "No existe un cliente con ese identificador", "No existe un cliente con ese identificador", "");
            }

        } catch (FileNotFoundException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        } catch (IOException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        }

    }

    /**
     * Método que lee texto escrito en el fichero datos.txt. Lee un cliente
     * mediante su identificador. Los campos únicos son almacenados cada uno en
     * un indice mediante un HashMap
     *
     * @param clienteId el identificador de coche
     * @return devuelve el coche con cliente_id = clienteId
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos
     */
    public Cliente leerCliente(int clienteId) throws ExceptionMotor {
        CrearDirectoriosFicheros();
        Cliente cliente = new Cliente(clienteId, "", "", "", "", "", "");
        File ficheroDatos = new File(".\\maniobra\\datos.txt");
        RandomAccessFile manejador;
        File ficheroIndice = new File(".\\maniobra\\indice.txt");
        File ficheroIndice1 = new File(".\\maniobra\\indice1.txt");
        FileInputStream lector;
        FileInputStream lector1;
        FileOutputStream escritor;
        FileOutputStream escritor1;
        BufferedInputStream lectorB;
        BufferedInputStream lectorB1;
        BufferedOutputStream escritorB;
        BufferedOutputStream escritorB1;
        ObjectInputStream lectorO = null;
        ObjectInputStream lectorO1 = null;
        ObjectOutputStream escritorO;
        ObjectOutputStream escritorO1;
        HashMap<Integer, Integer> indice = new HashMap();
        HashMap<Integer, String> indice1 = new HashMap();

        try {
            lector = new FileInputStream(ficheroIndice);
            lector1 = new FileInputStream(ficheroIndice1);
            lectorB = new BufferedInputStream(lector);
            lectorB1 = new BufferedInputStream(lector1);

            lectorO = new ObjectInputStream(lectorB);
            lectorO1 = new ObjectInputStream(lectorB1);
            indice = (HashMap) lectorO.readObject();
            indice1 = (HashMap) lectorO1.readObject();
        } catch (FileNotFoundException ex) {
            //Si los indices están vacíos
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
        try {
            manejador = new RandomAccessFile(ficheroDatos, "r");
            char nombre[], apellido1[], apellido2[], email[];

            manejador.seek(indice.get(clienteId));
            nombre = new char[30];
            for (int i = 0; i < 30; i++) {
                nombre[i] = manejador.readChar();
            }
            cliente.setNombre(new String(nombre).trim());
            apellido1 = new char[30];
            for (int i = 0; i < 30; i++) {
                apellido1[i] = manejador.readChar();
            }
            cliente.setApellido1(new String(apellido1).trim());
            apellido2 = new char[30];
            for (int i = 0; i < 30; i++) {
                apellido2[i] = manejador.readChar();
            }
            cliente.setApellido2(new String(apellido2).trim());
            email = new char[30];
            for (int i = 0; i < 30; i++) {
                email[i] = manejador.readChar();
            }
            cliente.setEmail(new String(email).trim());

            cliente.setDni(indice1.get(clienteId).trim());

        } catch (FileNotFoundException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        } catch (IOException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        }

        return cliente;
    }

    /**
     * Método que lee texto escrito en el fichero datos.txt. Lee todos los
     * clientes. Los campos únicos son almacenados cada uno en un indice
     * mediante un HashMap
     *
     * @return un ArrayList de coche con todos los clientes
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos
     */
    public ArrayList<Cliente> leerClientes() throws ExceptionMotor {
        CrearDirectoriosFicheros();
        ArrayList<Cliente> clientes = new ArrayList<>();

        CrearDirectoriosFicheros();
        File ficheroDatos = new File(".\\maniobra\\datos.txt");
        RandomAccessFile manejador;
        File ficheroIndice = new File(".\\maniobra\\indice.txt");
        File ficheroIndice1 = new File(".\\maniobra\\indice1.txt");
        FileInputStream lector;
        FileInputStream lector1;
        FileOutputStream escritor;
        FileOutputStream escritor1;
        BufferedInputStream lectorB;
        BufferedInputStream lectorB1;
        BufferedOutputStream escritorB;
        BufferedOutputStream escritorB1;
        ObjectInputStream lectorO = null;
        ObjectInputStream lectorO1 = null;
        ObjectOutputStream escritorO;
        ObjectOutputStream escritorO1;
        HashMap<Integer, Integer> indice = new HashMap();
        HashMap<Integer, String> indice1 = new HashMap();

        try {
            lector = new FileInputStream(ficheroIndice);
            lector1 = new FileInputStream(ficheroIndice1);
            lectorB = new BufferedInputStream(lector);
            lectorB1 = new BufferedInputStream(lector1);

            lectorO = new ObjectInputStream(lectorB);
            lectorO1 = new ObjectInputStream(lectorB1);
            indice = (HashMap) lectorO.readObject();
            indice1 = (HashMap) lectorO1.readObject();
        } catch (FileNotFoundException ex) {
            //Si los indices están vacíos
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
        try {
            manejador = new RandomAccessFile(ficheroDatos, "r");

            Iterator iteradorPosicion = indice.values().iterator();
            Iterator iteradorClienteId = indice.keySet().iterator();
            try {
                while (iteradorPosicion.hasNext()) {
                    char nombre[], apellido1[], apellido2[], email[];
                    int clienteId = (Integer) iteradorClienteId.next();
                    manejador.seek((Integer) iteradorPosicion.next());
                    nombre = new char[30];
                    for (int i = 0; i < 30; i++) {
                        nombre[i] = manejador.readChar();
                    }
                    apellido1 = new char[30];
                    for (int i = 0; i < 30; i++) {
                        apellido1[i] = manejador.readChar();
                    }
                    apellido2 = new char[30];
                    for (int i = 0; i < 30; i++) {
                        apellido2[i] = manejador.readChar();
                    }
                    email = new char[30];
                    for (int i = 0; i < 30; i++) {
                        email[i] = manejador.readChar();
                    }
                    clientes.add(new Cliente(clienteId, new String(nombre).trim(), new String(apellido1).trim(), new String(apellido2).trim(), new String(email).trim(), "", indice1.get(clienteId).trim()));
                }
            } catch (EOFException ex) {
                throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
            } catch (IOException ex) {
                throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
            }

        } catch (FileNotFoundException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        }

        return clientes;
    }

    /**
     * Método que lee texto escrito en el fichero datos.txt. Lee todos los
     * clientes siguiente un filtro y un orden. Los campos únicos son
     * almacenados cada uno en un indice mediante un HashMap
     *
     * @param filtro Las posibles opciones para filtro[0] son: cliente_id,
     * nombre, apellido1, apellido2, dni. En filtro[1] ira el texto a filtrar
     * @param orden Las posibles opciones son: cliente_id, nombre, apellido1,
     * apellido2, dni.
     * @return un ArrayList de coche con todos los clientes
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos
     */
    public ArrayList<Cliente> leerClientes(String[] filtro, String orden) throws ExceptionMotor {
        CrearDirectoriosFicheros();
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Cliente> clientesOrdenados = new ArrayList();
        CrearDirectoriosFicheros();
        File ficheroDatos = new File(".\\maniobra\\datos.txt");
        RandomAccessFile manejador;
        File ficheroIndice = new File(".\\maniobra\\indice.txt");
        File ficheroIndice1 = new File(".\\maniobra\\indice1.txt");
        FileInputStream lector;
        FileInputStream lector1;
        FileOutputStream escritor;
        FileOutputStream escritor1;
        BufferedInputStream lectorB;
        BufferedInputStream lectorB1;
        BufferedOutputStream escritorB;
        BufferedOutputStream escritorB1;
        ObjectInputStream lectorO = null;
        ObjectInputStream lectorO1 = null;
        ObjectOutputStream escritorO;
        ObjectOutputStream escritorO1;
        HashMap<Integer, Integer> indice = new HashMap();
        HashMap<Integer, String> indice1 = new HashMap();

        try {
            lector = new FileInputStream(ficheroIndice);
            lector1 = new FileInputStream(ficheroIndice1);
            lectorB = new BufferedInputStream(lector);
            lectorB1 = new BufferedInputStream(lector1);

            lectorO = new ObjectInputStream(lectorB);
            lectorO1 = new ObjectInputStream(lectorB1);
            indice = (HashMap) lectorO.readObject();
            indice1 = (HashMap) lectorO1.readObject();
        } catch (FileNotFoundException ex) {
            //Si los indices están vacíos
        } catch (ClassNotFoundException ex) {
        } catch (IOException ex) {
        }
        try {
            manejador = new RandomAccessFile(ficheroDatos, "r");

            Iterator iteradorPosicion = indice.values().iterator();
            Iterator iteradorClienteId = indice.keySet().iterator();
            try {
                while (iteradorPosicion.hasNext()) {
                    char nombre[], apellido1[], apellido2[], email[];
                    int clienteId = (Integer) iteradorClienteId.next();
                    manejador.seek((Integer) iteradorPosicion.next());
                    nombre = new char[30];
                    for (int i = 0; i < 30; i++) {
                        nombre[i] = manejador.readChar();
                    }
                    apellido1 = new char[30];
                    for (int i = 0; i < 30; i++) {
                        apellido1[i] = manejador.readChar();
                    }
                    apellido2 = new char[30];
                    for (int i = 0; i < 30; i++) {
                        apellido2[i] = manejador.readChar();
                    }
                    email = new char[30];
                    for (int i = 0; i < 30; i++) {
                        email[i] = manejador.readChar();
                    }

                    //Evaluo que cliente guardo en funcion del filtro que se me pasa
                    Cliente cliente = new Cliente(clienteId, new String(nombre).trim(), new String(apellido1).trim(), new String(apellido2).trim(), new String(email).trim(), "", indice1.get(clienteId).trim());
                    switch (filtro[0]) {
                        case "cliente_id":
                            if (cliente.getClienteId() == Integer.parseInt(filtro[1].trim())) {
                                clientes.add(cliente);
                            }
                            break;
                        case "nombre":
                            if (cliente.getNombre().trim().equals(filtro[1].trim())) {
                                clientes.add(cliente);
                            }
                            break;
                        case "apellido1":
                            if (cliente.getApellido1().trim().equals(filtro[1].trim())) {
                                clientes.add(cliente);
                            }
                            break;
                        case "apellido2":
                            if (cliente.getApellido2().trim().equals(filtro[1].trim())) {
                                clientes.add(cliente);
                            }
                            break;
                        case "dni":
                            if (cliente.getDni().trim().equals(filtro[1].trim())) {
                                clientes.add(cliente);
                            }
                            break;
                    }

                }
                
                //Ordeno los registros segun el orden deseado
                //int posicionMenor = 0;
                switch (orden) {
                    case "cliente_id":
                        for (int i = 0; i < clientes.size(); i++) {
                            for (int j = (i + 1); j < clientes.size(); j++) {

                                if (clientes.get(i).getClienteId() > clientes.get(j).getClienteId()) {
                                    Cliente cliente = clientes.get(j);
                                    clientes.remove(cliente);
                                    clientes.add(0, cliente);
                                }

                            }
                        }
                        break;
                    case "nombre":
                        for (int i = 0; i < clientes.size(); i++) {
                            for (int j = (i + 1); j < clientes.size(); j++) {
                                if (clientes.get(i).getNombre().trim().compareTo(clientes.get(j).getNombre().trim()) > 0) {
                                    Cliente cliente = clientes.get(j);
                                    clientes.remove(cliente);
                                    clientes.add(0, cliente);
                                }
                            }
                        }
                        break;
                    case "apellido1":
                        for (int i = 0; i < clientes.size(); i++) {
                            for (int j = (i + 1); j < clientes.size(); j++) {
                                if (clientes.get(i).getApellido1().trim().compareTo(clientes.get(j).getApellido1().trim()) > 0) {
                                    Cliente cliente = clientes.get(j);
                                    clientes.remove(cliente);
                                    clientes.add(0, cliente);
                                }
                            }
                        }
                        break;
                    case "apellido2":
                        for (int i = 0; i < clientes.size(); i++) {
                            for (int j = (i + 1); j < clientes.size(); j++) {
                                if (clientes.get(i).getApellido2().trim().compareTo(clientes.get(j).getApellido2().trim()) > 0) {
                                    Cliente cliente = clientes.get(j);
                                    clientes.remove(cliente);
                                    clientes.add(0, cliente);
                                }
                            }
                        }
                        break;
                    case "dni":
                        for (int i = 0; i < clientes.size(); i++) {
                            for (int j = (i + 1); j < clientes.size(); j++) {
                                if (clientes.get(i).getDni().trim().compareTo(clientes.get(j).getDni().trim()) > 0) {
                                    Cliente cliente = clientes.get(j);
                                    clientes.remove(cliente);
                                    clientes.add(0, cliente);
                                }
                            }
                        }
                        break;

                }

            } catch (EOFException ex) {
                throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
            } catch (IOException ex) {
                throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
            }

        } catch (FileNotFoundException ex) {
            throw new ExceptionMotor(0, ex.getMessage(), "Error en el sistema. Consulta con el administrador", "");
        }

        return clientes;
    }

    /**
     * Metodo que dado un indice, lo lee y devuelve la key mas alta que se usará
     * luego como identificador
     *
     * @param indice HashMap con los campos únicos cliente_id
     * @return el valor mas alto de la coleccion de keys
     */
    private int determinarUltimoClienteID(HashMap indice) {
        int maxID = 0;
        Iterator iterator = indice.keySet().iterator();
        while (iterator.hasNext()) {
            int nextID = Integer.parseInt(iterator.next().toString());
            if (maxID < nextID) {
                maxID = nextID;
            }
        }

        return maxID;

    }

    public void comprobarLongitudCampos(Cliente cliente) throws ExceptionMotor {
        try {
            cliente.getApellido1().substring(31);
            throw new ExceptionMotor(0, "Longitud de los campos demasiada larga", "Error. Los campos tienen una longitud maxima de 30 caracteres, dni 9 caracteres", "");
        } catch (Exception ex) {
        }
        try {
            cliente.getApellido2().substring(31);
            throw new ExceptionMotor(0, "Longitud de los campos demasiada larga", "Error. Los campos tienen una longitud maxima de 30 caracteres, dni 9 caracteres", "");
        } catch (Exception ex) {
        }
        try {
            cliente.getNombre().substring(31);
            throw new ExceptionMotor(0, "Longitud de los campos demasiada larga", "Error. Los campos tienen una longitud maxima de 30 caracteres, dni 9 caracteres", "");
        } catch (Exception ex) {
        }
        try {
            cliente.getEmail().substring(31);
            throw new ExceptionMotor(0, "Longitud de los campos demasiada larga", "Error. Los campos tienen una longitud maxima de 30 caracteres, dni 9 caracteres", "");
        } catch (Exception ex) {
        }
        try {
            cliente.getDni().substring(9);
            throw new ExceptionMotor(0, "Longitud de los campos demasiada larga", "Error. Los campos tienen una longitud maxima de 30 caracteres, dni 9 caracteres", "");
        } catch (Exception ex) {
        }

    }

    /**
     * Metodo que comprueba las reglas de negocio. El email tiene que contener @
     * o acabar en .com o .es
     *
     * @param cliente El cliente que se le comprueba su email
     * @throws ExceptionMotor excepcion que integra el mensaje de error al
     * usuario, el nombre de error y el mensaje de error que nos ha devuelto la
     * base de datos
     */
    public void comprobarEmail(Cliente cliente) throws ExceptionMotor {
        if (!cliente.getEmail().trim().contains("@")) {
            throw new ExceptionMotor(0, "El email no contiene @ ni acaba con .com o .es", "El email no contiene @ ni acaba con .com o .es", "");
        }
        if (!cliente.getEmail().trim().toLowerCase().endsWith(".com") && !cliente.getEmail().trim().toLowerCase().endsWith(".es")) {
            throw new ExceptionMotor(0, "El email no contiene @ o no acaba con .com o .es", "El email no contiene @ o no acaba con .com o .es", "");
        }
    }
    
    public void comprobarCaposVacios(Cliente cliente) throws ExceptionMotor{
        if (cliente.getNombre().equals("") || cliente.getApellido1().equals("") || cliente.getEmail().equals("") || cliente.getDni().equals("")){
            throw new ExceptionMotor(0, "Verificacion campos vacíos", "Los siguientes campos no pueden estar vacíos: nombre, apellido1, email y dni", "");
        }
    }

    private void comprobarNulosCliente(Cliente cliente) throws ExceptionMotor {
        if (cliente != null) {
            if (cliente.getNombre() == null || cliente.getApellido1() == null || cliente.getDni() == null || cliente.getEmail() == null) {
                throw new ExceptionMotor(0, "Verificacion campos nulos", "Los siguientes campos son obligatorios: nombre, apellido1, email y dni", "");
            }
            if (cliente.getApellido2() == null) {
                cliente.setApellido2("");
            }
        }
    }

}
