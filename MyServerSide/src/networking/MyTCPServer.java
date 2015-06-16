package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.SolutionsMap;

public class MyTCPServer {
	
	private ServerSocket server = null;
	private ExecutorService executor;
	
	private int port;
	private boolean isStopped;
	int idClient ;
	ClientHandler clientHandler;
	SolutionsMap mazeSolutionMap;
	
	public SolutionsMap getMazeSolutionMap() {
		return mazeSolutionMap;
	}
	public void setMazeSolutionMap(SolutionsMap mazeSolutionMap) {
		this.mazeSolutionMap = mazeSolutionMap;
	}

	HashMap<Integer, Socket> clientsMap = new HashMap<>();
	
	public MyTCPServer(int port) throws IOException 
	{
		
		server = new ServerSocket(port);
		server.setSoTimeout(10000);
		System.out.println("MyTCPServer.CTOR - START SERVER");
		
		idClient = 1;
		this.port = port;
		isStopped = false;
	}
	public void startServer(int numOfClients)
	{
		executor = Executors.newFixedThreadPool(numOfClients);	
		Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					System.out.println("\nwaiting for client");
					
						while(!isStopped)
						{
							try
							{
								final Socket someClient = server.accept();
								System.out.println("Client number : " + idClient + " connected! ");
								idClient ++;
								System.out.println("IP ADDRESS : " + someClient.getInetAddress());
								clientsMap.put(idClient, someClient);
							
								executor.execute(new Runnable() 
								{
									
									@Override
									public void run() 
									{
										// TODO Auto-generated method stub
										try 
										{
											MazeClientHandler clientHandler = new MazeClientHandler(someClient);
											clientHandler.setMazeSolutionMap(mazeSolutionMap);
											clientHandler.handleClient(someClient.getInputStream(),someClient.getOutputStream());			
											//someClient.close();
										} 
										catch (IOException e) 
										{
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
								});
								
							}
							catch(SocketTimeoutException e){} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
						executor.shutdown();
						try {
							server.close();
							
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				
			});
			
			thread.start();
			
	}
	
	public void stopServer(){
		isStopped = true;
	}

}
