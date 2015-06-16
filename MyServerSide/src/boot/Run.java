package boot;

import java.io.IOException;

import presenter.Presenter;
import model.MyModelServer;
import view.CliView;

public class Run {

	public static void main(String[] args) throws IOException {
		
		CliView myCliView = new CliView();
		MyModelServer myModelServer = new MyModelServer();
		Presenter presenter = new Presenter(myCliView, myModelServer);
		myCliView.addObserver(presenter);
		myModelServer.addObserver(presenter);
		myCliView.start();

	}

}
