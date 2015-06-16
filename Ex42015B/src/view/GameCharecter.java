package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class GameCharecter {

		   int x,y;
		   String gameCharecterPicture;
		   String filename = null;
		   
		   
		   public GameCharecter(int x,int y, String gameCharecterPicture) {
			this.x=x;
			this.y=y;
			this.gameCharecterPicture = gameCharecterPicture;
			if (this.gameCharecterPicture.equalsIgnoreCase("Bamba"))
				filename = new String("lib\\bambaBaby.jpg");
			else
				filename = new String("lib\\bissliBoys.jpg");
		   }
		   
		   public void paint(PaintEvent e,int w,int h){
			   
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
					
//					@Override
//					public void internal_dispose_GC(long arg0, GCData arg1) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public long internal_new_GC(GCData arg0) {
//						// TODO Auto-generated method stub
//						return 0;
//					}
				}, filename);  
				
				ImageData imageData =  image.getImageData();
				e.gc.drawImage(image, 0, 0, imageData.width , imageData.height, x, y, w, h);
		   }
		   
		   public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void paint(PaintEvent e,int w,int h, int x, int y){
			   
				Image image = new Image(new Device() {

//					@Override
//					public void internal_dispose_GC(long arg0, GCData arg1) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public long internal_new_GC(GCData arg0) {
//						// TODO Auto-generated method stub
//						return 0;
//					}
					
					@Override
	
					public int internal_new_GC(GCData arg0) {
						// TODO Auto-generated method stub
						return 0;
					}

					@Override
					public void internal_dispose_GC(int arg0, GCData arg1) {
						// TODO Auto-generated method stub
						
					}
					
					
				}, filename);  
				
				ImageData imageData =  image.getImageData();
				e.gc.drawImage(image, 0, 0, imageData.width, imageData.height, x, y, w, h);
		   }
		

}
