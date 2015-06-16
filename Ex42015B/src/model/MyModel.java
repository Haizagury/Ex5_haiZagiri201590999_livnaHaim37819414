package model;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import algorithms.mazeGenerator.Cell;
import algorithms.mazeGenerator.Maze;
import algorithms.search.Solution;


/** 
 * The MyModel class extends Observable and implements Model. 

* @author  hai zagury and livna haim 
* @version 1.0 
* @since 17.5.2015 
**/
public class MyModel extends Observable implements Model,Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The executor. */
	ExecutorService executor;
	
	/** The maze map. */
	HashMap<String, Maze> mazeMap;
	
	HashMap<String, Maze> mazeName;
	
	/** The solution map. */
	HashMap<String, Solution> solutionMap;
	
	/** The run properties. */
	Properties runProperties;
	
	/**
	 * Instantiates a new my model.
	 */
	public MyModel() {
		executor = Executors.newCachedThreadPool();
		mazeMap = new HashMap<>();
		solutionMap = new HashMap<>();
		runProperties = new Properties();
	}
	
	/* (non-Javadoc)
	 * @see model.Model#generateMaze(java.lang.String)
	 */
	@Override
	public void generateMaze(String mazeName) {
		Future<Maze> f = executor.submit (new CallableMaze(runProperties.getMazeGenerateType(), runProperties.getRows(),runProperties.getCols(),runProperties.getXStartPoint(),runProperties.getYStartPoint(),runProperties.getAllowDiagonals()));
		try {
			Maze m = f.get();
			m.setGoalState(new Cell(m.getRows() - 1, m.getCols() - 1));
			mazeMap.put(mazeName, m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ExecutionException e) {
			setChanged();
			notifyObservers("serverNotConnected ");
		}
		setChanged();
		notifyObservers("generateMazeComplete " + mazeName);
	}

	/* (non-Javadoc)
	 * @see model.Model#getMaze(java.lang.String)
	 */
	@Override
	public Maze getMaze(String mazeName) {
		if (mazeMap.containsKey(mazeName))
			return mazeMap.get(mazeName);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see model.Model#solveMaze(java.lang.String)
	 */
	@Override
	public void solveMaze(String mazeName) {
		String[] args = mazeName.split(",");
		String myMazeName = args[0];
		Maze maze = mazeMap.get(args[0]);
		if (args.length > 1)
			maze.setStartState(new Cell(Integer.valueOf(args[1]),Integer.valueOf(args[2])));
					
		Solution solution;
		if (mazeMap.containsKey(myMazeName)){
				Future<Solution> f = executor.submit (new CallableSolution(maze,runProperties.getSearchType(), runProperties.getHeuristic()));
				try {				
					solution = f.get();
					solutionMap.put(myMazeName, solution);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					setChanged();
					notifyObservers("serverNotConnected " + myMazeName);
				}
			//}
			setChanged();
			notifyObservers("solveMazeCompleted " + myMazeName);
		}
		else{
		
			setChanged();
			notifyObservers("solveMazeCompletedErorr " + myMazeName);
		}
		
	}

	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@Override
	public Solution getSolution(String mazeName) {
		if (solutionMap.containsKey(mazeName))
			return solutionMap.get(mazeName);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see model.Model#stop()
	 */
	@Override
	public void stop() {
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new FileOutputStream("lib\\Properties.xml"));
			encoder.writeObject(runProperties);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			encoder.close();
		}
		executor.shutdown();
	}

	/* (non-Javadoc)
	 * @see model.Model#upaloadSolutions()
	 */
	/*
	@Override
	public void upaloadSolutions() {
		
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("lib\\Solutions.txt"));
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
	*/

	/* (non-Javadoc)
	 * @see model.Model#start()
	 */
	@Override
	public void start() {
		XMLDecoder decoder = null;
	
		try {
			
			decoder = new XMLDecoder(new FileInputStream("lib\\Properties.xml"));
			runProperties = (Properties)decoder.readObject();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//upaloadSolutions();
	}

	@Override
	public String getRunProperties() {
		StringBuilder sb = new StringBuilder();
		sb.append(runProperties.getMazeGenerateType());
		sb.append(",");
		sb.append(runProperties.getSearchType());
		sb.append(",");
		sb.append(runProperties.getHeuristic());
		sb.append(",");
		sb.append(runProperties.getRows().toString());
		sb.append(",");
		sb.append(runProperties.getCols().toString());
		sb.append(",");
		sb.append(runProperties.getXStartPoint().toString());
		sb.append(",");
		sb.append(runProperties.getYStartPoint().toString());
		sb.append(",");
		sb.append(runProperties.getAllowDiagonals().toString());
		sb.append(",");
		sb.append(runProperties.getGameCharecter());
		sb.append(",");
		sb.append(runProperties.getUserInterfaceType());
		return sb.toString();
	}

	@Override
	public void setRunProperties(String runProperties) {
		String[] prop = runProperties.split(",");
		this.runProperties.setMazeGenerateType(prop[0]);
		this.runProperties.setSearchType(prop[1]);
		this.runProperties.setHeuristic(prop[2]);
		this.runProperties.setRows(Integer.valueOf(prop[3]));
		this.runProperties.setCols(Integer.valueOf(prop[4]));
		this.runProperties.setXStartPoint(Integer.valueOf(prop[5]));
		this.runProperties.setYStartPoint(Integer.valueOf(prop[6]));
		this.runProperties.setAllowDiagonals(Boolean.valueOf(prop[7]));
		this.runProperties.setGameCharecter(prop[8]);
		this.runProperties.setUserInterfaceType(prop[9]);
		
	}

	
	


}
