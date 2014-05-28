/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serv;

/**
 *
 * @author niko
 */

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
public class Serv {

    public static int port = 4000;
    
    public static void main(String[] args) throws IOException {
        //Servidor en espera de clientes
        try{
            ServerSocket s = new ServerSocket(port);
            while (true){
                Socket cl= s.accept();
                Clientes Tcl= new Clientes(cl);
                Tcl.start();
            }
        }catch(IOException e){
            System.out.println( e );
        }
    }
    
}
