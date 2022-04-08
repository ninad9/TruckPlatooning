package com.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.utility.AppendableObjectInputStream;
import com.utility.DataInputDto;

public class ClientMessageReceiverThread implements Runnable {

	Socket s;
	String ClientName;

	public ClientMessageReceiverThread(Socket s, String ClientName) {
		this.s = s;
		this.ClientName = ClientName;
	}

	@Override
	public void run() {
		try {
			System.out.println(ClientName+" Connected :)");

			// to read data coming from the client 
			//BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream())); 
			
			AppendableObjectInputStream is = new AppendableObjectInputStream(s.getInputStream());		
			DataInputDto data = null;
			
			// server executes continuously
			// repeat as long as the client 
			// does not send a null string or exit
			// read from client
			String str;			
			//while ((str = br.readLine()) != null) {
			while ((data =(DataInputDto) is.readObject() ) != null) {
				String str1 = data.getOperation();
				System.out.println("From "+ClientName+": "+str1);

				if(str1.equalsIgnoreCase("exit"))	//exit loop
				{
					System.out.println("Client Exited!!!");
					System.exit(0);
				}
				else
				{
					performActivityClient(data);
				}
				
				
			}
			// close connection 
			//br.close(); 
			s.close();
			ServerRun.clientList.remove(s);
			System.out.println(ClientName+" Disconnected :(");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void performActivityClient(DataInputDto data) {
		// TODO Auto-generated method stub
		System.out.println("str-" + data);
		/*String[] stAr = str.split("\\^");
		str = stAr[1];
		System.out.println("str-" + str);*/
		switch(data.getOperation()) {
		case "ack" : 
			System.out.println("init client :");
			System.out.println("Speed set at: " + data.getSpeed() + " mph");
			break;
		case "brake" : 
			System.out.println("Former Vehicle braking as Obstacle is detected");
			break;
		default:
			break;
		}
		
	}

}
