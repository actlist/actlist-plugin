package org.silentsoft.actlist.plugin;

import org.silentsoft.actlist.plugin.messagebox.MessageBox;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToggleButton;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * <em><tt>WARNING : This Debug application's source code is independent with real Actlist application's source code. So this application might not be same with real Actlist application.</tt></em></p>
 * 
 * This class is designed for debugging the Actlist plugin.</p>
 * 
 * <tt>included features</tt></br>
 * <li>calls pluginActivated(), pluginDeactivated() via toggle button.</li>
 * </p>
 * <tt>excluded features</tt></br>
 * <li>all but except pluginActivated() and pluginDeactivated().</li>
 * 
 * @author silentsoft
 */
public class DebugApp extends Application {

	public static void debug() {
		launch("");
	}

	private Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		Class<?> clazz = Class.forName("Plugin");
		if (ActlistPlugin.class.isAssignableFrom(clazz)) {
			ActlistPlugin plugin = ActlistPlugin.class.cast(clazz.newInstance());
			
			ChangeListener<Object> changeListener = new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
					if (oldValue == newValue) {
						return;
					}
					
					MessageBox.showError("Not supported feature on debug mode.");
				}
			};
			plugin.shouldShowLoadingBar().addListener(changeListener);
			plugin.exceptionObject().addListener(changeListener);
			plugin.showTrayNotificationObject().addListener(changeListener);
			plugin.dismissTrayNotificationObject().addListener(changeListener);
			plugin.shouldDismissTrayNotifications().addListener(changeListener);
			
			AnchorPane root = new AnchorPane();
			root.setPrefWidth(435.0);
			root.setStyle("-fx-background-color: #ffffff;");
			root.getChildren().add(createHamburger());
			root.getChildren().add(createHead(plugin));
			root.getChildren().add(createToggle(plugin));
			root.getChildren().add(createSeparator());
			root.getChildren().add(createBody(plugin));
			
			stage.setScene(new Scene(root));
		}
		
		stage.setTitle("Actlist Debug App");
		stage.show();
		
		Platform.runLater(() -> {
			((JFXToggleButton) stage.getScene().lookup("#toggle")).getOnAction().handle(null);
		});
	}
	
	private JFXHamburger createHamburger() {
		JFXHamburger hamburger = new JFXHamburger();
		hamburger.setLayoutX(13.0);
		hamburger.setLayoutY(19.0);
		hamburger.setOpacity(0.2);
		hamburger.setPrefHeight(14.0);
		hamburger.setPrefWidth(11.0);
		hamburger.setCursor(Cursor.MOVE);
		
		return hamburger;
	}
	
	private HBox createHead(ActlistPlugin plugin) {
		Label lblPluginName = new Label();
		lblPluginName.setText(plugin.getPluginName());
		lblPluginName.setPrefHeight(16.0);
		lblPluginName.setPrefWidth(302.0);
		HBox.setHgrow(lblPluginName, Priority.SOMETIMES);
		lblPluginName.setFont(Font.font("Arial", 14.0));
		
		HBox head = new HBox(lblPluginName);
		head.setAlignment(Pos.CENTER_LEFT);
		head.setPrefHeight(45.0);
		AnchorPane.setLeftAnchor(head, 35.0);
		AnchorPane.setRightAnchor(head, 60.0);
		AnchorPane.setTopAnchor(head, 0.0);
		head.setOpaqueInsets(Insets.EMPTY);
		head.setPadding(new Insets(5.0, 0.0, 0.0, 0.0));
		
		return head;
	}
	
	private JFXToggleButton createToggle(ActlistPlugin plugin) {
		JFXToggleButton toggle = new JFXToggleButton();
		toggle.setId("toggle");
		toggle.setLayoutX(199.0);
		toggle.setLayoutY(-2.0);
		toggle.setSelected(true);
		toggle.setText(" ");
		toggle.setToggleColor(Paint.valueOf("#fafafa"));
		toggle.setToggleLineColor(Paint.valueOf("#59bf53"));
		toggle.setUnToggleLineColor(Paint.valueOf("#e0e0e0"));
		AnchorPane.setRightAnchor(toggle, -0.203125);
		
		toggle.setOnAction(actionEvent -> {
			try {
				VBox body = (VBox) stage.getScene().lookup("#body");
				
				if (toggle.selectedProperty().get()) {
					if (plugin.existsGraphic()) {
						Node pluginContent = plugin.getGraphic();
						if (pluginContent != null) {
							body.getChildren().add(new BorderPane(pluginContent));
							Separator contentLine = new Separator();
							contentLine.setPrefWidth(215.0);
							contentLine.setPadding(new Insets(5.0, 0.0, 0.0, 0.0));
							body.getChildren().add(contentLine);
						}
					}
					
					plugin.pluginActivated();
				} else {
					body.getChildren().clear();
					
					plugin.pluginDeactivated();
				}
				
				stage.getScene().getWindow().sizeToScene();
			} catch (Exception e) {
				
			}
		});
		
		return toggle;
	}
	
	private Separator createSeparator() {
		Separator separator = new Separator();
		separator.setLayoutX(35.0);
		separator.setLayoutY(50.0);
		separator.setPrefWidth(215.0);
		AnchorPane.setLeftAnchor(separator, 35.0);
		AnchorPane.setRightAnchor(separator, 20.0);
		
		return separator;
	}
	
	private VBox createBody(ActlistPlugin plugin) {
		VBox body = new VBox();
		body.setId("body");
		body.setLayoutX(35.0);
		body.setLayoutY(51.0);
		body.setPrefWidth(380.0);
		AnchorPane.setLeftAnchor(body, 35.0);
		AnchorPane.setRightAnchor(body, 20.0);
		
		return body;
	}
}