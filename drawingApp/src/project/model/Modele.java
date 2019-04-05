package project.model;

import java.util.Vector;

public class Modele {
	
	private Vector<Forme> tab;
	
	public Modele(){
		tab = new Vector<Forme>();
	}
	
	public Forme get(int i) {return tab.get(i);}
	
	public int getSize() {return tab.size();}
	
	public void add(Forme f) {tab.add(f);}

}
