/**
 * 
 */
package view;



//import javax.swing.text.MaskFormatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import presenter.Presnter.Command;
import algorithms.mazeGenerator.Maze;
import algorithms.search.Solution;

/**
 * @author HFL
 *
 */


public class MazeWindow extends BasicWindow {

	private MyGuiView view; 
	Maze mazeData;
	private int mazeCounter = 0;
	Shell optionShell;
	Shell helpShell;
	MazeDisplay maze;	
	
	private String optionsMazeGenerateType;
	private String optionsSearchType;
	private String optionsHeuristic;
	private Integer optionsRows;
	private Integer optionsCols;
	private Integer optionsXStartPoint;
	private Integer optionsYStartPoint;
	private Boolean optionsAllowDiagonals;
	private String optionsGameCharecter ;
	private String optionsUserInterfaceType = new String("GUI");
	
	public Maze getMazeData() {
		return mazeData;
	}

	public void setMazeData(Maze mazeData) {
		this.mazeData = mazeData;
	}

	public MazeWindow(String title, int width, int height, MyGuiView view/*,Maze m*/) {
		super(title, width, height);
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		try {
			shell.setLayout(new GridLayout(1, false));
			uploadNewMaze();
					
			Command command1 = view.userCommands.get("getProperties");
			view.commandQueue.add(command1);
			view.Notify("Command getProperties");
			getOptions();
			
			//Close Shell with X-RED Button
			shell.addListener(SWT.Close, new Listener()
		    {
		        public void handleEvent(Event event)
		        {
		        	exit();		
		        }
		    });
			
			
			//Menu
			createMenu();
			
		    //Option Shell
		    createOptionShell();
		    
		    //Help Shell
		    createHelpShell();
		    
			//החלק של הכפתור ושני שדות הטקטס צריך למחוק בסוף הם לצורך נוחות בדיקה
/*
		    Button moveButton = new Button(shell, SWT.PUSH);
			moveButton.setText("move");
			moveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
			
			maze=new MazeDisplay(shell, SWT.BORDER);
			maze.setMazeData(mazeData,getOptionsAllowDiagonals(),getOptionsGameCharecter());
			maze.startMazeDisplay();
			maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,7));
			
			final Text xText = new Text(shell, SWT.BORDER);
			xText.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
			
			final Text yText = new Text(shell, SWT.BORDER);
			yText.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));

			moveButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					maze.move((new Integer(xText.getText()).intValue()), (new Integer(yText.getText()).intValue()));
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			*/
			// כאן נגמר הקטע קוד שיש להוריד לפני ההגשה
					
			// * את החלק הזה צריך להוסיף במקום בקוד עם הכפתורים
			 
			maze=new MazeDisplay(shell, SWT.BORDER);
			maze.setMazeData(mazeData,getOptionsAllowDiagonals(),getOptionsGameCharecter());
			maze.startMazeDisplay();
			maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1));
			
