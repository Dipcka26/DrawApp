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
	private Brush brush;
	private int formeIdx;
	private boolean enDeplacement = false;
	private ColorPicker colorPicker;

	private boolean draw_object = false;
	private boolean brush_activated = false;
	private int object_type = 0; // RECTANCLE; DISQUE; LIGNE
	private int modi = -1; // Tells which option the user choosed

	public static final int LOAD_FILE = 0;
	public static final int SAVE_FILE = 1;
	public static final int BRUSH = 2;

	public static final int CHANGE_COLOR = 3;
	public static final int FILL_OBJECT = 4;
	public static final int MOVE_OBJECT = 5;
	public static final int RESET = 6;
	public static final int RESIZE_OBJECT = 7;

	public static final int RECTANGLE = 10;
	public static final int LIGNE = 11;
	public static final int CERCLE = 12;

	public Control(GraphicsContext gc, double cWidth, double cHeight) {
		this.gc = gc;
		this.cWidth = cHeight;
		this.cHeight = cHeight;
		this.brush = new Brush();
		model = new Model();
		init();

	}

	public void init() {
		// model.add(new MRectangle(50, 50, 50, 30));
		// model.add(new MDisque(100, 100, 50));
		// model.add(new MDisque(300, 200, 80));
	}

	public void draw() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.cWidth, this.cHeight);

		for (int i = 0; i < model.getSize(); i++) {
			Shape f = model.get(i);
			f.draw(gc);
		}
		brush.draw(gc);
	}

	public void attrape(MouseEvent e) {

		if (draw_object) { // This code will be executed if we wanne creat a new object
			enDeplacement = true;
			if (RECTANGLE == object_type)
				model.add(new MRectangle(e.getX(), e.getY(), 0, 0));
			else if (CERCLE == object_type)
				model.add(new MDisque(e.getX(), e.getY(), 0));
			else if (LIGNE == object_type)
				model.add(new MLigne(e.getX(), e.getY()));
		} else {
			for (int i = 0; i < model.getSize(); i++) {
				Shape f = model.get(i);
				if (f.estDedans(e.getX(), e.getY())) {
					f.setColor(getColor());
					formeIdx = i;
					enDeplacement = true;
					if (f instanceof MRectangle)
						object_type = RECTANGLE;
					else if (f instanceof MDisque)
						object_type = CERCLE;
					else if (f instanceof MLigne)
						object_type = LIGNE;
					if (modi == FILL_OBJECT)
						f.setFilled(true);
					break;
				}
			}

		}
	}

	public void deplace(MouseEvent e) {

		if (draw_object || modi == RESIZE_OBJECT) {
			if (RECTANGLE == object_type) {
				MRectangle rec;
				if (modi == RESIZE_OBJECT) {
					rec = (MRectangle) model.get(formeIdx);
				} else
					rec = (MRectangle) model.get(model.getSize() - 1);

				rec.setLargeur(e.getX() - rec.getX());
				rec.setHauetru(e.getY() - rec.getY());
			} else if (CERCLE == object_type) {
				MDisque dis;
				if (modi == RESIZE_OBJECT)
					dis = (MDisque) model.get(formeIdx);
				else
					dis = (MDisque) model.get(model.getSize() - 1);
				dis.setRayon(e.getX() - dis.getX());
			} else if (LIGNE == object_type) {
				MLigne lig;
				if (modi == RESIZE_OBJECT)
					lig = (MLigne) model.get(formeIdx);
				else
					lig = (MLigne) model.get(model.getSize() - 1);
				// lig.setX(e.getX() - lig.getX());
				// lig.setY(e.getY() - lig.getY());
				lig.set_endX(e.getX());
				lig.set_endY(e.getY());
			}
		} else if (enDeplacement && brush_activated == false) {
			model.get(formeIdx).setX(e.getX());
			model.get(formeIdx).setY(e.getY());
		} else if (brush_activated)
			brush.fill_brush((int) e.getX(), (int) e.getY(), this.getColor());

		this.draw();

	}

	public void lache(MouseEvent e) {
		enDeplacement = false;
		draw_object = false;
	}

	/*
	 * Gets out what the user pressed and keeps forwarding to the next logical step
	 */
	public void changeMenu(Event e, int MODUS) {

		System.out.println("Modus :" + MODUS);

		modi = MODUS;

		if (MODUS != BRUSH && MODUS != CHANGE_COLOR)
			brush_activated = false;

		switch (MODUS) {

		case LOAD_FILE:
			break;
		case SAVE_FILE:
			break;
		// case CHANGE_COLOR: setColor(getColor()); break;
		case RESET:
			reset();
			break;
		case BRUSH:
			drawBrush();
			break;
		case RECTANGLE:
			addObject(RECTANGLE);
			break;
		case LIGNE:
			addObject(LIGNE);
			break;
		case CERCLE:
			addObject(CERCLE);
			break;
		case MOVE_OBJECT:
			draw();
			break; // TODO
		case FILL_OBJECT:
			setFillObject();
			break; // TODO
		case RESIZE_OBJECT:
			draw();
			break; // TODO

		}
	}

	public void setColorPicker(ColorPicker c) {
		colorPicker = c;
	}

	/* Set the current color the user choosed */
	public void setColor(Color c) {
		colorPicker.setValue(c);
	}

	public Color getColor() {
		return colorPicker.getValue();
	}

	public void addObject(int typeof_object) {

		draw_object = true;
		// enDeplacement = true;
		object_type = typeof_object;
	}

	public void reset() {
		model = new Model();
		brush = new Brush();
		draw();
	}

	public void drawBrush() {
		brush_activated = true;
	}

	public void setFillObject() {

	}

	public void save() {
		fileChooserOption()


	}
	public String toString() {
		String saved  ="";
		for(int i = 0; i < this.model.getSize(); i++) {
			saved += this.model.get(i).toString() + '\n';
			
		}
		saved += this.brush.toString();
		
		return saved;
	}

	public void load() {

	}
}
