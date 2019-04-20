package project.model;

import javafx.scene.canvas.GraphicsContext;

public class MDisque extends Shape {

	private double rayon;
	
	public MDisque(double x, double y, double rayon) {
		super(x,y);
		this.rayon=rayon;
	}
	
	public double getRayon() {return this.rayon;}
	
	public void setRayon(double rayon) {this.rayon=rayon;}
	
	public boolean estDedans(double x, double y) {
		double xc=this.x+this.rayon;
		double yc=this.y+this.rayon;
		return (x-xc)*(x-xc) + (y-yc)*(y-yc) < this.rayon*this.rayon;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(this.color);
		gc.fillOval(this.x, this.y, 2*this.rayon, 2*this.rayon);
	}
	
}
