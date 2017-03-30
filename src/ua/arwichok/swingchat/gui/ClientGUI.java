package ua.arwichok.swingchat.gui;

import ua.arwichok.swingchat.client.Client;


import java.awt.FlowLayout;
//import java.awt.TextLayout;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.JFrame;



public class ClientGUI extends JFrame{
	
	TextArea textArea;
	TextField textField;
	Button send;
	Font defaultFont;
	
	Client client;
	String name;

	public ClientGUI(String caption){
		super(caption);



		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 360);
		setResizable(true);

		defaultFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);

		
		textArea = new TextArea("", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
		
		textField = new TextField(30);
		send = new Button("Send");


		textArea.setFont(defaultFont);
		textField.setFont(defaultFont);
		send.setFont(defaultFont);

		textArea.setEditable(false);
		textField.transferFocus();

		String ipPort = JOptionPane.showInputDialog(null, "ip:port");
		if(ipPort == null) System.exit(0);
		if(ipPort.equals("")) System.exit(0);


		String name = JOptionPane.showInputDialog(null, "Your Nickname:");
		if(name == null) System.exit(0);
		if(name.equals("")) System.exit(0);


		client = new Client(textArea, textField, name, ipPort);


		add(textArea);
		add(textField);
		add(send);


		textField.addActionListener(client);

		send.addActionListener(client);
		
		this.addWindowListener(client);// last method before closed




		setVisible(true);
	}

}



// class TextAreaTest extends JFrame{
// 	TextArea ta;
// 	JTextField jl;
// 	String print;
// 	JButton b;
	
// 	TextAreaTest(String s){
// 		super(s);
// 		//setUndecorated(true);
// 		setLayout(new FlowLayout());
// 		ta = new TextArea("Harcho",10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
// 		jl = new JTextField(20);
// 		b = new JButton("Send");
		
// 		b.addMouseListener(new MouseAdapter(){
// 			public void mouseReleased(MouseEvent me){
// 				if(jl.getText().equals("")) return;
// 				ta.append("\n" + jl.getText());
// 				jl.setText("");
// 			}
// 		});
		
// 		JScrollPane js = new JScrollPane(ta);
		
		
// 		ta.setEditable(true);
// 		setVisible(true);
// 		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 		setSize(330, 260);
// 		setResizable(true);
// 		setLocationRelativeTo(null);
// 		add(js);
// 		add(jl);
// 		add(b);
// 		ta.append("\n<Arwichok> Hello");
		
// 	}
// 	public static void main(String[] args) {
// 		new TextAreaTest("TextAreaTest");
		
// 	}
// }
