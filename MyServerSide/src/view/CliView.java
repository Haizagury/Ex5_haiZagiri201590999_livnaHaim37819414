package view;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Observable;


public class CliView extends Observable implements View {

	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out;
	
	@Override
	public void start() {
		
		out.print("Enter command: "); 
		 
 		try { 
 			String line = in.readLine(); 
 			 
 			while (!line.equals("exit")) 
 			{ 
 				setChanged();
 				notifyObservers("Start");
 				 
 				out.print("Enter command: "); 
 				line = in.readLine(); 
 			} 
 			out.println("Goodbye");
 			exit();
 						 
 		} catch (IOException e) {			 
 			System.out.println("can't read/write from/to in/out streams"); 
 		} finally { 
 			try { 
 				in.close(); 
 				out.close(); 
 			} catch (IOException e) {				 
 				System.out.println("can't close from in/out streams"); 
 			}		 
 		}
		
		
	}

	@Override
	public void exit() {
		setChanged();
		notifyObservers("Stop");
		
	}

}
