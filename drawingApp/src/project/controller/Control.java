package project.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javafx.event.Event;
import project.model.*;

public class Control {

	private GraphicsContext gc; // pour le Canvas de la vue
	private double cWidth, cHeight;// Hauteur, largeur du Canvas
	private Model model;
	private Brush brush;
	private int formeIdx = -1;
	private boolean enDeplacement = false;
	private ColorPicker colorPicker;
	private FileChooser fc;

	private boolean draw_object = false;
	private boolean brush_activated = false;
	private int object_type = 0; // RECTANCLE; DISQUE; LIGNE
	private int modi = -1; // Tells which option the user choosed
	
	public static final double BRUSH_WIDTH = 5; //STANDART 5 
	
	public static final int LOAD_FILE = 0;
	public static final int SAVE_FILE = 1;
	public static final int BRUSH = 2;

	
	
	public static final int CHANGE_COLOR = 3;
	public static final int FILL_OBJECT = 4;
	public static final int MOVE_OBJECT = 5;
	public static final int RESET = 6;
	public static final int RESIZE_OBJECT = 7;
	public static final int DELETE_OBJECT = 8;
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
		gc.setLineWidth(BRUSH_WIDTH);
		
		
	}

	public void draw() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.cWidth, this.cHeight);

		brush.draw(gc);
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
				model.add(new MRectangle(e.getX(), e.getY(), 0, 0, getColor()));
			else if (CERCLE == object_type)
				model.add(new MDisque(e.getX(), e.getY(), 0,getColor()));
			else if (LIGNE == object_type)
				model.add(new MLigne(e.getX(), e.getY(),getColor()));
		} else if( modi == MOVE_OBJECT || modi == FILL_OBJECT  || modi == DELETE_OBJECT || modi == RESIZE_OBJECT){
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
					if(modi == DELETE_OBJECT)
						model.delete(i);
					break;
				}
			}

		}
		
		
		draw();
	}

	public void deplace(MouseEvent e) {

		if (draw_object || (modi == RESIZE_OBJECT && formeIdx != -1)) {
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
		} else if (enDeplacement && brush_activated == false && modi == MOVE_OBJECT) {
			
			if(model.get(formeIdx) instanceof MLigne) {
				MLigne lig = (MLigne) model.get(formeIdx);
				
				
//				double x = lig.getX();
//				double y = lig.getY();
				
				lig.setX(e.getX());
				lig.setY(e.getY());
				
				lig.set_endX(lig.get_endX() + lig.getX());
				lig.set_endY(lig.get_endY() + lig.getY());
		
			
			}
			else {
				model.get(formeIdx).setX(e.getX());
				model.get(formeIdx).setY(e.getY());
			}
			
			
		} else if (brush_activated)
			brush.fill_brush((int) e.getX(), (int) e.getY(), this.getColor(), gc.getLineWidth());
		

		this.draw();

	}

	public void lache(MouseEvent e) {
		enDeplacement = false;
		draw_object = false;
		formeIdx = -1;
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
			load();
			break;
		case SAVE_FILE:
			save();
			break;
		case CHANGE_COLOR: 
			setColor(getColor()); 
			break;
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
		case DELETE_OBJECT:
			draw();
			break;

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
		File file = chooseFile();
		String toSave = toString();

		if (file != null) {
			try {
				FileWriter openedFile = new FileWriter(file);
				openedFile.write(toSave);
				openedFile.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public File chooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("File");
		File file = fileChooser.showOpenDialog(null);

		return file;
	}

	public String toString() {
		String saved = "";
		for (int i = 0; i < this.model.getSize(); i++) {
			saved += this.model.get(i).toString() + '\n';

		}
		saved += this.brush.toString();

		return saved;
	}

	public void load() {
		reset();
		File file = chooseFile();
		
		if(file!=null)
		try {
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {

				String[] readed = line.split(" ");
				if (readed[0].equalsIgnoreCase("CIRCLE:")) {
					model.add(circleParser(readed));
				} else if (readed[0].equalsIgnoreCase("RECTANGLE:")) {
					model.add(rectParser(readed));
				} else if (readed[0].equalsIgnoreCase("LINE:")) {
					model.add(lineParser(readed));
				} else if (readed[0].equalsIgnoreCase("BRUSH:")) {
					line = br.readLine();
					this.brush = brushParser(line);
				}

			}
			this.draw();

		} catch (FileNotFoundException e) {
			System.out.println(" File not Found ");
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Brush brushParser(String s) throws IOException {
		Brush brush = new Brush();
		String[] readed = s.split(",");
		for (int i = 0; i < readed.length; i++) {
			String[] point = readed[i].split(" ");
			int x = Integer.parseInt(point[0]), y = Integer.parseInt(point[1]);
			double w = Double.parseDouble(point[2]);
			Color c = Color.valueOf(point[3]);
			brush.fill_brush(x, y, c, w); 
		}

		return brush;

	}

	public MDisque circleParser(String[] s) {
		double x, y, radius;
		Color c = Color.valueOf(s[4]);
		x = Double.parseDouble(s[1]);
		y = Double.parseDouble(s[2]);
		radius = Double.parseDouble(s[3]);
		return new MDisque(x, y, radius, c);
	}

	public MRectangle rectParser(String[] s) {
		double x, y, ox, oy;
		Color c = Color.valueOf(s[5]);
		x = Double.parseDouble(s[1]);
		y = Double.parseDouble(s[2]);
		ox = Double.parseDouble(s[3]);
		oy = Double.parseDouble(s[4]);
		return new MRectangle(x, y, ox, oy, c);
	}

	public MLigne lineParser(String[] s) {
		double x, y, ox, oy;
		Color c = Color.valueOf(s[5]);
		x = Double.parseDouble(s[1]);
		y = Double.parseDouble(s[2]);
		ox = Double.parseDouble(s[3]);
		oy = Double.parseDouble(s[4]);
		return new MLigne(x, y, ox, oy, c);
	}

	public void setWidth(Event e, String value) {
		System.out.println(value + " Stringvalue");
		
		
		if(value.equals("Width 1"))
			gc.setLineWidth(3);
		else if(value.equals("Width 2"))
			gc.setLineWidth(6);
		else if(value.equals("Width 3"))
			gc.setLineWidth(9);
		else if(value.equals("Width 4"))
			gc.setLineWidth(12);
		else if(value.equals("Width 5"))
			gc.setLineWidth(15);
		
	}
}
