package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;




public class Client {

	private String serverAddress;
	private int port;
	
	public Client() {
		this.serverAddress = "127.0.0.1";
		this.port = 5040;
	}
	
	public ServerReply getReply(ClientRequest request) {		
		
		Socket socket = null;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		
		ServerReply reply = new ServerReply();
		try {
			socket = new Socket(serverAddress, port);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
						
			out.writeObject(request);			
			reply = (ServerReply)in.readObject();
			
			return reply;	
								
		}
		catch (ConnectException e) {
			reply.setReplyName("Server Not Connected");
		}
		catch (IOException e) {			
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}	
		return reply;
	}
}