			maze.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					
					Boolean win = maze.mouseMove(arg0.x, arg0.y);
					if (win){
						int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
						MessageBox mb = new MessageBox(shell,style);
						mb.setMessage("You success to solve the maze. \n" + "Do You Want to play again?");
						if (mb.open() == SWT.YES){
							
							uploadNewMaze();
							shell.setVisible(false);
							Shell oldShell = shell;
							shell = new Shell(display);
							shell.setText(oldShell.getText());
							shell.setSize(oldShell.getSize());
							
							initWidgets();
							shell.open();
						}
						else {
							exit();
						}
					}
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			maze.setFocus();
			maze.addKeyListener(new KeyListener() {
				
				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
									
					Boolean win = false;
					//up left - a
					if (arg0.keyCode == 97){
						win = maze.keyMove(-1, -1);
					}
					//up right - s
					if (arg0.keyCode == 115){
						win = maze.keyMove(1, -1);
					}
					
					//down right - x
					if (arg0.keyCode == 120){
						win = maze.keyMove(1, 1);
					}
					
					//down left - z
					if (arg0.keyCode == 122){
						win = maze.keyMove(-1, 1);
					}
					
					if (arg0.keyCode == SWT.ARROW_UP){
						win = maze.keyMove(0, -1);
					}
					else if (arg0.keyCode == SWT.ARROW_DOWN){
						win = maze.keyMove(0, 1);
					}
					else if (arg0.keyCode == SWT.ARROW_LEFT){
						win = maze.keyMove(-1,0);
					}
					else if (arg0.keyCode == SWT.ARROW_RIGHT){
						win = maze.keyMove(1, 0);
					}
					

					if (win){
						
						int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
						MessageBox mb = new MessageBox(shell,style);
						mb.setMessage("You success to solve the maze. \n" + "Do You Want to play again?");
						if (mb.open() == SWT.YES){
							
							uploadNewMaze();
							shell.setVisible(false);
							Shell oldShell = shell;
							shell = new Shell(display);
							shell.setText(oldShell.getText());
							shell.setSize(oldShell.getSize());
							
							initWidgets();
							shell.open();
						}
						else {
							exit();
						}
					}
					
					
				}
			});

		}catch(SWTException e){
			
		}
		catch (Exception e) {
			
		}
				
	}

	public void displayMsg(String s) {
		try {
			MessageBox mb = new MessageBox(shell);
			mb.setMessage(s);
			mb.open();
		} catch (Exception e) {
			
		}
		
		
	}
	
	private void setOptions(){
		StringBuilder sb = new StringBuilder();
		sb.append(getOptionsMazeGenerateType());
		sb.append(",");
		sb.append(getOptionsSearchType());
		sb.append(",");
		sb.append(getOptionsHeuristic());
		sb.append(",");
		sb.append(getOptionsRows().toString());
		sb.append(",");
		sb.append(getOptionsCols().toString());
		sb.append(",");
		sb.append(getOptionsXStartPoint().toString());
		sb.append(",");
		sb.append(getOptionsYStartPoint().toString());
		sb.append(",");
		sb.append(getOptionsAllowDiagonals().toString());
		sb.append(",");
		sb.append(getOptionsGameCharecter());
		sb.append(",");
		sb.append(getOptionsUserInterfaceType());
		
		view.runOptions = sb.toString();
	}
	
	private void getOptions() {
		String[] options = view.runOptions.split(",");
		setOptionsMazeGenerateType(options[0]);
		setOptionsSearchType(options[1]);
		setOptionsHeuristic(options[2]);
		setOptionsRows(Integer.valueOf(options[3]));
		setOptionsCols(Integer.valueOf(options[4]));
		setOptionsXStartPoint(Integer.valueOf(options[5]));
		setOptionsYStartPoint(Integer.valueOf(options[6]));
		setOptionsAllowDiagonals(Boolean.valueOf(options[7]));
		setOptionsGameCharecter(options[8]);
		
	}
	
	public String getOptionsMazeGenerateType() {
		return optionsMazeGenerateType;
	}

	public void setOptionsMazeGenerateType(String optionsMazeGenerateType) {
		this.optionsMazeGenerateType = optionsMazeGenerateType;
	}

	public String getOptionsSearchType() {
		return optionsSearchType;
	}

	public void setOptionsSearchType(String optionsSearchType) {
		this.optionsSearchType = optionsSearchType;
	}

	public String getOptionsHeuristic() {
		return optionsHeuristic;
	}

	public void setOptionsHeuristic(String optionsHeuristic) {
		this.optionsHeuristic = optionsHeuristic;
	}

	public Integer getOptionsRows() {
		return optionsRows;
	}

	public void setOptionsRows(Integer optionsRows) {
		this.optionsRows = optionsRows;
	}

	public Integer getOptionsCols() {
		return optionsCols;
	}

	public void setOptionsCols(Integer optionsCols) {
		this.optionsCols = optionsCols;
	}

	public Integer getOptionsXStartPoint() {
		return optionsXStartPoint;
	}

	public void setOptionsXStartPoint(Integer optionsXStartPoint) {
		this.optionsXStartPoint = optionsXStartPoint;
	}

	public Integer getOptionsYStartPoint() {
		return optionsYStartPoint;
	}

	public void setOptionsYStartPoint(Integer optionsYStartPoint) {
		this.optionsYStartPoint = optionsYStartPoint;
	}
	
	public Boolean getOptionsAllowDiagonals() {
		return optionsAllowDiagonals;
	}

	public void setOptionsAllowDiagonals(Boolean optionsAllowDiagonals) {
		this.optionsAllowDiagonals = optionsAllowDiagonals;
	}

	public String getOptionsGameCharecter() {
		return optionsGameCharecter;
	}

	public void setOptionsGameCharecter(String optionsGameCharecter) {
		this.optionsGameCharecter = optionsGameCharecter;
	}

	public String getOptionsUserInterfaceType() {
		return optionsUserInterfaceType;
	}

	public void setOptionsUserInterfaceType(String optionsUserInterfaceType) {
		this.optionsUserInterfaceType = optionsUserInterfaceType;
	}

	
	public void uploadNewMaze(){
		try{
			mazeCounter++;
			Command command = view.userCommands.get("generateMaze");
			view.commandQueue.add(command);
			view.Notify("Command maze" + mazeCounter);
			
			command = view.userCommands.get("displayMaze");
			view.commandQueue.add(command);
			view.Notify("Command maze" + mazeCounter);
		} catch (Exception e) {
			
		}
		
	}
	
	public void exit(){
		shell.setVisible(false);
		shell.dispose();
		Command command1 = view.userCommands.get("exit");
		view.commandQueue.add(command1);
		view.Notify("Command exit");
		
	}
	
	public void createMenu(){
		try {
			Menu menuBar = new Menu(shell, SWT.BAR);
		    MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		    fileMenuHeader.setText("&Game");
		    
		    
		    
		    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		    fileMenuHeader.setMenu(fileMenu);

		    MenuItem newGameItem = new MenuItem(fileMenu, SWT.PUSH);
		    newGameItem.setText("&New Game\tF2");
		    newGameItem.setAccelerator(SWT.F2);
		    
		    
		    MenuItem optionsItem = new MenuItem(fileMenu, SWT.PUSH);
		    optionsItem.setText("&Options\tF5");
		    optionsItem.setAccelerator(SWT.F5);
		    
		    new MenuItem(fileMenu, SWT.SEPARATOR);

		    MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		    fileExitItem.setText("E&xit");
		    
		    MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		    helpMenuHeader.setText("&Help");
		    

		    Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		    helpMenuHeader.setMenu(helpMenu);
		    
		    MenuItem helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
		    helpGetHelpItem.setText("&Get Help\tF1");
		    helpGetHelpItem.setAccelerator(SWT.F1);
		    
		    MenuItem helpGetSolutionItem = new MenuItem(helpMenu, SWT.PUSH);
		    helpGetSolutionItem.setText("&Get Solution\tF3");
		    helpGetSolutionItem.setAccelerator(SWT.F3);
		    
		    MenuItem helpGetHintItem = new MenuItem(helpMenu, SWT.PUSH);
		    helpGetHintItem.setText("&Get Hint\tF4");
		    helpGetHintItem.setAccelerator(SWT.F4);
		    
		    helpGetSolutionItem.addSelectionListener(new  SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Command command1 = view.userCommands.get("solveMaze");
					view.commandQueue.add(command1);
					String x = new String(Integer.toString(maze.gameCharecter.x));
					String y = new String(Integer.toString(maze.gameCharecter.y));
					view.Notify("Command maze" + mazeCounter + "," +  y + "," + x);
					
					command1 = view.userCommands.get("displaySolution");
					view.commandQueue.add(command1);
					view.Notify("Command maze" + mazeCounter);
					
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    helpGetHintItem.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Command command1 = view.userCommands.get("solveMaze");
					view.commandQueue.add(command1);
					String x = new String(Integer.toString(maze.gameCharecter.x));
					String y = new String(Integer.toString(maze.gameCharecter.y));
					view.Notify("Command maze" + mazeCounter + "," +  y + "," + x);
					
					command1 = view.userCommands.get("displayHint");
					view.commandQueue.add(command1);
					view.Notify("Command maze" + mazeCounter);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    fileExitItem.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					//shell.setVisible(false);
					exit();
					//shell.dispose();
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    newGameItem.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					shell.setVisible(false);
					Shell oldShell = shell;
					shell = new Shell(display);
					shell.setText(oldShell.getText());
					shell.setSize(oldShell.getSize());
					 
					initWidgets();
					shell.open();
				
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    optionsItem.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if (!optionShell.getEnabled())
						optionShell.setEnabled(true);
					
					Command command1 = view.userCommands.get("getProperties");
					view.commandQueue.add(command1);
					view.Notify("Command getProperties");
					
					getOptions();
					
					optionShell.open();
					shell.setEnabled(false);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    helpGetHelpItem.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					if (!helpShell.getEnabled())
						helpShell.setEnabled(true);
					helpShell.open();
					shell.setEnabled(false);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});

		    shell.setMenuBar(menuBar);

		} catch (SWTException e) {
			
		}
		catch (Exception e) {
			
		}
		
	}
	
	public void createOptionShell(){
		try {
			optionShell = new Shell(shell);
		    optionShell.setText("Options");
		    optionShell.setSize(300, 400);
		    optionShell.setLayout(new GridLayout(2,false));	    										
											    
		    //Board Properties Group
		    Group boardGroup = new Group(optionShell, SWT.None);
		    boardGroup.setText("Board");
		    boardGroup.setLayout(new GridLayout(4,false));
		    boardGroup.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false,2,1));
		    
		    
		    Label boardWidthLbl = new Label(boardGroup,SWT.BOLD);
		    boardWidthLbl.setText("Width");
		    boardWidthLbl.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1,1));
		   
		    final Spinner boardWidthSpn = new Spinner(boardGroup,SWT.None);
		    boardWidthSpn.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1,1));
		    boardWidthSpn.setMaximum(100);
		    boardWidthSpn.setMinimum(5);
		    boardWidthSpn.setSelection(getOptionsRows());
		    
		    Label boardHeightLbl = new Label(boardGroup,SWT.BOLD);
		    boardHeightLbl.setText("Height");
		    boardHeightLbl.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false,1,1));
		    
		    final Spinner boardHeighSpn = new Spinner(boardGroup,SWT.None);
		    boardHeighSpn.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1,1));
		    boardHeighSpn.setMaximum(100);
		    boardHeighSpn.setMinimum(5);
		    boardHeighSpn.setSelection(getOptionsCols());
		    
		    Group boardGenerateGrp = new Group(boardGroup, SWT.None);
		    boardGenerateGrp.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false,4,1));
		    boardGenerateGrp.setText("Board Type Generating");
		    boardGenerateGrp.setLayout(new RowLayout(SWT.HORIZONTAL));
		    
		    final Button dfsGenerateBtn =  new Button(boardGenerateGrp, SWT.RADIO);
		    dfsGenerateBtn.setText("DFS");
		   
		    
		    final Button randomGenerateBtn = new Button(boardGenerateGrp, SWT.RADIO);
		    randomGenerateBtn.setText("RANDOM");
		    
		    if (getOptionsMazeGenerateType().equals("DFS"))
		    	 dfsGenerateBtn.setSelection(true);
		    else
		    	randomGenerateBtn.setSelection(true);
		    
		    Group boardCharacterGrp = new Group(boardGroup, SWT.None);
		    boardCharacterGrp.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false,4,1));
		    boardCharacterGrp.setText("Character");
		    boardCharacterGrp.setLayout(new RowLayout(SWT.HORIZONTAL));
		    
		    Button bambaCharacterBtn =  new Button(boardCharacterGrp, SWT.RADIO);
		    bambaCharacterBtn.setText("Bamba Baby");
		    
		    Button bissliCharacterBtn = new Button(boardCharacterGrp, SWT.RADIO);
		    bissliCharacterBtn.setText("Bissli Boys");
		    
		    if (getOptionsGameCharecter().equals("Bamba"))
		    	bambaCharacterBtn.setSelection(true);
		    else
		    	bissliCharacterBtn.setSelection(true);;
		    
		    Label startPointLbl = new Label(boardGroup,SWT.BOLD);
		    startPointLbl.setText("Start Point");
		    startPointLbl.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 4,1));
		    
		    Label xStartPointLbl = new Label(boardGroup,SWT.BOLD);
		    xStartPointLbl.setText("X");
		    xStartPointLbl.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1,1));
		   
		    final Spinner xStartPointSpn = new Spinner(boardGroup,SWT.None);
		    xStartPointSpn.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1,1));
		    xStartPointSpn.setMaximum(boardWidthSpn.getSelection()-1);
		    if (getOptionsXStartPoint() > boardWidthSpn.getSelection()-1)
		    	xStartPointSpn.setSelection(boardWidthSpn.getSelection()-1);
		    else
		    	xStartPointSpn.setSelection(getOptionsXStartPoint());
		    
		    Label yStartPointLbl = new Label(boardGroup,SWT.BOLD);
		    yStartPointLbl.setText("Y");
		    yStartPointLbl.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false,1,1));
		   
		    final Spinner yStartPointSpn = new Spinner(boardGroup,SWT.None);
		    yStartPointSpn.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1,1));
		    yStartPointSpn.setMaximum(boardHeighSpn.getSelection()-1);
		    if (getOptionsYStartPoint() > boardHeighSpn.getSelection()-1)
		    	yStartPointSpn.setSelection(boardHeighSpn.getSelection()-1);
		    else
		    	yStartPointSpn.setSelection(getOptionsYStartPoint());
		    
		    boardWidthSpn.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					xStartPointSpn.setMaximum(boardWidthSpn.getSelection()-1);
					if (xStartPointSpn.getMaximum() > boardWidthSpn.getSelection()-1)
						xStartPointSpn.setSelection(boardWidthSpn.getSelection()-1);
					setOptionsRows(boardWidthSpn.getSelection());
					
				}
			});
		    
		    boardHeighSpn.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					yStartPointSpn.setMaximum(boardHeighSpn.getSelection()-1);
					if (yStartPointSpn.getMaximum() > boardHeighSpn.getSelection()-1)
						yStartPointSpn.setSelection(boardHeighSpn.getSelection()-1);
					setOptionsCols(boardHeighSpn.getSelection());
					
				}
			});
		    
		    dfsGenerateBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsMazeGenerateType(dfsGenerateBtn.getText());
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    randomGenerateBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsMazeGenerateType(randomGenerateBtn.getText());
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    xStartPointSpn.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					setOptionsXStartPoint(xStartPointSpn.getSelection());
					
				}
			});
		   
		    yStartPointSpn.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					setOptionsYStartPoint(yStartPointSpn.getSelection());
					
				}
			});
		    
		    bambaCharacterBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsGameCharecter("Bamba");
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    bissliCharacterBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsGameCharecter("Bissli");
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		  //Solution Properties Group
		    Group solutiondGroup = new Group(optionShell, SWT.None);
		    solutiondGroup.setText("Solution");
		    solutiondGroup.setLayout(new GridLayout(2,false));
		    solutiondGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false,2,1));
		    
		    final Button astarSolutionBtn =  new Button(solutiondGroup, SWT.RADIO);
		    astarSolutionBtn.setText("A*");
		    
		    
		    final Button bfsSolutionBtn = new Button(solutiondGroup, SWT.RADIO);
		    bfsSolutionBtn.setText("BFS");
		    
		    if (getOptionsSearchType().equals("BFS"))
		    	bfsSolutionBtn.setSelection(true);
		    else
		    	astarSolutionBtn.setSelection(true);
		    
		    final Group astarHeuristicGrp = new Group(solutiondGroup, SWT.None);
		    astarHeuristicGrp.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false,2,1));
		    astarHeuristicGrp.setText("A* Heuristic");
		    astarHeuristicGrp.setLayout(new RowLayout(SWT.HORIZONTAL/*.VERTICAL*/));
		    
		    
		    
		    final Button airHeuristicBtn =  new Button(astarHeuristicGrp, SWT.RADIO);
		    airHeuristicBtn.setText("Air Distance");
		   
		    
		    final Button manhattanHeuristicBtn = new Button(astarHeuristicGrp, SWT.RADIO);
		    manhattanHeuristicBtn.setText("Manhattan Distance");
		    
		    if (getOptionsHeuristic().equals("AIR"))
		    	 airHeuristicBtn.setSelection(true);
		    else
		    	manhattanHeuristicBtn.setSelection(true);
		    
		    if (bfsSolutionBtn.getSelection()){
		    	astarHeuristicGrp.setEnabled(false);
			    airHeuristicBtn.setEnabled(false);
			    manhattanHeuristicBtn.setEnabled(false);
		    }
		    
		    final Group allowDiagonalsGrp = new Group(solutiondGroup, SWT.None);
		    allowDiagonalsGrp.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false,3,1));
		    allowDiagonalsGrp.setText("Allow Diagonals");
		    allowDiagonalsGrp.setLayout(new RowLayout(SWT.HORIZONTAL));
		    
		    
		    
		    final Button yesAllowDiagonalsBtn =  new Button(allowDiagonalsGrp, SWT.RADIO);
		    yesAllowDiagonalsBtn.setText("Yes");
		    
		    final Button notAllowDiagonalsBtn = new Button(allowDiagonalsGrp, SWT.RADIO);
		    notAllowDiagonalsBtn.setText("No");
		    
		    if (getOptionsAllowDiagonals())
		    	yesAllowDiagonalsBtn.setSelection(true);
		    else
		    	notAllowDiagonalsBtn.setSelection(true);
		    	
		    
		    bfsSolutionBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsSearchType("BFS");
					astarHeuristicGrp.setEnabled(false);
				    airHeuristicBtn.setEnabled(false);
				    manhattanHeuristicBtn.setEnabled(false);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    astarSolutionBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsSearchType("ASTAR");
					astarHeuristicGrp.setEnabled(true);
				    airHeuristicBtn.setEnabled(true);
				    manhattanHeuristicBtn.setEnabled(true);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    airHeuristicBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsHeuristic("AIR");
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    //MANHATTAN
		    manhattanHeuristicBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsHeuristic("MANHATTAN");
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    yesAllowDiagonalsBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsAllowDiagonals(true);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    notAllowDiagonalsBtn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptionsAllowDiagonals(false);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		    
		    Button optionOKButton = new Button(optionShell, SWT.PUSH);
		    optionOKButton.setText("OK");
		    optionOKButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		    
		    Button optionCancleButton = new Button(optionShell, SWT.PUSH);
		    optionCancleButton.setText("Cancle");
		    optionCancleButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		    
		    
		    
		    optionCancleButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					optionShell.setVisible(false);
					shell.setEnabled(true);
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		    
		  //Close optionShell with X-RED Button
		    optionShell.addListener(SWT.Close, new Listener()
		    {
		        public void handleEvent(Event event)
		        {
		        	event.doit = false;
		        	optionShell.setVisible(false);
		        	shell.setEnabled(true);
		        }
		    });
		    
		    optionOKButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setOptions();
					Command command1 = view.userCommands.get("setProperties");
					view.commandQueue.add(command1);
					view.Notify("Command " + view.runOptions); 
					
					uploadNewMaze();
					
					optionShell.setVisible(false);
					shell.setVisible(false);
					Shell oldShell = shell;
					shell = new Shell(display);
					shell.setText(oldShell.getText());
					shell.setSize(oldShell.getSize());
					
					initWidgets();
					shell.open();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		
		} catch (SWTException e) {
			
		}
		catch (Exception e) {
			
		}
		
		    
	  
	}
	
	public void createHelpShell(){
		 helpShell = new Shell(shell);
		 helpShell.setText("Help");
		 helpShell.setSize(630, 700);
		 helpShell.setLayout(new GridLayout(1,false));
		 
		 Font font1 = new Font(display,"Arial", 18, SWT.BOLD|SWT.UNDERLINE_SINGLE);
		 Font font2 = new Font(display,"Arial", 12, SWT.BOLD);
		 Font font3 = new Font(display,"Arial", 10, SWT.NONE);
		 
		 Label lbl = new Label(helpShell,SWT.NONE);
		 lbl.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl.setFont(font1);
		 lbl.setForeground(new Color(display, 0, 0, 255));
		 lbl.setText("Maze Game\n");
		 
		 Label lbl1 = new Label(helpShell,SWT.NONE);
		 lbl1.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl1.setFont(font3);
		 lbl1.setText("\nMaze is a puzzle built passageways branching.\nThe goal: find a way through.");
		 
		 Label lbl2 = new Label(helpShell,SWT.NONE);
		 lbl2.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl2.setFont(font2);
		 lbl2.setForeground(new Color(display, 0, 0, 255));
		 lbl2.setText("\nTo start New Game :");
		 
		 Label lbl3 = new Label(helpShell,SWT.COLOR_GREEN);
		 lbl3.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl3.setFont(font3);
		 lbl3.setText("Click the Game menu, and then click New Game (or press F2 on keyboard).");
		 
		 Label lbl4 = new Label(helpShell,SWT.NONE);
		 lbl4.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl4.setFont(font2);
		 lbl4.setForeground(new Color(display, 0, 0, 255));
		 lbl4.setText("\nTo change game option:");
		 
		 Label lbl5 = new Label(helpShell,SWT.NONE);
		 lbl5.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl5.setFont(font3);
		 lbl5.setText("You can adjust the maze size, character image and more.\n\n   1.Click the Game menu, and then click Options (or press F5 on keyboard).\n   2.Make your choices, and then click OK.");
		 
		 
		 Label lbl6 = new Label(helpShell,SWT.NONE);
		 lbl6.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl6.setFont(font2);
		 lbl6.setForeground(new Color(display, 0, 0, 255));
		 lbl6.setText("\nTo get a solution or hint:");
		 
		 Label lbl7 = new Label(helpShell,SWT.NONE);
		 lbl7.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl7.setFont(font3);
		 lbl7.setText("You can ask for a maze solution or a hint (the next step).\n\nClick on the Help menu, and then Get Solution(F3) or Get  Hint(F4)  respectively.");
		 
		 Label lbl8 = new Label(helpShell,SWT.NONE);
		 lbl8.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl8.setFont(font2);
		 lbl8.setForeground(new Color(display, 0, 0, 255));
		 lbl8.setText("\nHow to play?");
		 
		 Label lbl9 = new Label(helpShell,SWT.NONE);
		 lbl9.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl9.setFont(font3);
		 lbl9.setText("You can move your character through the maze with the mouse or using the keyboard arrows.");
		 
		 Label lbl10 = new Label(helpShell,SWT.NONE);
		 lbl10.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl10.setFont(font3);
		 lbl10.setText("If you select a shift diagonals keyboard keys are:\n   a - Left up\n   s - right up\n   z - Left Down\n   x - right down");
		 
		 Label lbl11 = new Label(helpShell,SWT.NONE|SWT.UNDERLINE_SINGLE);
		 lbl11.setLayoutData(new GridData(SWT.FILL, SWT.None, false,false,1,1));
		 lbl11.setFont(font1);
		 lbl11.setText("\n\nEnjoy the game.");
		 
		//Close optionShell with X-RED Button
		 helpShell.addListener(SWT.Close, new Listener()
		    {
		        public void handleEvent(Event event)
		        {
		        	event.doit = false;
		        	helpShell.setVisible(false);
		        	shell.setEnabled(true);
		        }
		    });
	}
	

	public void displaySolution(Solution s) {
		maze.draw(s, false);
		maze.setEnabled(false);
	}

	
	public void displayHint(Solution s) {
		maze.draw(s, true);
	}

}
