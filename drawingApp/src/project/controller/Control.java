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
	
	private boolean draw_object = false;
	private int object_type = 0; //1 = RECTANCLE; 2 = DISQUE; 3 = LIGNE
	public static final int LOAD_FILE = 0;
	public static final int SAVE_FILE = 1;
	public static final int BRUSH = 2;
	
	public static final int CHANGE_COLOR = 3;
	public static final int FILL_OBJECT = 4;
	public static final int MOVE_OBJECT = 5;
	public static final int RESET = 6;
	
	public static final int RECTANGLE = 10;
	public static final int LIGNE = 11;
	public static final int CERCLE = 12;
	
	
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
	
		for (int i = 0; i < model.getSize(); i++) {
			Shape f = model.get(i);
			//f.setColor(getColor());
			f.draw(gc);
		}
	}

	public void attrape(MouseEvent e) {
		
		if(draw_object == false)
			for (int i = 0; i < model.getSize(); i++) {
				Shape f = model.get(i);
				if (f.estDedans(e.getX(), e.getY())) {
					f.setColor(getColor());
					formeIdx = i;
					enDeplacement = true;
					break;
				}
			}
		else {//This code will be executed if we wanne creat a new object
			enDeplacement = true;
			if(RECTANGLE == object_type)
				model.add(new MRectangle(e.getX(), e.getY(), 0, 0));
			else if(CERCLE ==  object_type)
				model.add(new MDisque(e.getX(), e.getY(), 0));
			else if(LIGNE == object_type){
				model.add(new MLigne(e.getX(), e.getY()));
			}
		}
		
	}

	public void deplace(MouseEvent e) {
		if(draw_object) {
		 //model.get(model.getSize() - 1).(e.getX());
		if(RECTANGLE == object_type){
			MRectangle rec= (MRectangle) model.get(model.getSize() - 1 );		
			rec.setLargeur(e.getX() - rec.getX());
			rec.setHauetru(e.getY() - rec.getY() );		
		}
		else if(CERCLE == object_type) {
			MDisque dis= (MDisque) model.get(model.getSize() - 1 );		
			dis.setRayon(e.getX() - dis.getX());
		}
		else if(LIGNE == object_type) {
			MLigne lig = (MLigne) model.get(model.getSize() - 1 );		
			lig.set_endX(e.getX() );
			lig.set_endY(e.getY() );
		}
		 
		 this.draw();
		
		}
		else if(enDeplacement){// (enDeplacement) {
			model.get(formeIdx).setX(e.getX());
			model.get(formeIdx).setY(e.getY());
			this.draw();
		}
		
	}
		

	public void lache(MouseEvent e) {
		enDeplacement = false;
		draw_object = false;
	}
		
	/*Gets out what the user pressed and keeps forwarding to the next logical step*/
	public void changeMenu(Event e, int MODUS) {
		
		System.out.println("Modus :" + MODUS);
			
		switch(MODUS) {
		
	//	case CHANGE_COLOR: setColor(getColor()); break;
		case RESET: reset(); break;
		case RECTANGLE: addObject(RECTANGLE); break;
		case LIGNE:	addObject(LIGNE); break;
		case CERCLE: addObject(CERCLE); break;
		}
	}
	
	public void setColorPicker (ColorPicker c) {
		colorPicker = c;
	}
	
	/*Set the current color the user choosed*/
	public void setColor(Color c) {
		colorPicker.setValue(c);
		//Color c = colorPicker.getValue();
	    //System.out.println("New Color's RGB = "+c.getRed()+" "+c.getGreen()+" "+c.getBlue());
	}
	
	public Color getColor() {
		
		return colorPicker.getValue();
	}
	
	public void addObject(int typeof_object) {
		
		draw_object = true;
		enDeplacement = true;
		object_type = typeof_object;
	}
	public void reset() {
		model = new Model();
		draw();
	}
}
