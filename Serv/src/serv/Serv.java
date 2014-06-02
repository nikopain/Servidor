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
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class Serv implements Runnable {
    public static int port = 4000;
    Socket client;
    private static int count=0;
    private int ID;
    private PrintWriter os=null;
    public Serv(Socket socket){
        this.client = socket;
    }
    public Serv(Socket socket, int id) {
            this.client = socket;
            this.ID = id;
    }
 
    public static void main(String argv[]) throws Exception{ 
        InetAddress IP=InetAddress.getLocalHost();
        System.out.println(IP.getHostAddress());
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
        BufferedReader inFromClient;
        DataOutputStream outToClient;
        System.out.println("Se ha conectado el cliente "+ID);
	try {
            inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));             
            outToClient = new DataOutputStream(client.getOutputStream());             
            clientSentence = inFromClient.readLine();             
            System.out.println("Received from client "+ID+": " + clientSentence);             
            capitalizedSentence = clientSentence.toUpperCase() + '\n';             
            outToClient.writeBytes(capitalizedSentence);      
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void retornaFichero(String sfichero)
	{
            // comprobamos si tiene una barra al principio
            String opciones;
            if (sfichero.startsWith("/"))
            {
                    sfichero = sfichero.substring(1) ;
            }
            // si acaba en /, le retornamos el index.htm de ese directorio
            // si la cadena esta vacia, no retorna el index.htm principal
            if (sfichero.endsWith("/") || sfichero.equals(""))
            {
                    sfichero = sfichero + "menu.html" ;
            }
            try
            {
                // Ahora leemos el fichero y lo retornamos
                if(sfichero.equals("pag2.html")){
                    String contactos="";
                    File cont= new File("contactos.txt");
                    if(cont.exists()){
                        StringTokenizer s;
                        BufferedReader fLocal= new BufferedReader(new FileReader(cont));
                        String lin="";
                        String comienzo="<html>\n" +
                                        "    <body background=\"bg.jpg\">\n" +
                                        "        <div>\n"+
                                        "           <select name=\"contactos\" multiple=\"multiple\">";
                       while((lin=fLocal.readLine())!=null){
                            s= new StringTokenizer(lin);
                            contactos= contactos+"<option>"+ s.nextToken()+"</option>\n";
                            
                        }
                        String fin= "</select>\n" +
                                    "<form method=\"POST\" action=\"pag2.html\">\n" +
                                    "<textarea rows=\"5\" cols=\"50\"></textarea><br>    \n" +
                                    "<input type=\"text\" name=\"msje\" required>    \n" +
                                    "<input type=\"submit\" value=\"Enviar\">    \n" +
                                    "</div>\n" +
                                    "</form>\n" +
                                    "<form method=\"POST\" action=\"pag1.html\">\n" +
                                    "<input type=\"submit\" value=\"Agregar Contacto\">\n" +
                                    "</form>\n" +
                                    "</body>\n" +
                                    "</html>";
                        os.println(comienzo+contactos+fin);
                        fLocal.close();
                        os.close();
                    }
                    else{
                        os.println("HTTP/1.0 400 ok");
                        os.close();
                    }
                }
                else{
                File mifichero = new File(sfichero) ;
                
                if (mifichero.exists()) 
                {

                    BufferedReader ficheroLocal = new BufferedReader(new FileReader(mifichero));
                    String linea = "";
                    while (linea != null)			
                    {
                        linea = ficheroLocal.readLine();
                        os.println(linea);
                    }

                    ficheroLocal.close();
                    os.close();

                }  // fin de si el fiechero existe 
                else
                {	
                    os.println("HTTP/1.0 400 ok");
                    os.close();
                }
                }

            }
            catch(Exception e){
            }

        }

        public void imprimirFichero(String nextToken,String nombre1,String dirIp1,String puerto1) {
            Writer writer = null;

            try {
                
                writer = new BufferedWriter(new OutputStreamWriter(
                      new FileOutputStream("contactos.txt",true), "utf-8"));
                        writer.write("\r\n");
                        writer.write(nombre1+ " ");
                        writer.write(dirIp1+ " ");
                        writer.write(puerto1);
            } catch (IOException ex) {
              // report
            } finally {
               try {writer.close();} catch (Exception ex) {}
            }
            retornaFichero(nextToken);
            //lee archivo si no existe
            
        }
    
}
