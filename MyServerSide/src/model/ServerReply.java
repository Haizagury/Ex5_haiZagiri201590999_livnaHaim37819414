package model;

import java.io.Serializable;

import algorithms.mazeGenerator.Maze;
import algorithms.search.Solution;

public class ServerReply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String replyName = null;
	private Maze maze = null;
	private Solution solution = null;
	
	public ServerReply() {
		// TODO Auto-generated constructor stub
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	
}
