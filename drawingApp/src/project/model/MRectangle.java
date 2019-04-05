package project.model;

import javafx.scene.canvas.GraphicsContext;

public class MRectangle extends Forme {
	
	private double larg, haut;
	
	public MRectangle(double x, double y, double larg, double haut) {
		super(x,y);
		this.larg=larg;
		this.haut=haut;
	}
	
	public double getLargeur() {return larg;}
	public double getHauteur() {return haut;}
	public void setLargeur(double larg) {this.larg=larg;}
	public void setHauetru(double haut) {this.haut=haut;}
	
	public boolean estDedans(double x, double y) {
		return (x-this.x)<this.larg && (x-this.x)>0 && (y-this.y)<this.haut && (y-this.y)>0;
	}
	
	public void draw(GraphicsContext gc) {
		gc.fillRect(this.x,this.y,this.larg,this.haut);
	}

}
