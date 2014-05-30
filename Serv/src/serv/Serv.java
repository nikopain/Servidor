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
import client.Client; 
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
public class Serv implements Runnable {
    public static int port = 4000;
    Socket client;
    private static int count=0;
    private int ID;
    Serv(Socket socket, int id) {
            this.client = socket;
            this.ID = id;
    }
 
    public static void main(String argv[]) throws Exception{ 
                 
        try{
            ServerSocket Server = new ServerSocket(port);
            System.out.println("ServerSocket Initialized");
            while(true){             
                Socket client = Server.accept();
                Runnable runa= new Serv(client,++count);
                Thread t= new Thread(runa);
                t.start();    
            }  
        }
        catch (Exception e){
            System.out.println(e);
        }
    } 

    @Override
    public void run() {
        String clientSentence;          
        String capitalizedSentence; 
	try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));             
            DataOutputStream outToClient = new DataOutputStream(client.getOutputStream());             
            clientSentence = inFromClient.readLine();             
            System.out.println("Received from client "+ID+": " + clientSentence);             
            capitalizedSentence = clientSentence.toUpperCase() + '\n';             
            outToClient.writeBytes(capitalizedSentence);      
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
