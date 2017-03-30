package ua.arwichok.swingchat;

import ua.arwichok.swingchat.client.Client;
import ua.arwichok.swingchat.server.Server;
import ua.arwichok.swingchat.gui.ClientGUI;

import javax.swing.SwingUtilities;

public class Main{
	public static void main(String args[]){

		final String CAPTION = "SwingChat";

		if(args.length == 2){
			if(args[0].equals("-server"))
				new Server(Integer.parseInt(args[1]));
		}
		else if(args.length == 1){
			if(args[0].equals("-server"))
				new Server(3000);
		}
		else{
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){

					new ClientGUI(CAPTION);
				}
			});
		}

	}
}