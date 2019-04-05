package project.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.controller.Control;

public class View extends Application {

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Drawing app");

		Canvas drawArea = new Canvas(600, 600);

		GraphicsContext gc = drawArea.getGraphicsContext2D();
		Control control = new Control(gc, drawArea.getWidth(), drawArea.getHeight());
		control.draw();

		BorderPane pane = new BorderPane();
		pane.setCenter(drawArea);
		addToolbar(pane);

		Scene scene = new Scene(pane, 600, 600);

		/*
		 * drawArea.setOnMousePressed(e -> control.attrape(e));
		 * 
		 * drawArea.setOnMouseReleased(e -> control.lache(e));
		 * 
		 * drawArea.setOnMouseDragged(e -> control.deplace(e));
		 */
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void addToolbar(BorderPane pane) {
	
	 ToolBar toolBar = new ToolBar(
		     new Button("Brush"),
		     new Button("Shape"),
		     new Button("Color Selector"),
		     new Separator(),
		     new Button("ShapeColor selector"),
		     new Button("Move"),
		     new Button("Edit"),
		     new Separator(),
		     new Button("Save")
		 );
	 pane.setTop(toolBar);
	 
	 
	}
	

	public static void main(String[] args) {
		Application.launch(args);
	}
}
