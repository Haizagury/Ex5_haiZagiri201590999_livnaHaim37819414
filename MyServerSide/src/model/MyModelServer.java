package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;

import networking.MyTCPServer;

public class MyModelServer extends Observable implements ModelServer {

	MyTCPServer server = null;
	int port = 5040;
	int numOfClients = 10;
	SolutionsMap mazeSolutionMap;
	
	public MyModelServer() throws IOException {
		server = new MyTCPServer(port);
	}
	
	@Override
	public void stopServer() {
		saveSolutions();
		server.stopServer();

	}

	@Override
	public void startServer() {
		upaloadSolutions();
		server.setMazeSolutionMap(mazeSolutionMap);
		server.startServer(numOfClients);
		
	}
	
	public void upaloadSolutions() {
		
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("resources\\Solutions.txt"));
			SolutionsMap solutions = (SolutionsMap) in.readObject();
			mazeSolutionMap = solutions;
			in.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void saveSolutions(){
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream("resources\\Solutions.txt"));
			out.writeObject(mazeSolutionMap);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
