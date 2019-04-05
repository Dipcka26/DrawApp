package project.model;

import javafx.scene.canvas.GraphicsContext;

abstract public class Shape {

	protected double x,y;
	
	public Shape(double x, double y){
		this.x=x;
		this.y=y;
	}
	
	public double getX() {return x;}
	
	public double getY() {return y;}
	
	public void setX(double x) {this.x=x;}
	
	public void setY(double y) {this.y=y;}
	
	public abstract boolean estDedans(double x, double y);
	
	public abstract void draw(GraphicsContext gc);
}
