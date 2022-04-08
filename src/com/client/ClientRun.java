package com.client;

// Client2 class that 
// sends data and receives also 
  
import java.io.*; 
import java.net.*;

import com.utility.AppendableObjectOutputStream;
import com.utility.DataInputDto; 
  
class ClientRun { 
  
    public static void main(String args[]) 
        throws Exception 
    {
    	try {
			System.out.println("Starting Client...");
			// Create client socket 
	        Socket s = new Socket("localhost", 888);
	        
	        new Thread(new ServerMessageReceiver(s)).start();
	  
	        // to send data to the server 
	        DataOutputStream dos 
	            = new DataOutputStream( 
	                s.getOutputStream()); 
	  
	        // to read data from the keyboard 
	        BufferedReader kb 
	            = new BufferedReader( 
	                new InputStreamReader(System.in)); 
	        String str = kb.readLine();
	        int speed = 0;
			DataInputDto data = sendDataToServer(str, speed);
			AppendableObjectOutputStream os = null;
			
			os = new AppendableObjectOutputStream(s.getOutputStream());
			 os.writeObject(data);
			 os.flush();
			
			
	        // repeat as long as exit 
	        // is not typed at client 
	        while (!(str = kb.readLine()).equals("exit")) { 
	        	
	        	//sendInitialInputsToServer();
	            // send to the server 
	            dos.writeBytes(str + "\n");  
	        } 
	  
	        // close connection. 
	        dos.close();
	        kb.close(); 
	        s.close(); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    

	private static DataInputDto sendDataToServer(String str, int speed) {
		DataInputDto result=null;
		switch(str) {
		case "ack" : 
			result = sendInitialInputsToServer(speed);
			break;
			
		case "brake" : 
			result = brakeServer();
			break;
		default:
			break;	
			
		}
		return result;
	}

	private static DataInputDto brakeServer() {
		DataInputDto data = new DataInputDto();
		data.setSpeed(20);
		data.setvGap(4);
		data.setOperation("brake");
		return data;
	}

	private static DataInputDto sendInitialInputsToServer(int speed) {
		DataInputDto data = new DataInputDto();
		
		//System.out.println("Speed set at: " + data.getSpeed() + " mph");
		data.setSpeed(speed);
		data.setvGap(2);
		data.setOperation("ack");
		//System.out.println(data.toString());
		return data;
	} 
} 