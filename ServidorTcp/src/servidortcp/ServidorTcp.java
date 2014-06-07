/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidortcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
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
                System.out.println(ip + " "+user+ " "+msje+" "+ '\n');
                
                Writer writer = null;

                try {

                    writer = new BufferedWriter(new OutputStreamWriter(
                          new FileOutputStream("mensaje.txt",true), "utf-8"));
                        writer.write(ip+ " ");
                        writer.write(user+ " ");
                        writer.write(msje);
                        writer.write("\r\n");
                } catch (IOException ex) {
                  // report
                } finally {
                   try {writer.close();} catch (Exception ex) {}
                }
            }
            else if(tkn.countTokens() >= 2&&protocolo.equals("recibido")){
                //IP del cliente;
                String  user=tkn.nextToken();
                        
                FileInputStream msj= new FileInputStream("mensaje.txt");
                DataInputStream message = new DataInputStream(msj);
                BufferedReader br= new BufferedReader(new InputStreamReader(message));
                StringTokenizer tk=null;
                

                //leer las lineas del archivo y encontrar la ip destino = a user                
                String linea;
                while((linea=br.readLine())!=null){
                    tk=new StringTokenizer(linea);
                    if(tk.nextToken().equals(user)){
                        os.writeBytes(tk.nextToken()+" "+tk.nextToken());
                    }
                }
                br.close();
            }
        }   
    }
    
}
