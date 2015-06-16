package presenter;

import java.util.Observable;
import java.util.Observer;

import model.ModelServer;
import view.View;

public class Presenter implements Observer {

	
		/** The view. */
		View view;
		
		/** The model. */
		ModelServer modelServer;
		
		/**
		 * Instantiates a new presnter.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public Presenter(View view, ModelServer modelServer ) {
			this.view = view;
			this.modelServer = modelServer;
			
		}
		
		
		@Override
		public void update(Observable o, Object arg) {
			String param = (String)arg;
			if (o == view){
				
				if (param.equals("Start")){
					modelServer.startServer();
				}
				else if(param.equals("Stop")){
					modelServer.stopServer();
				}
				
			}
			else if (o == modelServer){
				
			}
			
		}
		

	

}
