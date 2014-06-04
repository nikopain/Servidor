/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidortcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServidorTcp {
    
    public static void main(String[] args) throws IOException {
        ServerSocket skt= new ServerSocket(5000);
        //escuchando
        String mensaje;
        String protocolo;
        while(true){
            Socket sk2= skt.accept();
            BufferedReader in= new BufferedReader(new InputStreamReader(sk2.getInputStream()));
            DataOutputStream os = new DataOutputStream(sk2.getOutputStream());
            mensaje= in.readLine();
            System.out.println(mensaje);
            
            StringTokenizer tkn= new StringTokenizer(mensaje);
            protocolo=tkn.nextToken();
            
            if(tkn.countTokens() >= 2&&protocolo.equals("enviar")){
                System.out.println("enviado");
                String user= tkn.nextToken();
                String ip= tkn.nextToken();
                String msje= tkn.nextToken();
                
                FileWriter f= null;
                PrintWriter pw=null;
                try {
                    f = new FileWriter("mensaje.txt",true);
                    pw= new PrintWriter(f);
                    pw.println(user + " "+ip+ " "+msje+" "+ '\n');
                }catch(Exception e){
                    System.out.println(e);
                }
            }
            else if(tkn.countTokens() >= 2&&protocolo.equals("recibido")){
                
            }
        }   
    }
    
}
