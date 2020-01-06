package org.silentsoft.actlist.plugin;

import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.silentsoft.actlist.plugin.tray.TrayNotification;

import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

/**
 * Please generate executable main class called <tt>your.pkg.Plugin.java</tt> that you assigned from <tt>mainClass</tt> property on <tt>pom.xml</tt>
 * and inherit this <tt>ActlistPlugin</tt> class in your <tt>Plugin</tt> class.</br>
 * <em><tt>NOTE : You should not write any code other than calling the <code>debug();</code> method for debugging in the main method.</tt></em></p>
 * 
 * To make a plugin that contains graphic things, you can write the 'Plugin.fxml' file where in the same location of your <tt>Plguin</tt> class.
 * Also you can set the plugin's icon image that display where in about section (Right click -> About) through 'Plugin.png'</br>
 * <em><tt>NOTE : the recommended size of image is 48x48.</tt></em></p>
 * 
 * Finally, run maven command via CLI or GUI with <tt>clean package</tt> goals.</p>
 * 
 * All done. Happy enjoy it !</p>
 * 
 * @author silentsoft
 * @see <a href="https://actlist.silentsoft.org/docs/quick-start/">Plugin Development Guide</a>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class ActlistPlugin {
	
	/**
	 * Actlist engine will reflects this variable to determine what the version of the ActlistPlugin is.
	 */
	@SuppressWarnings("unused")
	private String version = "1.7.0.alpha";
	
	public enum SupportedPlatform {
		WINDOWS, MACOSX
	}
	
	public class Function {
		Node graphic;
		Runnable action;
		Function(Node graphic, Runnable action) {
			this.graphic = graphic;
			this.action = action;
		}
		public Node getGraphic() {
			return this.graphic;
		}
		protected void setGraphic(Node graphic) {
			this.graphic = graphic;
		}
	}
	
	public class TextFunction extends Function {
		public TextFunction(String text, Runnable action) {
			super(new Label(text), action);
		}
	}
	
	public class GraphicFunction extends Function {
		public GraphicFunction(Node graphic, Runnable action) {
			super(graphic, action);
		}
	}
	
	public class ToggleFunction extends GraphicFunction {
		public ToggleFunction(String text, boolean selected, ChangeListener<? super Boolean> listener) {
			super(new Supplier<Node>() {
				@Override
				public Node get() {
					Label label = new Label(text);
					label.setAlignment(Pos.CENTER_RIGHT);
					label.setMaxWidth(Double.MAX_VALUE);
					
					JFXToggleButton toggleButton = new JFXToggleButton();
					toggleButton.setText("");
					toggleButton.setSelected(selected);
					toggleButton.selectedProperty().addListener(listener);
					toggleButton.setContentDisplay(ContentDisplay.RIGHT);
					toggleButton.setMinHeight(23.0);
					toggleButton.setPrefHeight(23.0);
					toggleButton.setMaxHeight(23.0);
					toggleButton.setToggleColor(Paint.valueOf("#fafafa"));
					toggleButton.setToggleLineColor(Paint.valueOf("#59bf53"));
					toggleButton.setUnToggleLineColor(Paint.valueOf("#e0e0e0"));
					toggleButton.setDisableVisualFocus(true);
					
					BorderPane borderPane = new BorderPane();
					borderPane.setCenter(label);
					borderPane.setRight(toggleButton);
					HBox.setHgrow(borderPane, Priority.ALWAYS);
					
					return borderPane;
				}
			}.get(), null);
		}
	}
	
	private String pluginName = null;

	private String pluginVersion = null;
	
	private String pluginDescription = null;
	private URI pluginDescriptionURI = null;
	
	private String pluginAuthor = null;
	private URI pluginAuthorURI = null;
	
	private String pluginChangeLog = null;
	private URI pluginChangeLogURI = null;
	
	private String pluginLicense = null;
	private URI pluginLicenseURI = null;

	private String minimumCompatibleVersion = null;
	
	private String warningText = null;
	
	private URI pluginUpdateCheckURI = null;
	private URI pluginArchivesURI = null;
	private Consumer<HttpRequest> beforeRequest = null;
	
	private boolean oneTimePlugin = false;
	
	private SupportedPlatform[] supportedPlatforms = null;
	
	private String pluginStatisticsUUID = null;
	
	private PluginConfig pluginConfig = null;
	
	private LinkedHashMap<String, Function> functionMap = new LinkedHashMap<>();
	
	private BooleanProperty shouldShowLoadingBar = new SimpleBooleanProperty(false);
	
	private ObjectProperty<Throwable> exceptionObject = new SimpleObjectProperty(null);
	
	private ObjectProperty<TrayNotification> showTrayNotificationObject = new SimpleObjectProperty(null);
	private ObjectProperty<TrayNotification> dismissTrayNotificationObject = new SimpleObjectProperty(null);
	private BooleanProperty shouldDismissTrayNotifications = new SimpleBooleanProperty(false);
	
	private ObjectProperty<ClassLoader> classLoaderObject = new SimpleObjectProperty(null);
	
	private ObjectProperty<HttpHost> proxyHostObject = new SimpleObjectProperty(null);
	
	private BooleanProperty shouldBrowseActlistArchives = new SimpleBooleanProperty(false);
	
	private ObjectProperty<Boolean> shouldRequestShowActlist = new SimpleObjectProperty(null);
	
	private BooleanProperty shouldRequestDeactivate = new SimpleBooleanProperty(false);
	
	private ObjectProperty<SupportedPlatform> currentPlatformObject = new SimpleObjectProperty(null);
	
	private BooleanProperty darkModeProperty = new SimpleBooleanProperty(false);
	
	public ActlistPlugin(String pluginName) {
		this.pluginName = pluginName;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected static void debug() {
		DebugApp.debug(DebugParameter.custom().build());
	}
	
	/**
	 * @param isDebugMode if this value set to <code>false</code>, then {@link ActlistPlugin#isDebugMode()} will returns <code>false</code>. otherwise, <code>true</code>.
	 * @since 1.5.1
	 * @see #debug(DebugParameter)
	 */
	@Deprecated
	@CompatibleVersion("1.5.1")
	protected static void debug(boolean isDebugMode) {
		DebugApp.debug(DebugParameter.custom().setDebugMode(isDebugMode).build());
	}
	
	/**
	 * @param proxyHost e.g. "http://1.2.3.4:8080"
	 * @since 1.5.1
	 * @see #debug(DebugParameter)
	 */
	@Deprecated
	@CompatibleVersion("1.5.1")
	protected static void debug(String proxyHost) {
		DebugApp.debug(DebugParameter.custom().setProxyHost(proxyHost).build());
	}
	
	/**
	 * @param isDebugMode if this value set to <code>false</code>, then {@link ActlistPlugin#isDebugMode()} will returns <code>false</code>. otherwise, <code>true</code>.
	 * @param proxyHost e.g. "http://1.2.3.4:8080"
	 * @since 1.5.1
	 * @see #debug(DebugParameter)
	 */
	@Deprecated
	@CompatibleVersion("1.5.1")
	protected static void debug(boolean isDebugMode, String proxyHost) {
		DebugApp.debug(DebugParameter.custom().setDebugMode(isDebugMode).setProxyHost(proxyHost).build());
	}
	
	/**
	 * @param debugParameter e.g. <code>DebugParameter.custom().setProxyHost("http://1.2.3.4:8080").setDarkMode(true).build()</code>
	 * @since 1.6.0
	 */
	@CompatibleVersion("1.6.0")
	protected static void debug(DebugParameter debugParameter) {
		DebugApp.debug(debugParameter);
	}
	
	/**
	 * @since 1.2.6 created as a non-static method
	 * @since 1.5.1 breaking changes as a static method
	 */
	@CompatibleVersion("1.5.1")
	public static boolean isDebugMode() {
		// release mode
		if (DebugApp.debugParameter == null) {
			return false;
		}
		
		return DebugApp.debugParameter.isDebugMode();
	}
	
	/**
	 * @since 1.7.0
	 */
	@CompatibleVersion("1.7.0")
	public static void analyze() {
		DebugApp.analyze(DebugParameter.custom().build());
	}
	
	/**
	 * @since 1.7.0
	 */
	@CompatibleVersion("1.7.0")
	public static void analyze(DebugParameter debugParameter) {
		DebugApp.analyze(debugParameter);
	}
	
	/**
	 * Please write code inside of this method to initialize the plugin when first time.</p>
	 * <em>
	 * <b>CRITICAL</b> :</br>
	 * Please do not change this method's access modifier to public.</br>
	 * b/c name that '<tt>initialize</tt>' is method that called by FXMLLoader automatically.</br>
	 * so you should know that may occur change plugin's life cycle what if you change this method's access modifier to public from protected.
	 * </em>
	 * 
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	protected abstract void initialize() throws Exception;
	
	/**
	 * This method will be called when plugin is activated.</p>
	 * 
	 * Plugin will be activated when the user clicks to toggle button.</br>
	 * also this method will be called if this plugin was activate when Actlist is started up.</p>
	 * 
	 * <em>
	 * <b>CRITICAL</b> :</br>
	 * This method will be called via FxApplicationThread.
	 * If your work needs a few seconds for finish, you <b>must</b> write your code into a new Thread. (I'm <b>hirely recomend</b> this option)
     * If not, The Actlist's UI will be hang for few seconds.
	 * </em></p>
	 * 
	 * @throws Exception
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	public abstract void pluginActivated() throws Exception;
	
	/**
	 * This method will be called when plugin is deactivated.</p>
	 * 
	 * Plugin will be deactivated when the user clicks the toggle button again after plugin is activated.</p>
	 * 
	 * <em>
	 * <b>CRITICAL</b> :</br>
	 * This method will be called via FxApplicationThread.
	 * If your work needs a few seconds for finish, you <b>must</b> write your code into a new Thread. (I'm <b>hirely recomend</b> this option)
     * If not, The Actlist's UI will be hang for few seconds.
	 * </em></p>
	 * 
	 * @throws Exception
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	public abstract void pluginDeactivated() throws Exception;
	
	/**
	 * This method will be called when the update check response's <code>available</code> value is <code>true</code>.</p>
	 * 
	 * <em>
	 * You can't set pluginArchivesURI here. there will be no change and no effect. the pluginArchivesURI must be declared in the consturctor or update check response.
	 * </em></p>
	 * 
	 * <em>
	 * <b>CRITICAL</b> :</br>
	 * This method will be called via non-FxApplicationThread.
	 * so if you wanna do control some of the your graphic Node, you should writes code within FxApplicationThread.
	 * </em></p>
	 * 
	 * @throws Exception
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public void pluginUpdateFound() throws Exception {
		
	}
	
	/**
	 * This method will be called when Actlist application is activated.</p>
	 * 
	 * It could be time that user clicks system tray icon, or press the global short cut to showing up.
	 * 
	 * @throws Exception
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	public void applicationActivated() throws Exception {
		
	}
	
	/**
	 * This method will be called when Actlist application is deactivated.</p>
	 * 
	 * It could be time that user clicks minimize button or system tray icon again, or press the global short cut again when after shown.
	 *  
	 * @throws Exception
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	public void applicationDeactivated() throws Exception {
		
	}
	
	/**
	 * This method will be called when Actlist application got close request.</p>
	 * 
	 * It could be time that user clicks close button at task-manager, or press ALT+F4.
	 * 
	 * @throws Exception
	 * @since 1.2.2
	 */
	@CompatibleVersion("1.2.2")
	public void applicationCloseRequested() throws Exception {
		
	}
	
	/**
	 * This method will be called when Actlist application's config has been changed.</p>
	 * 
	 * Supported config event
	 * <ul>
	 * <li>proxy host</li>
	 * <li>dark mode</li>
	 * </ul>
	 * 
	 * @throws Exception
	 * @since 1.6.0
	 */
	@CompatibleVersion("1.6.0")
	public void applicationConfigChanged() throws Exception {
		
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected String getActlistVersion() {
		String actlistVersion = null;
		
		Class<?> buildVersionClass = getBuildVersionClass();
		if (buildVersionClass != null) {
			try {
				actlistVersion = (String) buildVersionClass.getField("VERSION").get(null);
			} catch (Exception | Error e) {
				
			}
		}
		
		return actlistVersion;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected String getActlistBuildTime() {
		String actlistBuildTime = null;
		
		Class<?> buildVersionClass = getBuildVersionClass();
		if (buildVersionClass != null) {
			try {
				actlistBuildTime = (String) buildVersionClass.getField("BUILD_TIME").get(null);
			} catch (Exception | Error e) {
				
			}
		}
		
		return actlistBuildTime;
	}
	
	
	Class<?> buildVersionClass;
	Class<?> getBuildVersionClass() {
		if (buildVersionClass == null) {
			try {
				buildVersionClass = Class.forName("org.silentsoft.actlist.version.BuildVersion");
			} catch (Exception | Error e) {
				
			}
		}
		
		return buildVersionClass;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected boolean isActlistVersionLowerThan(int major, int minor, int patch) {
		return VersionComparator.getInstance().compare(getActlistVersion(), String.format("%d.%d.%d", major, minor, patch)) < 0;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected boolean isActlistVersionSameWith(int major, int minor, int patch) {
		return VersionComparator.getInstance().compare(getActlistVersion(), String.format("%d.%d.%d", major, minor, patch)) == 0;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected boolean isActlistVersionHigherThan(int major, int minor, int patch) {
		return VersionComparator.getInstance().compare(getActlistVersion(), String.format("%d.%d.%d", major, minor, patch)) > 0;
	}
	
	/**
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	public String getPluginName() {
		return pluginName;
	}
	
	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	public String getPluginVersion() {
		return pluginVersion;
	}

	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	protected void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}
	
	/**
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	public String getPluginDescription() {
		return pluginDescription;
	}

	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	protected void setPluginDescription(String pluginDescription) {
		this.pluginDescription = pluginDescription;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginDescription(URI uri) {
		this.pluginDescriptionURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public URI getPluginDescriptionURI() {
		return pluginDescriptionURI;
	}
	
	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	public String getPluginAuthor() {
		return pluginAuthor;
	}

	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	protected void setPluginAuthor(String pluginAuthor) {
		this.pluginAuthor = pluginAuthor;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginAuthor(String pluginAuthor, URI uri) {
		this.pluginAuthor = pluginAuthor;
		this.pluginAuthorURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public URI getPluginAuthorURI() {
		return pluginAuthorURI;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public String getPluginChangeLog() {
		return pluginChangeLog;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginChangeLog(String pluginChangeLog) {
		this.pluginChangeLog = pluginChangeLog;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginChangeLog(URI uri) {
		this.pluginChangeLogURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public URI getPluginChangeLogURI() {
		return pluginChangeLogURI;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public String getPluginLicense() {
		return pluginLicense;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginLicense(String pluginLicense) {
		this.pluginLicense = pluginLicense;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginLicense(URI uri) {
		this.pluginLicenseURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public URI getPluginLicenseURI() {
		return pluginLicenseURI;
	}
	
	/**
	 * @since 1.2.4
	 */
	@CompatibleVersion("1.2.4")
	public String getMinimumCompatibleVersion() {
		return minimumCompatibleVersion;
	}

	/**
	 * @since 1.2.4
	 */
	@CompatibleVersion("1.2.4")
	protected void setMinimumCompatibleVersion(int major, int minor, int patch) {
		this.minimumCompatibleVersion = String.format("%d.%d.%d", major, minor, patch);
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public String getWarningText() {
		return warningText;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	protected void setWarningText(String warningText) {
		this.warningText = warningText;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public URI getPluginUpdateCheckURI() {
		return pluginUpdateCheckURI;
	}
	
	/**
	 * @since 1.2.6 created as a public method
	 * @since 1.6.0 breaking changes as a protected method
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginUpdateCheckURI(URI pluginUpdateCheckURI) {
		this.pluginUpdateCheckURI = pluginUpdateCheckURI;
	}
	
	/**
	 * @since 1.2.6 created as a public method
	 * @since 1.6.0 breaking changes as a protected method
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginUpdateCheckURI(URI pluginUpdateCheckURI, URI pluginArchivesURI) {
		this.pluginUpdateCheckURI = pluginUpdateCheckURI;
		this.pluginArchivesURI = pluginArchivesURI;
	}
	
	/**
	 * @since 1.5.0 created as a public method
	 * @since 1.6.0 breaking changes as a protected method
	 */
	@CompatibleVersion("1.5.0")
	protected void setPluginUpdateCheckURI(URI pluginUpdateCheckURI, Consumer<HttpRequest> beforeRequest) {
		this.pluginUpdateCheckURI = pluginUpdateCheckURI;
		this.beforeRequest = beforeRequest;
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public URI getPluginArchivesURI() {
		return pluginArchivesURI;
	}

	/**
	 * @since 1.2.6 created as a public method
	 * @since 1.6.0 breaking changes as a protected method
	 */
	@CompatibleVersion("1.2.6")
	protected void setPluginArchivesURI(URI pluginArchivesURI) {
		this.pluginArchivesURI = pluginArchivesURI;
	}
	
	/**
	 * @since 1.5.0
	 */
	@CompatibleVersion("1.5.0")
	public Consumer<HttpRequest> getBeforeRequest() {
		return beforeRequest;
	}
	
	/**
	 * @since 1.5.0 created as a public method
	 * @since 1.6.0 breaking changes as a protected method
	 */
	@CompatibleVersion("1.5.0")
	protected void setBeforeRequest(Consumer<HttpRequest> beforeRequest) {
		this.beforeRequest = beforeRequest;
	}
	
	/**
	 * @since 1.2.10
	 */
	@CompatibleVersion("1.2.10")
	public boolean isOneTimePlugin() {
		return oneTimePlugin;
	}
	
	/**
	 * @since 1.2.10 created as a public method
	 * @since 1.6.0 breaking changes as a protected method
	 */
	@CompatibleVersion("1.2.10")
	protected void setOneTimePlugin(boolean oneTimePlugin) {
		this.oneTimePlugin = oneTimePlugin;
	}
	
	/**
	 * @since 1.3.0
	 */
	@CompatibleVersion("1.3.0")
	public SupportedPlatform[] getSupportedPlatforms() {
		return supportedPlatforms;
	}
	
	/**
	 * @since 1.3.0 created as a public method
	 * @since 1.6.0 breaking changes as a protected method
	 */
	@CompatibleVersion("1.3.0")
	protected void setSupportedPlatforms(SupportedPlatform... supportedPlatforms) {
		this.supportedPlatforms = supportedPlatforms;
	}
	
	/**
	 * @since 1.6.0
	 */
	@CompatibleVersion("1.6.0")
	public String getPluginStatisticsUUID() {
		return pluginStatisticsUUID;
	}
	
	/**
	 * @since 1.6.0
	 */
	@CompatibleVersion("1.6.0")
	protected void setPluginStatisticsUUID(String pluginStatisticsUUID) {
		this.pluginStatisticsUUID = pluginStatisticsUUID;
	}
	
	PluginConfig getPluginConfig() {
		return pluginConfig;
	}
	
	void setPluginConfig(PluginConfig pluginConfig) {
		this.pluginConfig = pluginConfig;
	}
	
	private URL getPNG() {
		return getClassLoader().getResource(getClass().getName().replaceAll("\\.", "/").concat(".png"));
	}
	
	private URL getFXML() {
		return getClassLoader().getResource(getClass().getName().replaceAll("\\.", "/").concat(".fxml"));
	}
	
	private Boolean existsIcon;
	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	public boolean existsIcon() {
		if (existsIcon == null) {
			existsIcon = true;
			try {
				getPNG().openStream().close();
			} catch (Exception e) {
				existsIcon = false;
			}
		}
		
		return existsIcon;
	}
	
	private ImageView icon;
	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	public ImageView getIcon() throws Exception {
		if (icon == null) {
			icon = new ImageView(getPNG().toExternalForm());
		}
		return icon;
	}
	
	private Boolean existsGraphic;
	/**
	 * @since 1.1.0
	 */
	@CompatibleVersion("1.1.0")
	public boolean existsGraphic() {
		if (existsGraphic == null) {
			existsGraphic = true;
			try {
				getFXML().openStream().close();
			} catch (Exception e) {
				existsGraphic = false;
			}
		}
		
		return existsGraphic;
	}
	
	private Node graphic;
	/**
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	public Node getGraphic() throws Exception {
		if (graphic == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
			fxmlLoader.setClassLoader(getClassLoader());
			fxmlLoader.setController(this);
			graphic = fxmlLoader.load();
		}
		return graphic;
	}
	
	LinkedHashMap<String, Function> getFunctionMap() {
		return functionMap;
	}
	
	BooleanProperty shouldShowLoadingBar() {
		return shouldShowLoadingBar;
	}
	
	ObjectProperty<Throwable> exceptionObject() {
		return exceptionObject;
	}
	
	ObjectProperty<TrayNotification> showTrayNotificationObject() {
		return showTrayNotificationObject;
	}
	
	ObjectProperty<TrayNotification> dismissTrayNotificationObject() {
		return dismissTrayNotificationObject;
	}
	
	BooleanProperty shouldDismissTrayNotifications() {
		return shouldDismissTrayNotifications;
	}
	
	ObjectProperty<ClassLoader> classLoaderObject() {
		return classLoaderObject;
	}
	
	ObjectProperty<HttpHost> proxyHostObject() {
		return proxyHostObject;
	}
	
	BooleanProperty shouldBrowseActlistArchives() {
		return shouldBrowseActlistArchives;
	}
	
	ObjectProperty<Boolean> shouldRequestShowActlist() {
		return shouldRequestShowActlist;
	}
	
	BooleanProperty shouldRequestDeactivate() {
		return shouldRequestDeactivate;
	}
	
	ObjectProperty<SupportedPlatform> currentPlatformObject() {
		return currentPlatformObject;
	}
	
	BooleanProperty darkModeProperty() {
		return darkModeProperty;
	}
	
	/**
	 * @param functionName for display to user.
	 * @param function executes when user choosed.
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	protected void putFunction(String functionName, Function function) throws Exception {
		if (getFunctionMap().containsKey(functionName)) {
			throw new Exception(String.format("The function '%s' is already exists !", functionName));
		}
		
		getFunctionMap().put(functionName, function);
	}
	
	/**
	 * @since 1.7.0
	 */
	@CompatibleVersion("1.7.0")
	protected Function getFunction(String functionName) {
		return getFunctionMap().get(functionName);
	}
	
	/**
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	protected void removeFunction(String functionName) {
		getFunctionMap().remove(functionName);
	}
	
	/**
	 * @since 1.0.0
	 */
	@CompatibleVersion("1.0.0")
	protected void replaceFunction(String functionName, Function function) {
		getFunctionMap().replace(functionName, function);
	}
	
	/**
	 * Returns specific value that depends on <code>key</code> from own config file.</p>
	 * 
	 * <em>
	 * <b>IMPORTANT</b> :</br>
	 * Do not call this method on plugin's constructor.
	 * </em></p>
	 * 
	 * @since 1.1.0
	 */
	@CompatibleVersion("1.1.0")
	public <T> T getConfig(String key) throws Exception {
		return getPluginConfig().get(key);
	}
	
	/**
	 * Each <code>key</code> and </code>value</code> will be put into own config file.</p>
	 * 
	 * <em>
	 * <b>IMPORTANT</b> :</br>
	 * Do not call this method on plugin's constructor.
	 * </em></p>
	 * 
	 * @since 1.1.0
	 */
	@CompatibleVersion("1.1.0")
	public void putConfig(String key, Object value) throws Exception {
		getPluginConfig().put(key, value);
	}
	
	/**
	 * Removes entire key-value from own config file.</p>
	 * 
	 * <em>
	 * <b>IMPORTANT</b> :</br>
	 * Do not call this method on plugin's constructor.
	 * </em></p>
	 * 
	 * @since 1.1.0
	 */
	@CompatibleVersion("1.1.0")
	public void removeConfig(String key) throws Exception {
		getPluginConfig().remove(key);
	}
	
	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	public void showLoadingBar() {
		shouldShowLoadingBar().set(true);
	}
	
	/**
	 * @since 1.2.0
	 */
	@CompatibleVersion("1.2.0")
	public void hideLoadingBar() {
		shouldShowLoadingBar().set(false);
	}
	
	/**
	 * The plugin's toggle button will be toggle-off and displayed as RED color when you call to this method.</p>
	 * <em>
	 * <b>CRITICAL</b> :</br>
	 * if you created a thread and the thread is do something within infinite-while-loop,</br>
	 * you must to do that finalize all kind of thread that you are created.</p>
	 * 
	 * the recommended infinite-while-loop code of thread is below.
	 * <code><pre>
	 * private Thread thread;
	 * 
	 * <code>@Override</code>
	 * public void pluginActivated() throws Exception {
	 *     thread = null;
	 *     thread = new Thread(() -> {
	 *         try {
	 *             while (true) {
	 *                 // do something here.
	 * 
	 *                 Thread.sleep(WHATEVER_YOU_WANT);
	 *             }
	 *         } catch (InterruptedException e) {
	 *             // expected exception.
	 *         } catch (Exception e) {
	 *             throwException(e);
	 *         }
	 *     });
	 *     thread.start();
	 * }
	 * 
	 * <code>@Override</code>
	 * public void pluginDeactivated() throws Exception {
	 *     thread.interrupt();
	 * }
	 * </pre></code>
	 * </em>
	 * 
	 * @param exception that you can't handle
	 * @since 1.2.1
	 */
	@CompatibleVersion("1.2.1")
	public void throwException(Throwable exception) {
		exceptionObject().set(exception);
	}
	
	/**
	 * @since 1.2.4
	 */
	@CompatibleVersion("1.2.4")
	public void showTrayNotification(TrayNotification trayNotification) {
		showTrayNotificationObject().set(trayNotification);
	}
	
	/**
	 * @since 1.2.4
	 */
	@CompatibleVersion("1.2.4")
	public void dismissTrayNotification(TrayNotification trayNotification) {
		dismissTrayNotificationObject().set(trayNotification);
	}
	
	/**
	 * @since 1.2.4
	 */
	@CompatibleVersion("1.2.4")
	public void dismissTrayNotifications() {
		shouldDismissTrayNotifications().set(true);
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public void browseActlistArchives() {
		shouldBrowseActlistArchives().set(true);
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public ClassLoader getClassLoader() {
		return classLoaderObject().get();
	}
	
	/**
	 * @since 1.2.6
	 */
	@CompatibleVersion("1.2.6")
	public HttpHost getProxyHost() {
		return proxyHostObject().get();
	}
	
	/**
	 * @since 1.2.10
	 */
	@CompatibleVersion("1.2.10")
	public void requestShowActlist() {
		shouldRequestShowActlist().set(true);
	}
	
	/**
	 * @since 1.2.10
	 */
	@CompatibleVersion("1.2.10")
	public void requestHideActlist() {
		shouldRequestShowActlist().set(false);
	}

	/**
	 * @since 1.2.10
	 */
	@CompatibleVersion("1.2.10")
	public void requestDeactivate() {
		shouldRequestDeactivate().set(true);
	}
	
	/**
	 * @since 1.3.0
	 */
	@CompatibleVersion("1.3.0")
	public SupportedPlatform getCurrentPlatform() {
		return currentPlatformObject().get();
	}
	
	/**
	 * @since 1.6.0
	 */
	@CompatibleVersion("1.6.0")
	public boolean isDarkMode() {
		return darkModeProperty().get();
	}
	
}
