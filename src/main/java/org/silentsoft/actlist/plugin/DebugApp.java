package org.silentsoft.actlist.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.controlsfx.control.PopOver;
import org.silentsoft.actlist.plugin.ActlistPlugin.Function;
import org.silentsoft.actlist.plugin.ActlistPlugin.SupportedPlatform;
import org.silentsoft.actlist.plugin.messagebox.MessageBox;
import org.silentsoft.core.util.FileUtil;
import org.silentsoft.core.util.JSONUtil;
import org.silentsoft.core.util.ObjectUtil;
import org.silentsoft.core.util.SystemUtil;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	
	ActlistPlugin plugin;
	
	Stage stage;
	
	PopOver popOver;
	
	ObservableList<Node> functions;
	
	boolean isAvailableNewPlugin = false;
	URI newPluginURI;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		String mainClass = null;
		Class<?> pluginClass = null;
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(Paths.get(System.getProperty("user.dir"), "target", "classes", "META-INF", "MANIFEST.MF").toString());
			
			Manifest manifest = new Manifest(inputStream);
			mainClass = manifest.getMainAttributes().getValue(Attributes.Name.MAIN_CLASS).trim();
			if (ObjectUtil.isEmpty(mainClass)) {
				mainClass = "Plugin";
			}
		} catch (Exception | Error e) {
			e.printStackTrace();
		} finally {
			try {
				pluginClass = getClass().getClassLoader().loadClass(mainClass);
			} catch (ClassNotFoundException e) {
				StringBuffer message = new StringBuffer();
				message.append(String.join("", "[ERROR] '", mainClass, "' class is not exists. Please check 'mainClass' property in pom.xml", "\r\n"));
				message.append(String.join("", ">>", "\r\n"));
				message.append(String.join("", "    <properties>", "\r\n"));
				message.append(String.join("", "        <mainClass>your.pkg.Plugin</mainClass>", "\r\n"));
				message.append(String.join("", "    </properties>", "\r\n"));
				message.append(String.join("", "<<", "\r\n"));
				System.err.println(message.toString());
			}
			
			if (inputStream != null) {
				inputStream.close();
			}
		}
		
		if (ActlistPlugin.class.isAssignableFrom(pluginClass)) {
			this.plugin = ActlistPlugin.class.cast(pluginClass.newInstance());
			
			popOver = new PopOver(new VBox());
			((VBox) popOver.getContentNode()).setPadding(new Insets(3, 3, 3, 3));
			popOver.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
			
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
							JFXToggleButton toggle = (JFXToggleButton) stage.getScene().lookup("#togActivator");
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
			root.getChildren().add(createHead());
			root.getChildren().add(createToggleBox());
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
			
			functions = FXCollections.observableArrayList();
			for (Function function : plugin.getFunctionMap().values()) {
				addFunction(function);
			}
			
			if (plugin.isOneTimePlugin() == false) {
				((JFXToggleButton) stage.getScene().lookup("#togActivator")).getOnAction().handle(null);
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
	
	private void addFunction(Function function) {
		functions.add(createFunctionBox(new Label("", function.graphic), mouseEvent -> {
			try {
				if (function.action != null) {
					function.action.run();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				popOver.hide();
			}
		}));
	}
	
	private HBox createFunctionBox(Node node, javafx.event.EventHandler<? super MouseEvent> action) {
		HBox hBox = new HBox(node);
		hBox.setAlignment(Pos.CENTER);
		hBox.setPadding(new Insets(3, 3, 3, 3));
		hBox.setStyle("-fx-background-color: white;");
		hBox.setOnMouseEntered(mouseEvent -> {
			hBox.setStyle("-fx-background-color: lightgray;");
		});
		hBox.setOnMouseExited(mouseEvent -> {
			hBox.setStyle("-fx-background-color: white;");
		});
		hBox.addEventFilter(MouseEvent.MOUSE_CLICKED, action);
		
		return hBox;
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
	
	private HBox createHead() {
		Label lblPluginName = new Label();
		lblPluginName.setText((plugin.getPluginName() == null || plugin.getPluginName().trim().isEmpty()) ? "(empty name)" : plugin.getPluginName());
		lblPluginName.setPrefHeight(16.0);
		HBox.setHgrow(lblPluginName, Priority.ALWAYS);
		lblPluginName.setFont(Font.font("Arial", 14.0));
		
		HBox head = new HBox(lblPluginName);
		head.setAlignment(Pos.CENTER_LEFT);
		head.setPrefHeight(45.0);
		AnchorPane.setLeftAnchor(head, 35.0);
		AnchorPane.setRightAnchor(head, 102.0);
		AnchorPane.setTopAnchor(head, 0.0);
		head.setOpaqueInsets(Insets.EMPTY);
		head.setPadding(new Insets(5.0, 0.0, 0.0, 0.0));
		head.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				if (popOver != null) {
					((VBox) popOver.getContentNode()).getChildren().clear();
					
					/*
					((VBox) popOver.getContentNode()).getChildren().add(createAboutFunction());
					*/
					
					JFXToggleButton toggle = (JFXToggleButton) stage.getScene().lookup("#togActivator");
					if (toggle.selectedProperty().get()) {
						/*
						if (plugin.getFunctionMap().size() > 0) {
							((VBox) popOver.getContentNode()).getChildren().add(new Separator(Orientation.HORIZONTAL));
						}
						*/
						
						((VBox) popOver.getContentNode()).getChildren().addAll(functions);
					}
					
					popOver.show(stage.getScene().lookup("#contentLoadingBox"), e.getScreenX(), e.getScreenY());
				}
			}
		});
		
		return head;
	}
	
	Stage aboutStage;
	private HBox createAboutFunction() {
		return createFunctionBox(new Label("About"), mouseEvent -> {
			stage.getScene().lookup("#updateAlarmLabel").getOnMouseClicked().handle(null);
		});
	}
	private HBox createToggleBox() {
		Label warningLabel = new Label();
		warningLabel.setId("warningLabel");
		warningLabel.setMaxHeight(6.0);
		warningLabel.setMaxWidth(6.0);
		warningLabel.setMinHeight(6.0);
		warningLabel.setMinWidth(6.0);
		warningLabel.setOnMouseClicked(mouseEvent -> {
			warningLabel.setVisible(false);
			
			try {
				String warningText = plugin.getWarningText();
				if (ObjectUtil.isNotEmpty(warningText)) {
					MessageBox.showWarning(stage, warningText);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		warningLabel.setStyle("-fx-background-color: orange; -fx-background-radius: 5em;");
		warningLabel.setVisible(false);
		warningLabel.setCursor(Cursor.HAND);
		
		Label updateAlarmLabel = new Label();
		updateAlarmLabel.setId("updateAlarmLabel");
		updateAlarmLabel.setMaxHeight(6.0);
		updateAlarmLabel.setMaxWidth(6.0);
		updateAlarmLabel.setMinHeight(6.0);
		updateAlarmLabel.setMinWidth(6.0);
		updateAlarmLabel.setOnMouseClicked(mouseEvent -> {
			updateAlarmLabel.setVisible(false);
			
			/**
			 * this aboutStage must be closed when if already opened.
			 * because the newPluginURI variable will be set by another thread.
			 */
			if (aboutStage != null) {
				aboutStage.close();
				aboutStage = null;
			}
			
			aboutStage = new Stage();
			aboutStage.initOwner(this.stage);
			aboutStage.initStyle(StageStyle.UTILITY);
			{
				BorderPane scene = new BorderPane();
				//scene.setCenter(new PluginAbout(this.plugin, this.isAvailableNewPlugin, this.newPluginURI).getViewer());
				
				aboutStage.setScene(new Scene(scene));
			}
			aboutStage.show();
		});
		updateAlarmLabel.setStyle("-fx-background-color: red; -fx-background-radius: 5em;");
		updateAlarmLabel.setVisible(false);
		updateAlarmLabel.setCursor(Cursor.HAND);
		
		HBox innerBox = new HBox(warningLabel, updateAlarmLabel);
		innerBox.setAlignment(Pos.CENTER);
		innerBox.setSpacing(8.0);
		HBox.setMargin(innerBox, Insets.EMPTY);
		
		JFXToggleButton togActivator = new JFXToggleButton();
		togActivator.setId("togActivator");
		togActivator.setText(" ");
		togActivator.setSelected(!plugin.isOneTimePlugin());
		togActivator.setToggleColor(Paint.valueOf("#fafafa"));
		togActivator.setToggleLineColor(Paint.valueOf("#59bf53"));
		togActivator.setUnToggleLineColor(Paint.valueOf("#e0e0e0"));
		togActivator.setOnAction(actionEvent -> {
			Platform.runLater(() -> {
				try {
					VBox contentBox = (VBox) stage.getScene().lookup("#contentBox");
					VBox contentLoadingBox = (VBox) stage.getScene().lookup("#contentLoadingBox");
					
					if (togActivator.selectedProperty().get()) {
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
						
						if (popOver != null) {
							popOver.hide();
						}
						
						plugin.pluginDeactivated();
					}
					
					stage.getScene().getWindow().sizeToScene();
				} catch (Exception e) {
					
				}
			});
		});
		
		HBox toggleBox = new HBox(innerBox, togActivator);
		toggleBox.setAlignment(Pos.CENTER_LEFT);
		toggleBox.setLayoutX(361.0);
		toggleBox.setLayoutY(-2.0);
		toggleBox.setSpacing(8.0);
		AnchorPane.setRightAnchor(toggleBox, 0.0);
		
		return toggleBox;
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
