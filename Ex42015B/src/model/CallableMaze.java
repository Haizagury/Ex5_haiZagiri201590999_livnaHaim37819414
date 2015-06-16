/**
 * 
 */
package model;


import java.util.concurrent.Callable;
import algorithms.mazeGenerator.Maze;

/**
 * The Class CallableMaze implements Callable<>.
 *Generate a Maze with thread.
 * @author hai zagury and livna haim
 * @version 1.0
 * @since 17.05.2015
 */
public class CallableMaze implements Callable<Maze> {
	
	/** The rows. */
	private int rows;
	
	/** The cols. */
	private int cols;
	
	/** The x start point. */
	private int xStartPoint;
	
	/** The y start point. */
	private int yStartPoint;
	
	/** The maze generate type. */
	private String mazeGenerateType;
	
	/** The Allow diagonals option */
	private Boolean allowDiagonals;

	/**
	 * Instantiates a new callable maze.
	 *
	 * @param mazeGenerateType the maze generate type
	 * @param rows the rows
	 * @param cols the cols
	 * @param xStartPoint the x start point
	 * @param yStartPoint the y start point
	 */
	public CallableMaze(String mazeGenerateType, int rows, int cols, int xStartPoint, int yStartPoint, Boolean allowDiagonals) {

		this.mazeGenerateType = mazeGenerateType;
		this.rows = rows;
		this.cols = cols;
		this.xStartPoint = xStartPoint;
		this.yStartPoint = yStartPoint;
		this.allowDiagonals = allowDiagonals;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Maze call() throws Exception {
		Client client = new Client();
		ClientRequest request = new ClientRequest();
		request.setRequestName("generateMaze");
		StringBuffer requestArgs = new StringBuffer();
		requestArgs.append(this.mazeGenerateType);
		requestArgs.append(",");
		requestArgs.append(this.rows);
		requestArgs.append(",");
		requestArgs.append(this.cols);
		requestArgs.append(",");
		requestArgs.append(this.xStartPoint);
		requestArgs.append(",");
		requestArgs.append(this.yStartPoint);
		requestArgs.append(",");
		requestArgs.append(this.allowDiagonals);
		
		
		request.setRequestArgs(requestArgs.toString());
		request.setMaze(null);
		ServerReply reply = client.getReply(request);
		Maze maze = new Maze();
		if (reply.getReplyName().equals("Maze")){
			maze = reply.getMaze();
		}
		else
			maze = null;
		
		return maze;
	}

}
