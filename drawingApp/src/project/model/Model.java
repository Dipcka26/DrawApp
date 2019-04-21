package project.model;

import java.util.Vector;

public class Model {

	private Vector<Shape> tab;

	public Model() {
		tab = new Vector<Shape>();
	}

	public Shape get(int i) {
		return tab.get(i);
	}

	public int getSize() {
		return tab.size();
	}

	public void add(Shape f) {
		tab.add(f);
	}

}
