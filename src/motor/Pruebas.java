/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oracle
 */
public class Pruebas {

    public static void main(String[] args) {
        Motor motor = new Motor();
        try {
            /*
            motor.insertarCliente(new Cliente(4, "1Cosmin1",     "Firsov1",   "bbb",  "F1@gm.es",  "6aa", "k1"));
            motor.insertarCliente(new Cliente(4, "2Cosmin2",    "Firsov11", "bbb",   "F11@gm.com",  "6aa", "kk1"));
            motor.insertarCliente(new Cliente(4, "3Cosmin3",   "Firsov111", "bbb",  "F111@gm.es",    "6aa", "kk1i"));
            motor.insertarCliente(new Cliente(4, "4Cosmin4",  "Firsov1111", "bbb", "F1111@gm.es",  "6aa", "kki1k"));
            motor.insertarCliente(new Cliente(4, "5Cosmin5", "Firsov11111", "bbb","F11111@gm.es",  "6aa", "kkik1i"));
            motor.insertarCliente(new Cliente(4, "6Cosmin6", "Firsov11111", "bbb","F11111@gm.es", "6aa", "kkiki1k"));
            motor.insertarCliente(new Cliente(4, "7Cosmin7", "Firsov11111", "bbb","F11111@gm.es",  "6aa", "kkik1iki"));
            */
            
            //motor.insertarCliente(new Cliente(7, "Cosmin1",     "Firsov1",     "",     "te€@.es", "", "a"));
            
            
            
            
            motor.modificarCliente(new Cliente(55,"be","eeeeeertooo"," !!!!","kker@.es","9@.es","a"));
            //motor.eliminarClientes(2);
            /*
            //motor.insertarCoche(new Coche(1,"kk","kkkk","kkkk",new Date(System.currentTimeMillis()+100000000),new Cliente(5,"kk","kk","","kker",null,"kkik")));
            //motor.modificarCoche(new Coche(8,"cer","cerer","cerer",new Date(System.currentTimeMillis()+100000000),new Cliente(1,"kkkkkkk","kkkkkkkkk","","kkkkkkkkker","600","kkkkkkkkkik")));
            //motor.eliminarCoche(8);
            /*
            Cliente cliente = motor.leerCliente(20);
            System.out.printf("%4s %20s %20s %20s %20s %9s %9s ", "ID", "Nombre cliente", "Primer apellido", "Segundo apellido",
                    "Email", "Telefono", "DNI");
            System.out.println("");
            System.out.printf("%4d %20s %20s %20s %20s %9s %9s ", cliente.getClienteId(), cliente.getNombre().trim(), cliente.getApellido1().trim(), cliente.getApellido2().trim(),
                    cliente.getEmail().trim(), cliente.getTelefono().trim(), cliente.getDni().trim());
            */
            /*
            Coche coche = motor.leerCoche(1);
            
            System.out.printf("%4s %15s %15s %7s %10s %20s %20s %9s \n", "ID", "Marca", "Modelo", "Matricula", "ITV", "Nombre cliente", "Primer apellido",
                                            "DNI");
            
            System.out.printf("%4d %15s %15s %7s %tF %20s %20s %9s \n", coche.getCocheId(),
                        coche.getMarca(), coche.getModelo(), coche.getMatricula(),
                        coche.getItv(), coche.getCliente().getNombre(),
                        coche.getCliente().getApellido1(), coche.getCliente().getDni());
             */
 /*
            ArrayList<Coche> coches = motor.leerCoches();

            System.out.printf("%4s %15s %15s %7s %10s %20s %20s %9s \n", "ID", "Marca", "Modelo", "Matricula", "ITV", "Nombre cliente", "Primer apellido",
                                            "DNI");
            while (!coches.isEmpty()) {
                System.out.printf("%4d %15s %15s %7s %tF %20s %20s %9s \n", coches.get(0).getCocheId(),
                        coches.get(0).getMarca(), coches.get(0).getModelo(), coches.get(0).getMatricula(),
                        coches.get(0).getItv(), coches.get(0).getCliente().getNombre(),
                        coches.get(0).getCliente().getApellido1(), coches.get(0).getCliente().getDni());
                coches.remove(0);
            }*/
            String[] filtro = {"apellido2","bbb"};
            String orden = "apellido1";
            ArrayList<Cliente> clientes = motor.leerClientes();
            
            System.out.println("");
            System.out.printf("%4s %20s %20s %20s %20s %9s %9s \n", "ID", "Nombre cliente", "Primer apellido", "Segundo apellido",
                    "Email", "Telefono", "DNI");

            while (!clientes.isEmpty()) {
                System.out.println("");
                System.out.printf("%4d %20s %20s %20s %20s %9s %9s \n", clientes.get(0).getClienteId(), clientes.get(0).getNombre().trim(), clientes.get(0).getApellido1().trim(), clientes.get(0).getApellido2().trim(),
                        clientes.get(0).getEmail().trim(), clientes.get(0).getTelefono().trim(), clientes.get(0).getDni().trim());
                clientes.remove(0);
            }
        } catch (ExceptionMotor ex) {
            System.out.println(ex.getMensajeErrorUsuario());
        }/* catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    }
}
