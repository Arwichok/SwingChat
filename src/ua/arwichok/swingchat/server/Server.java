/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.arwichok.swingchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Den
 */
public class Server {
    
    Connection connections[];
    Socket sock;
    

    
    public Server(int port){
        
        int maxcon = 100;
        connections = new Connection[maxcon];
        
        
        int i = 0;
        
        try{
            
            ServerSocket socketServer = new ServerSocket(port, maxcon);
            
            while(true){
                sock = socketServer.accept();
                connections[i] = new Connection(sock);
                connections[i].start();
                i++;
                
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    private class Connection extends Thread{
        BufferedReader in;
        PrintWriter out;
        Socket socket;
        
        private String name = "";
        
        private Connection(Socket socket){
            
            this.socket = socket;
            
            try{
                
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            
            }catch(IOException e){
                System.out.println(e);
            }
        }
            
            
            @Override
            public void run(){
                try{
                    name = in.readLine();
                    
                    for(Connection c : connections){
                        if(c == null) continue;
                        c.out.println(name + " comes now");
                    }
                    System.out.println(name + " comes now");
                    
                    String str;
                    
                    
                    
                    while(true){
                        str = in.readLine();
                        
                        if(str.equals("/ip")) str = socket.getInetAddress().getHostAddress();

                        if(str.equals("/mem")){
                            Runtime r = Runtime.getRuntime();
                            long f, t;
                            f = r.freeMemory();
                            t = r.totalMemory();
                            

                            System.out.println("Free memory: " + f);
                            System.out.println("Total memory: " + t);
                            System.out.println("Busy: " + (t - f));
                        }

                        if(str.equals("/help")){
                            str = "Help page \n" +
                            "/ip -\tyour ip";
                        }

                        if(str.equals("/exit")) break;
                        
                        for(Connection c : connections){
                            if(c == null) continue;
                            c.out.println("<" + name + "> " + str);
                        }
                        System.out.println(name + ": " + str);
                    }
                    
                    for(Connection c : connections){
                        if(c == null) break;
                        c.out.println(name + " has left");
                    }
                    System.out.println(name + " has left");
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        
        }
}
