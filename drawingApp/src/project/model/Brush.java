package project.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brush {

	List<Integer> x_koordinates; 
	List<Integer> y_koordinates;
	List<Color>   colors;
	int sizeof_elements;
	public Brush() 
	{
		x_koordinates = new ArrayList<>();
		y_koordinates = new ArrayList<>();
		colors= new ArrayList<>();
		sizeof_elements = 0;
	}
	
	public void fill_brush(int x, int y, Color c) {
		x_koordinates.add(x);
		y_koordinates.add(y);
		colors.add(c);
		sizeof_elements++;
	}
	
	public int numberofElements() {
		return sizeof_elements;
	}
	
	
	public void draw(GraphicsContext gc) {
		
		int x;
		int y;
		
		for(int i = 0; i < sizeof_elements;i++) {
			Color c = colors.get(i);
			x = x_koordinates.get(i);
			y = y_koordinates.get(i);
			gc.setStroke(c);
		//	gc.setLineWidth(10);
			gc.strokeLine(x, y, x, y);
		}
	}
	
	public String toString() {
		String ret = "";
		if(this.numberofElements() > 0) {
			ret = "BRUSH: " + '\n';
			for (int i  = 0; i < this.numberofElements(); i++) {
				ret += this.x_koordinates.get(i) + " " + this.y_koordinates.get(i) + " " + this.colors.get(i).toString();
				//we put one point separator, in this way we can split each point from brush when we were loading
				ret += ','; 
			}
		}

		
		return ret;
	}
	
	
	
}
