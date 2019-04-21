package project.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MRectangle extends Shape {

	private double larg, haut;

	public MRectangle(double x, double y, double larg, double haut) {
		super(x, y);
		this.larg = larg;
		this.haut = haut;
	}

	public MRectangle(double x, double y, double ox, double oy, Color c) {
		super(x, y);
		this.larg = ox;
		this.haut = oy;
		this.color = c;
	}

	public double getLargeur() {
		return larg;
	}

	public double getHauteur() {
		return haut;
	}

	public void setLargeur(double larg) {
		this.larg = larg;
	}

	public void setHauetru(double haut) {
		this.haut = haut;
	}

	public boolean estDedans(double x, double y) {
		return (x - this.x) < this.larg && (x - this.x) > 0 && (y - this.y) < this.haut && (y - this.y) > 0;
	}

	public void draw(GraphicsContext gc) {
		if (this.isFilled()) {
			gc.setFill(this.color);
			gc.fillRect(this.x, this.y, this.larg, this.haut);
		} else {
			// gc.setFill(this.color);
			gc.strokeRect(this.x, this.y, this.larg, this.haut);
		}
	}

	@Override
	public String toString() {
		String data = "RECTANGLE: ";
		data += this.x + " " + this.y + " " + this.larg + " " + this.haut + " " + this.color.toString();
		return data;
	}

}
