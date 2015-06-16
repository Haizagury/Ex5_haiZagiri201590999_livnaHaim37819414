package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import algorithms.mazeGenerator.Cell;
import algorithms.mazeGenerator.DFSMazeGenerator;
import algorithms.mazeGenerator.Maze;
import algorithms.mazeGenerator.MazeGenerator;
import algorithms.mazeGenerator.RandomMazeGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.Heuristic;
import algorithms.search.MazaManhattanHeuristic;
import algorithms.search.MazeAirHeuristic;
import algorithms.search.Solution;

import model.ClientRequest;
import model.ServerReply;
import model.SolutionsMap;

public class MazeClientHandler implements ClientHandler {

	private Socket socket;
	SolutionsMap mazeSolutionMap;
	
	public SolutionsMap getMazeSolutionMap() {
		return mazeSolutionMap;
	}

	public void setMazeSolutionMap(SolutionsMap mazeSolutionMap) {
		this.mazeSolutionMap = mazeSolutionMap;
	}

	public MazeClientHandler(Socket socket)
	{
		this.socket = socket;
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			ClientRequest request;
			ServerReply reply = new ServerReply();
			try {
				request = (ClientRequest) in.readObject();
				String[] args = request.getRequestArgs().split(",");
				if (request.getRequestName().equals("generateMaze")){
					MazeGenerator mg = null;
					if (args[0].equals("DFS")){
						mg = new DFSMazeGenerator();
					}
					else if (args[0].equals("RANDOM")){
						mg = new RandomMazeGenerator();
					}
					
					int rows = Integer.valueOf(args[1]);
					int cols = Integer.valueOf(args[2]);
					int xStartPoint = Integer.valueOf(args[3]);
					int yStartPoint = Integer.valueOf(args[4]);
					Boolean allowDiagonals = Boolean.valueOf(args[5]);
					Maze maze = mg.generateMaze(rows,cols,new Cell(xStartPoint,yStartPoint),allowDiagonals);
					
					reply.setReplyName("Maze");
					reply.setMaze(maze);
					reply.setSolution(null);
				}
				else if (request.getRequestName().equals("solveMaze")){
					Solution solution = new Solution();
					
					
					if (mazeSolutionMap.containsKey(request.getMaze())){
						solution = mazeSolutionMap.get(request.getMaze());
					}
					else{
							if (args[0].equals("BFS")){
								BFS bfsSearch = new BFS();
								solution = bfsSearch.search(request.getMaze());
							}
							else if (args[0].equals("ASTAR")){
								Heuristic heur = null;
								if (args[1].equals("AIR")){
									heur = new MazeAirHeuristic();
									
								}
								else if (args[1].equals("MANHATTAN")){
									heur = new MazaManhattanHeuristic();
								}
								AStar aStarSearch = new AStar(heur);
								solution = aStarSearch.search(request.getMaze());
							}
							
							mazeSolutionMap.put(request.getMaze(), solution);
					}
							
					reply.setReplyName("Solution");
					reply.setSolution(solution);
					reply.setMaze(null);
				}
				else{
					reply.setReplyName("UnKnown Request");
					reply.setMaze(null);
					reply.setSolution(null);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			out.writeObject(reply);
			
		} catch (IOException e) {			
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
	}

}
