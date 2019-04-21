package project.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
			// gc.setFill(this.color);
			gc.strokeOval(this.x, this.y, 2 * this.rayon, 2 * this.rayon);
		}
	}

	@Override
	public String toString() {
		return "CIRCLE: " + this.x + " " + this.y + " " + this.rayon + " " + this.color.toString();
	}

}
