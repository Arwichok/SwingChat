package ua.arwichok.swingchat.server;
	
import java.net.*;
import java.io.*;

public class Server{

	ServerSocket serverSocket;
	Socket acceptClient;
	Socket arrayClient[] = new Socket[5];
	String buffer = "";


	InputStream arrayMessage[] = new InputStream[5];
	OutputStream arraySend[] = new OutputStream[5];

	Thread arrayThreadMessage[] = new Thread[5];

	public Server(int port){
		System.out.println("Start server with port " + port);

		try{
			
			serverSocket = new ServerSocket(3000);
			//acceptClient = serverSocket.accept();
			
			Thread threadClient = new Thread(new Runnable(){
				public void run(){
					
					try{

						for(int i = 0; i < arrayClient.length; i++){
							if(arrayClient[i] != null) continue;

							arrayClient[i] = serverSocket.accept();
							arrayMessage[i] = arrayClient[i].getInputStream();
							arraySend[i] = arrayClient[i].getOutputStream();

							arrayThreadMessage[i] = new Thread(new Runnable(){
								
								public void run(){
									int b;
									
									
									try{
										do{
											while(true){
												
												b = arrayMessage[0].read();
												if(b == -1) break;
	
												buffer += (char) b;
											}
	
											sendAllMessage(buffer);
											
											if(buffer.equals("stop")) break;
											
											buffer = "";
	
										}while(true);

									}catch(Exception e){
										System.out.println(e);
									}
								}	
							});
						}

					}catch(Exception e){
						System.out.println(e);
					}
				}
			});
			threadClient.start();


			// Thread threadSend = new Thread(new Runnable(){
			// 	public void run(){
			// 		try{

			// 			for(InputStream n : arrayMessage){
			// 				if(n == null) continue;
			// 				int p;
			// 				while(true){
			// 					p = n.read();
			// 					if(p == -1) break;
			// 					System.out.print((char) p);
			// 				}
			// 			}

			// 		}catch(Exception e){
			// 			System.out.println(e);
			// 		}
			// 	}
			// });
			// threadSend.start();

		}catch(Exception e){
			System.out.println(e);
		}

		


		// try(BufferedReader acceptMessage = 
		// 			new BufferedReader(
		// 				new InputStreamReader(acceptClient.getInputStream(), "UTF-8"));
		// 	OutputStream sendMessage = acceptClient.getOutputStream()){

		// 	int g;
		// 	String buffer = "";

		// 	while(true){
				
		// 		do{
		// 			g = acceptMessage.read();
					
		// 			if(g == -1) break;

		// 			System.out.print((char) g);
		// 			buffer += (char) g;

		// 		}while(g != '\n');
				
		// 		if(g == -1) break;
		// 		if(buffer.equals("/stop")) break;

				
		// 		//sendMessage.write(buffer.getBytes());
				

		// 		buffer = "";

		// 	}


		// 	serverSocket.close();
		// }catch(Exception e){
		// 	System.out.println(e);
		// }

		

	}

	synchronized public void sendAllMessage(String message) throws Exception{
		for(int g = 0; g < arraySend.length; g++){
			if(arraySend[g] == null) continue;

			arraySend[g].write(message.getBytes());
		}
	}
}