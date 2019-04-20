package project.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

abstract public class Shape {

	protected double x,y;
	
	protected Color color;
	
	public Shape(double x, double y){
		this.x=x;
		this.y=y;
		color = Color.DARKRED;
	}
	
	public double getX() {return x;}
	
	public double getY() {return y;}
	
	public void setX(double x) {this.x=x;}
	
	public void setY(double y) {this.y=y;}
	
	public void setColor(Color c) {color = c;}
	
	public Color getColor() { return color;}
	
	public abstract boolean estDedans(double x, double y);
	
	public abstract void draw(GraphicsContext gc);
}
