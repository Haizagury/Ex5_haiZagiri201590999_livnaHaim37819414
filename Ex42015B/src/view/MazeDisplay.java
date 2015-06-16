/**
 * 
 */
package view;



import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerator.Cell;
import algorithms.mazeGenerator.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * @author HFL
 *
 */
public class MazeDisplay extends Canvas {

	Maze mazeData = null;
	Boolean printHint = false;
	Boolean printSolution = false;
	Solution mazeSolution;
	boolean Moved = false;
	MazeDisplay maze;
	Boolean allowDiagnoal = false;
	String gameCharecterPicture;
	 /*Timer timer;
	 TimerTask myTask;*/
	GameCharecter gameCharecter;

	 

	public Maze getMazeData() {
		return mazeData;
	}

	public void setMazeData(Maze mazeData, Boolean allowDiagnoal, String gameCharecterPicture) {
		this.mazeData = mazeData;
		this.allowDiagnoal = allowDiagnoal;
		this.gameCharecterPicture = gameCharecterPicture;
	}
	
	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
	}

	public void startMazeDisplay() {
		setBackground(new Color(null, 255, 255, 255));

		Cell c = new Cell(mazeData.getStartState());
		
		gameCharecter = new GameCharecter(c.getCol(), c.getRow(),gameCharecterPicture);
		
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				
				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));

				int width = getSize().x;
				int height = getSize().y;

				int w = width / mazeData.getCols();
				int h = height / mazeData.getRows();
				
				Cell currGameCharecter = new Cell(gameCharecter.getY(),gameCharecter.getX() );
				
				if (printSolution){
					
					Boolean stopPrint = false;
					for (State s : mazeSolution){
						Cell currSolCell = new Cell(s);
						
						if (!stopPrint){
							new GameCharecter(currSolCell.getCol(), currSolCell.getRow(),gameCharecterPicture).paint(e, w, h, currSolCell.getCol()*w, currSolCell.getRow()*h);
							if ((currSolCell.getRow() == currGameCharecter.getRow()) && (currSolCell.getCol() == currGameCharecter.getCol())){
								stopPrint = true;
								printSolution = false;
							}
						}
						
					}
					
				}
				else if (printHint){
					
					Boolean hintPrinted = false;
					Cell prevSolCell = new Cell(mazeData.getGoalState());
					
					for (State s : mazeSolution){
						if (!hintPrinted){
							Cell currSolCell = new Cell(s);
							
							if ((currSolCell.getRow() == currGameCharecter.getRow()) && (currSolCell.getCol() == currGameCharecter.getCol())){
								new GameCharecter(prevSolCell.getCol(), prevSolCell.getRow(),gameCharecterPicture).paint(e, w, h, prevSolCell.getCol()*w, prevSolCell.getRow()*h);
								hintPrinted = true;
								printHint = false;
								gameCharecter.setX(prevSolCell.getCol());
								gameCharecter.setY(prevSolCell.getRow());
							}
							else{
								prevSolCell = new Cell(s);
							}
						}
					}
				}
				
				printGoalCellImage(e, w, h);				
						
				gameCharecter.x = gameCharecter.x * w;
				gameCharecter.y = gameCharecter.y * h;
			
				gameCharecter.paint(e, w, h);
				
				gameCharecter.x = gameCharecter.x / w;
				gameCharecter.y = gameCharecter.y / h;
				
				for (int i = 0; i < mazeData.getRows(); i++)
					for (int j = 0; j < mazeData.getCols(); j++) {

						int x = j * w;
						int y = i * h;
						Cell c = mazeData.getCell(i, j);
						
						if (c.getHasTopWall()) {
							e.gc.drawLine(x, y, x + w, y);
						}
						if (c.getHasBottomWall()) {
							e.gc.drawLine(x, y + h, x + w, y + h);
						}
						if (c.getHasLeftWall()) {
							e.gc.drawLine(x, y, x, y + h);
						}
						if (c.getHasRightWall()) {
							e.gc.drawLine(x + w, y, x + w, y + h);
						}

					}
				
			}
		});

	}

	/*
	public void start() {
		myTask = new TimerTask() {

			@Override
			public void run() {

				getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						Random r = new Random();
						gameCharecter.x = r.nextInt(500);
						gameCharecter.y = r.nextInt(500);
						redraw();
					}
				});
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(myTask, 0, 500);
	}
	*/

	public void move(int x, int y) {
		
		gameCharecter.x = y;
		gameCharecter.y = x;
		redraw();

	}
	
	public Boolean mouseMove(int x, int y) {
		
		int currX = gameCharecter.x;
		int currY = gameCharecter.y;
		
		gameCharecter.x = x;
		gameCharecter.y = y;
		
		int width = getSize().x;
		int height = getSize().y;

		int w = width / mazeData.getCols();
		int h = height / mazeData.getRows();
		
		gameCharecter.x = gameCharecter.x / w;
		gameCharecter.y = gameCharecter.y / h;
		
		if (CheckMotion(currX, currY, gameCharecter.x, gameCharecter.y)){
			redraw();
			return checkWin(gameCharecter.x, gameCharecter.y);
		}
		else{
			gameCharecter.x =currX;
			gameCharecter.y = currY;
		}
		return false;
		

	}
	
	public Boolean keyMove(int x, int y) {
		int currX = gameCharecter.x;
		int currY = gameCharecter.y;
		
		gameCharecter.x = gameCharecter.x + x;
		gameCharecter.y = gameCharecter.y + y;
		
		if (CheckMotion(currX, currY, gameCharecter.x, gameCharecter.y)){
			redraw();
			//Check if user finish to solve maze
			return checkWin(gameCharecter.x, gameCharecter.y);
		}
		else{
			gameCharecter.x =currX;
			gameCharecter.y = currY;
		}
		return false;
	}

	public void draw(Solution s, boolean onlyHint) {
		mazeSolution = s;
		printHint = onlyHint;
		
		if (!onlyHint){
			printSolution = true;
		}
		redraw();
	}
	
	public void printGoalCellImage(PaintEvent e,int width ,int height){
		
		String filename = null;
		if (gameCharecterPicture.equalsIgnoreCase("Bamba"))
			filename = new String("lib\\bambaGoal.jpg");
		else
			filename = new String("lib\\bissliGoal.jpg");
		//paint the goal state
		Image image = new Image(new Device() {
			
			@Override

			public int internal_new_GC(GCData arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void internal_dispose_GC(int arg0, GCData arg1) {
				// TODO Auto-generated method stub
				
			}
			
//			@Override
//			public void internal_dispose_GC(long arg0, GCData arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public long internal_new_GC(GCData arg0) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
		}, filename);  
		
		Cell goalCell =  new Cell(mazeData.getGoalState());
		
		int goalX = goalCell.getCol() * width;
		int goalY = goalCell.getRow() * height;
		
		ImageData imageData =  image.getImageData();
		e.gc.drawImage(image, 0, 0, imageData.width , imageData.height, goalX, goalY, width, height);
		

	}


	public Boolean checkWin(int x , int y){
		Cell goal = new Cell(mazeData.getGoalState());
		if ((x == goal.getCol()) && (y == goal.getRow()))
			return true;
		else
			return false;
	}
	
	public boolean CheckMotion(int CurrentX,int CurrentY,int nextX,int nextY){ 
	 		 
	 		if((nextX<0) || (nextY<0) || (nextX >= mazeData.getCols()) || (nextY >= mazeData.getRows())
	 				|| (Math.abs(CurrentX - nextX) > 1) || (Math.abs(CurrentY-nextY) > 1) ){
	 			return false; 
	 		}
	 		else{
	 			
	 			if (mazeData.getAllowsDiagonal()){
	 				/* טיפול באלכסונים*/
	 				//top left
	 				if (((CurrentX < nextX) && (CurrentY < nextY) &&
	 					(((!mazeData.getCell(nextY, nextX).getHasTopWall()) && (!mazeData.getCell(CurrentY, CurrentX).getHasRightWall()) )||
	 					 ((!mazeData.getCell(nextY, nextX).getHasLeftWall())  && (!mazeData.getCell(CurrentY, CurrentX).getHasBottomWall())))) ||
	 					//top right
	 					 ((CurrentX < nextX) && (CurrentY > nextY) &&
	 		 			 (((!mazeData.getCell(CurrentY, CurrentX).getHasTopWall()) && (!mazeData.getCell(nextY, nextX).getHasLeftWall()) )||
	 		 			   ((!mazeData.getCell(CurrentY, CurrentX).getHasRightWall())  && (!mazeData.getCell(nextY, nextX).getHasBottomWall())))) ||
	 		 			 //bottom left
	 		 			   ((CurrentX > nextX) && (CurrentY < nextY) &&
	 		 		 	   (((!mazeData.getCell(CurrentY, CurrentX).getHasBottomWall()) && (!mazeData.getCell(nextY, nextX).getHasRightWall()) )||
	 		 		 		((!mazeData.getCell(CurrentY, CurrentX).getHasLeftWall())  && (!mazeData.getCell(nextY, nextX).getHasTopWall())))) ||
	 		 		 	 //bottom right
	 		 		 	 ((CurrentX > nextX) && (CurrentY > nextY) &&
	 		 		 	  (((!mazeData.getCell(nextY, nextX).getHasBottomWall()) && (!mazeData.getCell(CurrentY, CurrentX).getHasLeftWall()) )||
	 		 		 	   ((!mazeData.getCell(nextY, nextX).getHasRightWall())  && (!mazeData.getCell(CurrentY, CurrentX).getHasTopWall()))))){
	 					return true;
	 				}
	 			}
	 			
		 		if (((CurrentX < nextX) && (CurrentY == nextY) && (!mazeData.getCell(CurrentY, CurrentX).getHasRightWall())) || 
		 				((CurrentX > nextX) && (CurrentY== nextY) && (!mazeData.getCell(CurrentY, CurrentX).getHasLeftWall())) ||
		 				((CurrentY < nextY) && (CurrentX == nextX) && (!mazeData.getCell(CurrentY, CurrentX).getHasBottomWall())) ||
		 				((CurrentY > nextY) && (CurrentX == nextX) && (!mazeData.getCell(CurrentY, CurrentX).getHasTopWall()))){
		 					return true;
		 		}
	 		}
	 		
	 	
	 		return false; 
		}
		
}
