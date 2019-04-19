package project.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.event.Event;
import project.model.*;

public class Control {

	private GraphicsContext gc; // pour le Canvas de la vue
	private double cWidth, cHeight;// Hauteur, largeur du Canvas
	private Model model;

	private int formeIdx;
	private boolean enDeplacement = false;
	private ColorPicker colorPicker;
	
	public static final int LOAD_FILE = 0;
	public static final int SAVE_FILE = 1;
	public static final int BRUSH = 2;
	
	public static final int CHANGE_COLOR = 3;
	public static final int FILL_OBJECT = 4;
	
	public static final int FILL_RECTANGLE = 10;
	public static final int FILL_LIGNE = 11;
	public static final int FILL_CERCLE = 12;
	
	
	public Control(GraphicsContext gc, double cWidth, double cHeight) {
		this.gc = gc;
		this.cWidth = cHeight;
		this.cHeight = cHeight;
		model = new Model();
		init();
		
	}

	public void init() {
		model.add(new MRectangle(50, 50, 50, 30));
		model.add(new MDisque(100, 100, 50));
		model.add(new MDisque(300, 200, 80));
	}

	public void draw() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.cWidth, this.cHeight);
		gc.setFill(Color.RED);
		for (int i = 0; i < model.getSize(); i++) {
			Shape f = model.get(i);
			f.draw(gc);
		}
	}

	public void attrape(MouseEvent e) {
		for (int i = 0; i < model.getSize(); i++) {
			Shape f = model.get(i);
			if (f.estDedans(e.getX(), e.getY())) {
				formeIdx = i;
				enDeplacement = true;
				break;
			}
		}
		
	}

	public void deplace(MouseEvent e) {
		if (enDeplacement) {
			model.get(formeIdx).setX(e.getX());
			model.get(formeIdx).setY(e.getY());
			this.draw();
		}
	}
		

	public void lache(MouseEvent e) {
		enDeplacement = false;
	}
		
	/*public void changeMenu(MouseEvent e, int MODUS) {
		System.out.println("Modus :" + MODUS);
		
	}*/
	
	/*Gets out what the user pressed and keeps forwarding to the next logical step*/
	public void changeMenu(Event e, int MODUS) {
		System.out.println("Modus :" + MODUS);
		
		//TODO fill with logic
		/*
		 * switch(MODUS)
		 * case LOAD_FILE: ....
		 * case SAVE_FILE: ....
		 */
	}
	
	public void setColorPicker (ColorPicker c) {
		colorPicker = c;
		
		//TODO
		
	}
	
	/*Set the current color the user choosed*/
	public void setColor() {
		//TODO
		
		//Color c = colorPicker.getValue();
	    //System.out.println("New Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
	}
}
