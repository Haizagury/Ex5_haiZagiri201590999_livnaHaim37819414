
package model;

import java.util.concurrent.Callable;
import algorithms.mazeGenerator.Maze;
import algorithms.search.Solution;

/** 
11 * The MazeSolveCallable class implements Callable. 
12 * Generates a solution for a maze with threads. 
13 * @author  hai zagury and livna haim 
14 * @version 1.0 
15 * @since 17.5.2015 
16 */ 

public class CallableSolution implements Callable<Solution> {

	/** The maze. */
	private Maze maze;
	
	/** The searcht type. */
	private String searchtType;
	
	/** The heuristic. */
	private String heuristic;
	
	/**
	 * Instantiates a new callable solution.
	 *
	 * @param maze the maze
	 * @param searchtType the searcht type
	 * @param heuristic the heuristic
	 */
	public CallableSolution(Maze maze, String searchtType, String heuristic) {
		this.maze = maze;
		this.searchtType = searchtType;
		this.heuristic = heuristic;
		}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Solution call() throws Exception {
		
		Client client = new Client();
		ClientRequest request = new ClientRequest();
		request.setRequestName("solveMaze");
		StringBuffer requestArgs = new StringBuffer();
		requestArgs.append(this.searchtType);
		requestArgs.append(",");
		requestArgs.append(this.heuristic);
		
		request.setRequestArgs(requestArgs.toString());
		request.setMaze(this.maze);
		
		ServerReply reply = client.getReply(request);
		Solution solution = new Solution();
		if (reply.getReplyName().equals("Solution")){
			solution = reply.getSolution();
		}
		else
			solution = null;
		
		return solution;
	}

}
