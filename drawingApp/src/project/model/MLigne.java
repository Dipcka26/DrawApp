package project.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import project.controller.Control;

public class MLigne extends Shape {

	double x_end;
	double y_end;

	public MLigne(double x, double y, Color c) {
		super(x, y);
		x_end = 0;
		y_end = 0;
		this.color = c;
	}

	public MLigne(double x, double y, double ox, double oy, Color c) {
		super(x, y);
		x_end = ox;
		y_end = oy;
		this.color = c;
	}

	public void set_endX(double x_set) {
		x_end = x_set;
	}

	public void set_endY(double y_set) {
		y_end = y_set;
	}
	public double get_endX() {
		return x_end;
	}
	public double get_endY() {
		return y_end;
	}
	// https://stackoverflow.com/questions/17692922/check-is-a-point-x-y-is-between-two-points-drawn-on-a-straight-line
	public boolean estDedans(double x_between, double y_between) {

		int distanz_AC = (int) Math.sqrt(Math.pow(x_between - this.x, 2) + Math.pow(y_between - this.y, 2));
		int distanz_BC = (int) Math.sqrt(Math.pow(x_between - x_end, 2) + Math.pow(y_between - y_end, 2));
		int distanz_AB = (int) Math.sqrt(Math.pow(x - x_end, 2) + Math.pow(y - y_end, 2));

		return (distanz_AC + distanz_BC) == distanz_AB;
	}

	public void draw(GraphicsContext gc) {
		
		double old_width = gc.getLineWidth();
		gc.setLineWidth(Control.BRUSH_WIDTH);
		gc.setStroke(this.color);
		gc.strokeLine(x, y, x_end, y_end);
		gc.setLineWidth(old_width);
	}

	@Override
	public String toString() {
		return "LINE: " + this.x + " " + this.y + " " + this.x_end + " " + this.y_end + " " + this.color.toString();
	}

}
