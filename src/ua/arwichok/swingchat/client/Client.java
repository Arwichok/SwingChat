package ua.arwichok.swingchat.client;

import ua.arwichok.swingchat.gui.ClientGUI;

import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Client extends WindowAdapter 
		implements ActionListener
{
	TextArea textArea;
	TextField textField;
	BufferedReader in;
    PrintWriter out;
    String str = "";
    Socket socketClient;
    Resander resand;
    String ipPortArr[];


	public Client(TextArea textArea, TextField textField, String name, String ipPort){
		this.textArea = textArea;
		this.textField = textField;
		ipPortArr = ipPort.split(":");


		try{


            
            Socket socket = new Socket(ipPortArr[0], Integer.parseInt(ipPortArr[1]));
            
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            resand = new Resander();
            resand.start();

            out.println(name);
 
            
        }catch(IOException e){
            System.out.println(e);
            textArea.setForeground(Color.red);
            textArea.setText("Could not find server");
        }
        
    }
    
    private class Resander extends Thread{
        private boolean stoped;
        String str;
        
        public void setStop(){
            stoped = true;
        }
        
        @Override
        public void run(){
            try{
                
                while(!stoped){
	                str = in.readLine();
					textArea.append(str + "\n");
					System.out.println(str);
                }

            }catch(IOException e){
                System.out.print(e);
            }
        }
    }

	// String name = "<Arw> ";
	// TextArea textArea;
	// TextField textField;
	// Socket socketClient;
	// //InputStream receive;
	// OutputStream sendMessage;
	// BufferedReader receive;

	// public Client(TextArea textArea, TextField textField){
	// 	this.textArea = textArea;
	// 	this.textField = textField;


	// 	try{
	// 		socketClient = new Socket("localhost", 3000);
	// 		receive = new BufferedReader(new InputStreamReader(socketClient.getInputStream(), "UTF-8"));
	// 		sendMessage = socketClient.getOutputStream();

	// 	}catch(Exception e){
	// 		System.out.println(e);
	// 	}



	// 	class AcceptRequest implements Runnable{
	// 		public void run(){
	// 			try{
	// 				int g;
	// 				String buffer = "";
					
	// 				while(true){
	// 					while(true){
							
	// 						g = receive.read();
	// 						if(g == -1 || g == '\n') break;
	// 						System.out.print((char) g);
							

	// 						buffer+= (char) g;
	// 					}
						
	// 					System.out.println();

	// 					textArea.append(buffer + "\n");
	// 					buffer = "";
	// 				}

	// 			}catch(IOException ioe){
	// 				System.out.println(ioe);
	// 			}
	// 		}
	// 	}
	// 	Thread request = new Thread(new AcceptRequest());
	// 	request.start();

	// }

	

	public void actionPerformed(ActionEvent ae){//send message

		if(!textField.getText().equals("")){
			
			//textArea.append(name + textField.getText() + "\n");
			
			// try{
				out.println(textField.getText());
				//sendMessage.write((textField.getText() + "\n").getBytes());

			// }catch(IOException e){
			// 	System.out.println(e);
			// }
			
			textField.setText("");
		}
	}
	
	

	public void windowClosing(WindowEvent e){ // closed method
		
		try{
			out.println("/exit");
			resand.setStop();
			socketClient.close();
		}catch(Exception exc){System.out.println(exc);}

		System.out.println("End Application");
	}


}