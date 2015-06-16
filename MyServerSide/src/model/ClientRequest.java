package model;

import java.io.Serializable;

import algorithms.mazeGenerator.Maze;

public class ClientRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String requestName = null;
	private String requestArgs = null;
	private Maze maze = null;
	
	public ClientRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getRequestArgs() {
		return requestArgs;
	}

	public void setRequestArgs(String requestArgs) {
		this.requestArgs = requestArgs;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	
	
	
	
}
