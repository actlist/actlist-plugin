package org.silentsoft.actlist.plugin;

import java.io.File;
import java.nio.file.Paths;

import org.silentsoft.actlist.plugin.ActlistPlugin.SupportedPlatform;
import org.silentsoft.actlist.plugin.messagebox.MessageBox;
import org.silentsoft.core.util.FileUtil;
import org.silentsoft.core.util.JSONUtil;
import org.silentsoft.core.util.SystemUtil;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSpinner;
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
 * <li>pluginActivated(), pluginDeactivated()</li>
 * <li>showLoadingBar(), hideLoadingBar()</li>
 * </p>
 * 
 * <tt>excluded features</tt></br>
 * <li>except all but included features</li>
 * </p>
 * 
 * @author silentsoft
 */
public final class DebugApp extends Application {

	static boolean isDebugMode = false;
	
	public static void debug() {
		isDebugMode = true;
		
		launch("");
	}
	
	Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		Class<?> clazz = Class.forName("Plugin");
		if (ActlistPlugin.class.isAssignableFrom(clazz)) {
			ActlistPlugin plugin = ActlistPlugin.class.cast(clazz.newInstance());
			
			SupportedPlatform currentPlatform = null;
			{
				if (SystemUtil.isWindows()) {
					currentPlatform = SupportedPlatform.WINDOWS;
				} else if (SystemUtil.isMac()) {
					currentPlatform = SupportedPlatform.MACOSX;
				} /* else if (SystemUtil.isLinux()) {
					currentPlatform = SupportedPlatform.LINUX;
				} else {
					currentPlatform = SupportedPlatform.UNKNOWN;
				} */
			}
			plugin.currentPlatformObject().set(currentPlatform);
			
			plugin.classLoaderObject().set(getClass().getClassLoader());
			
			plugin.setPluginConfig(new PluginConfig("debug"));
			File configFile = Paths.get(System.getProperty("user.dir"), "plugins", "config", "debug.config").toFile();
			if (configFile.exists()) {
				String configContent = FileUtil.readFile(configFile);
				PluginConfig pluginConfig = JSONUtil.JSONToObject(configContent, PluginConfig.class);
				if (pluginConfig != null) {
					plugin.setPluginConfig(pluginConfig);
				}
			}
			
			{
				ChangeListener<Object> changeListener = new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
						if (oldValue == newValue) {
							return;
						}
						
						Platform.runLater(() -> {
							MessageBox.showError("Not supported feature on debug mode.");
						});
					}
				};
				plugin.exceptionObject().addListener(changeListener);
				plugin.showTrayNotificationObject().addListener(changeListener);
				plugin.dismissTrayNotificationObject().addListener(changeListener);
				plugin.shouldDismissTrayNotifications().addListener(changeListener);
				plugin.shouldBrowseActlistArchives().addListener(changeListener);
				plugin.shouldRequestShowActlist().addListener(changeListener);
			}
			{
				ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if (oldValue == newValue) {
							return;
						}
						
						displayLoadingBar(plugin, newValue);
					}
				};
				plugin.shouldShowLoadingBar().addListener(changeListener);
			}
			{
				ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if (newValue) {
							JFXToggleButton toggle = (JFXToggleButton) stage.getScene().lookup("#toggle");
							boolean isActivated = toggle.selectedProperty().get();
							if (isActivated) {
								toggle.setSelected(false);
								toggle.getOnAction().handle(null);
							}
							
							plugin.shouldRequestDeactivate().set(false);
						}
					}
				};
				plugin.shouldRequestDeactivate().addListener(changeListener);
			}
			
			AnchorPane root = new AnchorPane();
			root.setPrefWidth(435.0);
			root.setStyle("-fx-background-color: #ffffff;");
			root.getChildren().add(createHamburger());
			root.getChildren().add(createHead(plugin));
			root.getChildren().add(createToggle(plugin));
			root.getChildren().add(createSeparator());
			root.getChildren().add(createContentBox());
			root.getChildren().add(createContentLoadingBox());
			
			stage.setScene(new Scene(root));
			
			stage.setTitle("Actlist Debug App");
			stage.show();
			
			if (plugin.existsGraphic()) {
				plugin.getGraphic();
			}
			
			plugin.initialize();
			
			if (plugin.isOneTimePlugin() == false) {
				((JFXToggleButton) stage.getScene().lookup("#toggle")).getOnAction().handle(null);
			}
		} else {
			throw new Exception("The Plugin class must be extends ActlistPlugin !");
		}
	}
	
	private void displayLoadingBar(ActlistPlugin plugin, boolean shouldShowLoadingBar) {
		if (plugin.existsGraphic()) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					VBox contentLoadingBox = (VBox) stage.getScene().lookup("#contentLoadingBox");
					
					contentLoadingBox.getChildren().clear();
					
					if (shouldShowLoadingBar) {
						contentLoadingBox.getChildren().add(new JFXSpinner());
					}
					
					contentLoadingBox.setVisible(shouldShowLoadingBar);
				}
			};
			
			if (Platform.isFxApplicationThread()) {
				runnable.run();
			} else {
				Platform.runLater(() -> {
					runnable.run();
				});
			}
		}
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
		lblPluginName.setText((plugin.getPluginName() == null || plugin.getPluginName().trim().isEmpty()) ? "(empty name)" : plugin.getPluginName());
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
		toggle.setText(" ");
		toggle.setSelected(!plugin.isOneTimePlugin());
		toggle.setToggleColor(Paint.valueOf("#fafafa"));
		toggle.setToggleLineColor(Paint.valueOf("#59bf53"));
		toggle.setUnToggleLineColor(Paint.valueOf("#e0e0e0"));
		AnchorPane.setRightAnchor(toggle, -0.203125);
		
		toggle.setOnAction(actionEvent -> {
			Platform.runLater(() -> {
				try {
					VBox contentBox = (VBox) stage.getScene().lookup("#contentBox");
					VBox contentLoadingBox = (VBox) stage.getScene().lookup("#contentLoadingBox");
					
					if (toggle.selectedProperty().get()) {
						if (plugin.existsGraphic()) {
							Node pluginContent = plugin.getGraphic();
							if (pluginContent != null) {
								contentBox.getChildren().add(new BorderPane(pluginContent));
								Separator contentLine = new Separator();
								contentLine.setPrefWidth(215.0);
								contentLine.setPadding(new Insets(5.0, 0.0, 0.0, 0.0));
								contentBox.getChildren().add(contentLine);
							}
						}
						
						plugin.pluginActivated();
					} else {
						contentBox.getChildren().clear();
						contentLoadingBox.getChildren().clear();
						
						plugin.pluginDeactivated();
					}
					
					stage.getScene().getWindow().sizeToScene();
				} catch (Exception e) {
					
				}
			});
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
	
	private VBox createContentBox() {
		VBox contentBox = new VBox();
		contentBox.setId("contentBox");
		contentBox.setLayoutX(35.0);
		contentBox.setLayoutY(51.0);
		contentBox.setPrefWidth(380.0);
		AnchorPane.setRightAnchor(contentBox, 20.0);
		AnchorPane.setLeftAnchor(contentBox, 35.0);
		
		return contentBox;
	}
	
	private VBox createContentLoadingBox() {
		VBox contentLoadingBox = new VBox();
		contentLoadingBox.setId("contentLoadingBox");
		contentLoadingBox.setAlignment(Pos.CENTER);
		contentLoadingBox.setLayoutX(35.0);
		contentLoadingBox.setLayoutY(51.0);
		contentLoadingBox.setPrefWidth(380.0);
		contentLoadingBox.setStyle("-fx-background-color: white;");
		contentLoadingBox.setVisible(false);
		AnchorPane.setBottomAnchor(contentLoadingBox, 3.0);
		AnchorPane.setLeftAnchor(contentLoadingBox, 35.0);
		AnchorPane.setRightAnchor(contentLoadingBox, 0.0);
		AnchorPane.setTopAnchor(contentLoadingBox, 51.0);
		
		return contentLoadingBox;
	}
}
