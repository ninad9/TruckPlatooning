package com.server;

import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.utility.AppendableObjectInputStream;
import com.utility.AppendableObjectOutputStream;
import com.utility.DataInputDto; 

class ServerRun{ 

	
	public static List<Socket> clientList = new ArrayList<Socket>();
	private static int clientNo = 1;
	
	public static void main(String args[]) 
			throws Exception 
	{
		//DataInputDto dt = new DataInputDto();
		// Create server Socket 
		ServerSocket ss = new ServerSocket(888);
		
		System.out.println("Welcome to Truck Paltooning System");
		Scanner sc= new Scanner(System.in);
		System.out.println("Set Speed of the lead vehicle");
		int speed = sc.nextInt();
		System.out.println("Lead Vehicle speed is set to "+speed+" mph");
		//Accept New Client and add in Client List for sending messages
		//Create new Thread for each client to show received messages
		Thread thread = new Thread(()-> {
			while(true)
			{
				Socket s;
				try {
					s = ss.accept();
					clientList.add(s);
					Thread t = new Thread(new ClientMessageReceiverThread(s, "CLIENT "+clientNo));
					t.start(); 
					clientNo++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		
		// to read data from the keyboard 
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in)); 
		
		//Code to send messages to Client
		
		while(true){
			
			if(clientList.size()==0) {
				Thread.sleep(1000);
				continue;
			}
			
			
			
			String str = kb.readLine();		
			DataInputDto data = sendDataToClient(str, speed);
			AppendableObjectOutputStream os = null;
			//ObjectOutputStream os = null;
			/*
			for(Socket s :clientList) {
				//new PrintStream(s.getOutputStream()).println(str);
				
				 os = new AppendableObjectOutputStream(s.getOutputStream());
				 os.writeObject(data);
				 os.flush();
				
			}
			*/
			int clientCount= clientList.size();
			
			for(int i=0; i<clientCount; i++){
				Socket s = clientList.get(clientCount-1);
				//new PrintStream(s.getOutputStream()).println(str);
				os = new AppendableObjectOutputStream(s.getOutputStream());
				 os.writeObject(data);
				 os.flush();
				 sendInitialInputsToClient(speed);
				break;
				}
			
			if(str.equalsIgnoreCase("exit")) {
				os.close();
				break;}
		}
		
		System.out.println("Server Exited !!!");	

		System.exit(0);
	}

	private static DataInputDto sendDataToClient(String str, int speed) throws IOException {
		DataInputDto result=null;
		switch(str) {
		case "initiate" : 
			result = sendInitialInputsToClient(speed);
			break;
		case "brake" : 
			result = brakeClient();
			break;
		default:
			break;	
			
		}
		return result;
	}

	private static DataInputDto brakeClient() throws IOException {
		
		AppendableObjectOutputStream os = null;
		DataInputDto data = new DataInputDto();
		
		for(Socket s :clientList) {
			//new PrintStream(s.getOutputStream()).println(str);
			
			 os = new AppendableObjectOutputStream(s.getOutputStream());
			 data.setSpeed(20);
				data.setvGap(4);
				data.setOperation("brake");
			 os.writeObject(data);
			 os.flush();
			
		}
		
		
		return data;
	}

	private static DataInputDto sendInitialInputsToClient(int speed) {
		DataInputDto data = new DataInputDto();
		data.setSpeed(speed);
		data.setvGap(2);
		data.setOperation("initiate");
		//System.out.println(data.toString());
		return data;
	} 
} 
