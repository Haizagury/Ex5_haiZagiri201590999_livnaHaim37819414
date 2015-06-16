package boot;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import model.MyModel;
import model.Properties;
import presenter.Presnter;
import view.MyGuiView;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		Properties properties;
		XMLDecoder decoder = null;
		try {
			
			decoder = new XMLDecoder(new FileInputStream("lib\\Properties.xml"));
			properties = (Properties)decoder.readObject();
			
			if (properties.getUserInterfaceType().endsWith("GUI")){
				MyGuiView myGuiView = new MyGuiView();
				MyModel myModel = new MyModel();
				Presnter presnter = new Presnter(myGuiView, myModel);
				myGuiView.addObserver(presnter);
				myModel.addObserver(presnter);
				myGuiView.start();
			}
			else{
				MyView myView = new MyView(null, null);
				MyModel myModel = new MyModel();
				Presnter presnter = new Presnter(myView , myModel);
				myView.addObserver(presnter);
				myModel.addObserver(presnter);	
				myView.start();
			}

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		
		 
		

	}

}
