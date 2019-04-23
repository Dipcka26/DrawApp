package project.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import project.controller.Control;

public class MDisque extends Shape {

	private double rayon;

	public MDisque(double x, double y, double rayon) {
		super(x, y);
		this.rayon = rayon;
	}

	public MDisque(double x, double y, double radius, Color c) {
		super(x, y);
		this.rayon = radius;
		this.color = c;
	}

	public double getRayon() {
		return this.rayon;
	}

	public void setRayon(double rayon) {
		this.rayon = rayon;
	}

	public boolean estDedans(double x, double y) {
		double xc = this.x + this.rayon;
		double yc = this.y + this.rayon;
		return (x - xc) * (x - xc) + (y - yc) * (y - yc) < this.rayon * this.rayon;
	}

	public void draw(GraphicsContext gc) {
		if (this.isFilled()) {
			gc.setFill(this.color);
			gc.fillOval(this.x, this.y, 2 * this.rayon, 2 * this.rayon);
		} else {
			double width = gc.getLineWidth();
			gc.setLineWidth(Control.BRUSH_WIDTH);
		    gc.setStroke(this.color);
			gc.strokeOval(this.x, this.y, 2 * this.rayon, 2 * this.rayon);
			gc.setLineWidth(width);
		}
	}

	@Override
	public String toString() {
		return "CIRCLE: " + this.x + " " + this.y + " " + this.rayon + " " + this.color.toString();
	}

}
