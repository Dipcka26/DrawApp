package project.view;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.controller.Control;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;

public class View extends Application {

	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Drawing app");

		Canvas drawArea = new Canvas(900, 1050);

		GraphicsContext gc = drawArea.getGraphicsContext2D();
		Control control = new Control(gc, drawArea.getWidth(), drawArea.getHeight());

		BorderPane pane = new BorderPane();
		pane.setCenter(drawArea);
		addToolbar(pane, control);

		control.draw();
		
		Scene scene = new Scene(pane,900, 1050);
		
		 
		 drawArea.setOnMousePressed(e -> control.attrape(e));
		  
		 drawArea.setOnMouseReleased(e -> control.lache(e));
		  
		 drawArea.setOnMouseDragged(e -> control.deplace(e));
		 
		 //drawArea.setOnMousePressed(e -> control.drawObject(e));
		 
		 //Referenz https://stackoverflow.com/questions/20477187/use-existing-method-as-javafx-event-handler
		 //	drawArea.setOnMousePressed(control::attrape);
		 //	drawArea.setOnMouseReleased(control::lache);
		 //	drawArea.setOnMouseDragged(control::deplace);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	public void addToolbar(BorderPane pane, Control control) {
	
	 ToolBar toolBar = new ToolBar();
	 
	 Button brush = new Button("Brush");
	 Button save_file = new Button ("Save-File");
	 Button load_file = new Button ("Load-File");
	 Button fill_object = new Button ("Fill");
	 Button move_object = new Button ("Move");
	 Button reset_all = new Button("Reset");
	 Button resize_object = new Button("Resize");
	 Button delete_object = new Button("Delete");
	 
	 brush.setOnMouseClicked(e -> control.changeMenu(e, Control.BRUSH));
	 save_file.setOnMouseClicked(e -> control.changeMenu(e, Control.SAVE_FILE));
	 load_file.setOnMouseClicked(e -> control.changeMenu(e, Control.LOAD_FILE));
	 fill_object.setOnMouseClicked(e -> control.changeMenu(e, Control.FILL_OBJECT));
	 move_object.setOnMouseClicked(e -> control.changeMenu(e, Control.MOVE_OBJECT));
	 reset_all.setOnMouseClicked(e->control.changeMenu(e,Control.RESET));
	 resize_object.setOnMouseClicked(e->control.changeMenu(e, Control.RESIZE_OBJECT));
	 delete_object.setOnMouseClicked(e->control.changeMenu(e,Control.DELETE_OBJECT));
	 
	 
	 //FOr the width of the brush! 
	 ChoiceBox choiceBox = new ChoiceBox();

     choiceBox.getItems().add("Width 1");
     choiceBox.getItems().add("Width 2");
     choiceBox.getItems().add("Width 3");
     choiceBox.getItems().add("Width 4");
     choiceBox.getItems().add("Width 5");
     choiceBox.setValue("Width 1");

     choiceBox.setOnAction(e->control.setWidth(e, (String) choiceBox.getValue()));
	 
	 
	 //Add Menu Fill Objects
	 Menu menu = new Menu("Choose Form");
	
	 MenuItem menuItem1 = new MenuItem("Ligne");
	 MenuItem menuItem2 = new MenuItem("Rectangle");
	 MenuItem menuItem3 = new MenuItem("Cercle");

	 menuItem1.setOnAction(e -> control.changeMenu(e, Control.LIGNE));
	 menuItem2.setOnAction(e -> control.changeMenu(e, Control.RECTANGLE));
	 menuItem3.setOnAction(e -> control.changeMenu(e, Control.CERCLE));
	 
	 menu.getItems().add(menuItem1);
	 menu.getItems().add(menuItem2);
	 menu.getItems().add(menuItem3);

	 MenuBar menuBar = new MenuBar();
	 menuBar.getMenus().add(menu);
	
	
	 //Add ColorPicker 
	 ColorPicker colorPicker = new ColorPicker();
	 control.setColorPicker(colorPicker);
	 colorPicker.getStyleClass().add("button");
	 colorPicker.setOnAction(e -> control.changeMenu(e, Control.CHANGE_COLOR));
	// control.setColor(Color.RED);
	 
	 
	 //Add all components
	 toolBar.getItems().add(save_file);
	 toolBar.getItems().add(load_file);
	 
	 toolBar.getItems().add(brush);
	 toolBar.getItems().add(choiceBox);
	 
	 toolBar.getItems().add(fill_object);
	 toolBar.getItems().add(move_object);
	 toolBar.getItems().add(resize_object);
	 toolBar.getItems().add(delete_object);
	 toolBar.getItems().add(menuBar);
	 toolBar.getItems().add(colorPicker); 
	 toolBar.getItems().add(reset_all);
	 
	 
	 
	 pane.setTop(toolBar);
	 
	}
	

	public static void main(String[] args) {
		Application.launch(args);
	}
}
