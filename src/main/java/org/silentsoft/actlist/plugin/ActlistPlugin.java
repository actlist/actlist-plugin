package org.silentsoft.actlist.plugin;

import java.net.URI;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedHashMap;

import org.apache.http.HttpHost;
import org.silentsoft.actlist.plugin.tray.TrayNotification;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Please generate executable main class called '<tt>Plugin</tt>' where in default package(please do not assign package).</br>
 * <em><tt>NOTE : You should not write any code other than calling the <code>debug();</code> method for debugging in the main method.</tt></em></p>
 * 
 * The Actlist core find the '<tt>Plugin</tt>' class in your jar that not defined any package.</br>
 * then export your project to runnable jar file(option : *'Extract required libraries into generated JAR') and put into <tt>/plugins</tt> directory that under the Actlist installed path.</p>
 * 
 * To make a plugin that contains graphic things, you can write the 'Plugin.fxml' file where in the same location of your <tt>Plguin</tt> class.
 * Also you can set the plugin's icon image that display where in about section (Right click -> About) through 'Plugin.png'</br>
 * <em><tt>NOTE : the recommended size of image is 48x48.</tt></em></p>
 * 
 * If you wanna make it jar as compact and light version, you should remove unused classes from exported jar file.</br>
 * this is very important because the *Extract option is contains all dependent libraries (even unused).</p>
 * 
 * All done. Happy enjoy it !
 * 
 * @author silentsoft
 */
public abstract class ActlistPlugin {
	
	/**
	 * Actlist engine will reflects this variable to determine what the version of the ActlistPlugin is.
	 */
	@SuppressWarnings("unused")
	private String version = "1.4.0";
	
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
	
	private String pluginName;
	
	private SupportedPlatform[] supportedPlatforms;
	
	private boolean oneTimePlugin;
	
	private String pluginDescription;
	private URI pluginDescriptionURI;
	
	private String pluginChangeLog;
	private URI pluginChangeLogURI;
	
	private String pluginLicense;
	private URI pluginLicenseURI;
	
	private String pluginVersion;
	
	private URI pluginUpdateCheckURI;
	private URI pluginArchivesURI;
	
	private String pluginAuthor;
	private URI pluginAuthorURI;
	
	private String minimumCompatibleVersion;
	
	private String warningText;
	
	private PluginConfig pluginConfig;
	
	private LinkedHashMap<String, Function> functionMap;
	
	private ObjectProperty<SupportedPlatform> currentPlatformObject;
	
	private ObjectProperty<ClassLoader> classLoaderObject;
	
	private ObjectProperty<HttpHost> proxyHostObject;
	
	private BooleanProperty shouldShowLoadingBar;
	
	private ObjectProperty<Throwable> exceptionObject;
	
	private ObjectProperty<TrayNotification> showTrayNotificationObject;
	private ObjectProperty<TrayNotification> dismissTrayNotificationObject;
	private BooleanProperty shouldDismissTrayNotifications;
	
	private BooleanProperty shouldBrowseActlistArchives;
	
	private ObjectProperty<Boolean> shouldRequestShowActlist;
	
	private BooleanProperty shouldRequestDeactivate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActlistPlugin(String pluginName) {
		this.pluginName = pluginName;
		this.oneTimePlugin = false;
		this.functionMap = new LinkedHashMap<>();
		
		currentPlatformObject = new SimpleObjectProperty(null);
		classLoaderObject = new SimpleObjectProperty(null);
		proxyHostObject = new SimpleObjectProperty(null);
		shouldShowLoadingBar = new SimpleBooleanProperty(false);
		exceptionObject = new SimpleObjectProperty(null);
		showTrayNotificationObject = new SimpleObjectProperty(null);
		dismissTrayNotificationObject = new SimpleObjectProperty(null);
		shouldDismissTrayNotifications = new SimpleBooleanProperty(false);
		shouldBrowseActlistArchives = new SimpleBooleanProperty(false);
		shouldRequestShowActlist = new SimpleObjectProperty(null);
		shouldRequestDeactivate = new SimpleBooleanProperty(false);
	}
	
	/**
	 * @since 1.2.6
	 */
	protected static void debug() {
		DebugApp.debug();
	}
	
	/**
	 * @since 1.2.6
	 */
	protected boolean isDebugMode() {
		return DebugApp.isDebugMode;
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
	public abstract void pluginDeactivated() throws Exception;
	
	/**
	 * This method will be called when the update check response's <code>available</code> value is <code>true</code>.</p>
	 * 
	 * If you set pluginArchivesURI here, the second parameter of {@link #setPluginUpdateCheckURI(URI, URI)} and
	 * 'url' in the server response will be ignored and it will be browse to the url that you set within this method.</p>
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
	public void applicationCloseRequested() throws Exception {
		
	}
	
	/**
	 * @since 1.2.6
	 */
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
	protected boolean isActlistVersionLowerThan(int major, int minor, int patch) {
		return versionComparator.compare(getActlistVersion(), String.format("%d.%d.%d", major, minor, patch)) < 0;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected boolean isActlistVersionSameWith(int major, int minor, int patch) {
		return versionComparator.compare(getActlistVersion(), String.format("%d.%d.%d", major, minor, patch)) == 0;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected boolean isActlistVersionHigherThan(int major, int minor, int patch) {
		return versionComparator.compare(getActlistVersion(), String.format("%d.%d.%d", major, minor, patch)) > 0;
	}
	
	private Comparator<String> versionComparator = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1.equals(o2)) {
				return 0;
			}
			
			String[] _o1 = o1.split("\\.");
			String[] _o2 = o2.split("\\.");
			
			Integer o1Major = Integer.valueOf(_o1[0]);
			Integer o2Major = Integer.valueOf(_o2[0]);
			
			int majorCompare = o1Major.compareTo(o2Major);
			if (majorCompare == 0) {
				Integer o1Minor = Integer.valueOf(_o1[1]);
				Integer o2Minor = Integer.valueOf(_o2[1]);
				
				int minorCompare = o1Minor.compareTo(o2Minor);
				if (minorCompare == 0) {
					Integer o1Patch = Integer.valueOf(_o1[2]);
					Integer o2Patch = Integer.valueOf(_o2[2]);
					
					return o1Patch.compareTo(o2Patch);
				} else {
					return minorCompare;
				}
			}
			
			return majorCompare;
		}
	};
	
	/**
	 * @since 1.0.0
	 */
	public String getPluginName() {
		return pluginName;
	}
	
	/**
	 * @since 1.3.0
	 */
	public SupportedPlatform[] getSupportedPlatforms() {
		return supportedPlatforms;
	}
	
	/**
	 * @since 1.3.0
	 */
	public void setSupportedPlatforms(SupportedPlatform... supportedPlatforms) {
		this.supportedPlatforms = supportedPlatforms;
	}
	
	/**
	 * @since 1.2.10
	 */
	public boolean isOneTimePlugin() {
		return oneTimePlugin;
	}
	
	/**
	 * @since 1.2.10
	 */
	public void setOneTimePlugin(boolean oneTimePlugin) {
		this.oneTimePlugin = oneTimePlugin;
	}
	
	/**
	 * @since 1.0.0
	 */
	public String getPluginDescription() {
		return pluginDescription;
	}

	/**
	 * @since 1.2.0
	 */
	protected void setPluginDescription(String pluginDescription) {
		this.pluginDescription = pluginDescription;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected void setPluginDescription(URI uri) {
		this.pluginDescriptionURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	public URI getPluginDescriptionURI() {
		return pluginDescriptionURI;
	}
	
	/**
	 * @since 1.2.6
	 */
	public String getPluginChangeLog() {
		return pluginChangeLog;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected void setPluginChangeLog(String pluginChangeLog) {
		this.pluginChangeLog = pluginChangeLog;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected void setPluginChangeLog(URI uri) {
		this.pluginChangeLogURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	public URI getPluginChangeLogURI() {
		return pluginChangeLogURI;
	}
	
	/**
	 * @since 1.2.6
	 */
	public String getPluginLicense() {
		return pluginLicense;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected void setPluginLicense(String pluginLicense) {
		this.pluginLicense = pluginLicense;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected void setPluginLicense(URI uri) {
		this.pluginLicenseURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	public URI getPluginLicenseURI() {
		return pluginLicenseURI;
	}

	/**
	 * @since 1.2.0
	 */
	public String getPluginVersion() {
		return pluginVersion;
	}

	/**
	 * @since 1.2.0
	 */
	protected void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}

	/**
	 * @since 1.2.0
	 */
	public String getPluginAuthor() {
		return pluginAuthor;
	}

	/**
	 * @since 1.2.0
	 */
	protected void setPluginAuthor(String pluginAuthor) {
		this.pluginAuthor = pluginAuthor;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected void setPluginAuthor(String pluginAuthor, URI uri) {
		this.pluginAuthor = pluginAuthor;
		this.pluginAuthorURI = uri;
	}
	
	/**
	 * @since 1.2.6
	 */
	public URI getPluginAuthorURI() {
		return pluginAuthorURI;
	}

	/**
	 * @since 1.2.6
	 */
	public URI getPluginArchivesURI() {
		return pluginArchivesURI;
	}

	/**
	 * @since 1.2.6
	 */
	public void setPluginArchivesURI(URI pluginArchivesURI) {
		this.pluginArchivesURI = pluginArchivesURI;
	}
	
	/**
	 * @since 1.2.6
	 */
	public URI getPluginUpdateCheckURI() {
		return pluginUpdateCheckURI;
	}
	
	/**
	 * @since 1.2.6
	 */
	public void setPluginUpdateCheckURI(URI pluginUpdateCheckURI) {
		setPluginUpdateCheckURI(pluginUpdateCheckURI, null);
	}
	
	/**
	 * @since 1.2.6
	 */
	public void setPluginUpdateCheckURI(URI pluginUpdateCheckURI, URI pluginArchivesURI) {
		this.pluginUpdateCheckURI = pluginUpdateCheckURI;
		this.pluginArchivesURI = pluginArchivesURI;
	}
	
	/**
	 * @since 1.2.4
	 */
	public String getMinimumCompatibleVersion() {
		return minimumCompatibleVersion;
	}

	/**
	 * @since 1.2.4
	 */
	protected void setMinimumCompatibleVersion(int major, int minor, int patch) {
		this.minimumCompatibleVersion = String.format("%d.%d.%d", major, minor, patch);
	}
	
	/**
	 * @since 1.2.6
	 */
	public String getWarningText() {
		return warningText;
	}
	
	/**
	 * @since 1.2.6
	 */
	protected void setWarningText(String warningText) {
		this.warningText = warningText;
	}

	PluginConfig getPluginConfig() {
		return pluginConfig;
	}
	
	void setPluginConfig(PluginConfig pluginConfig) {
		this.pluginConfig = pluginConfig;
	}
	
	private URL getPNG() {
		return getClassLoader().getResource(getClass().getSimpleName().concat(".png"));
	}
	
	private URL getFXML() {
		return getClassLoader().getResource(getClass().getSimpleName().concat(".fxml"));
	}
	
	private Boolean existsIcon;
	/**
	 * @since 1.2.0
	 */
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
	
	ObjectProperty<SupportedPlatform> currentPlatformObject() {
		return currentPlatformObject;
	}
	
	ObjectProperty<ClassLoader> classLoaderObject() {
		return classLoaderObject;
	}
	
	ObjectProperty<HttpHost> proxyHostObject() {
		return proxyHostObject;
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
	
	BooleanProperty shouldBrowseActlistArchives() {
		return shouldBrowseActlistArchives;
	}
	
	ObjectProperty<Boolean> shouldRequestShowActlist() {
		return shouldRequestShowActlist;
	}
	
	BooleanProperty shouldRequestDeactivate() {
		return shouldRequestDeactivate;
	}
	
	/**
	 * @param functionName for display to user.
	 * @param function executes when user choosed.
	 * @since 1.0.0
	 */
	protected void putFunction(String functionName, Function function) throws DuplicateNameException {
		if (getFunctionMap().containsKey(functionName)) {
			throw new DuplicateNameException("The function '{}' is already exists !", new Object[]{ functionName });
		}
		
		getFunctionMap().put(functionName, function);
	}
	
	/**
	 * @since 1.0.0
	 */
	protected void removeFunction(String functionName) {
		getFunctionMap().remove(functionName);
	}
	
	/**
	 * @since 1.0.0
	 */
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
	public void removeConfig(String key) throws Exception {
		getPluginConfig().remove(key);
	}
	
	/**
	 * @since 1.3.0
	 */
	public SupportedPlatform getCurrentPlatform() {
		return currentPlatformObject().get();
	}
	
	/**
	 * @since 1.2.6
	 */
	public ClassLoader getClassLoader() {
		return classLoaderObject().get();
	}
	
	/**
	 * @since 1.2.6
	 */
	public HttpHost getProxyHost() {
		return proxyHostObject().get();
	}
	
	/**
	 * @since 1.2.0
	 */
	public void showLoadingBar() {
		shouldShowLoadingBar().set(true);
	}
	
	/**
	 * @since 1.2.0
	 */
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
	public void throwException(Throwable exception) {
		exceptionObject().set(exception);
	}
	
	/**
	 * @since 1.2.4
	 */
	public void showTrayNotification(TrayNotification trayNotification) {
		showTrayNotificationObject().set(trayNotification);
	}
	
	/**
	 * @since 1.2.4
	 */
	public void dismissTrayNotification(TrayNotification trayNotification) {
		dismissTrayNotificationObject().set(trayNotification);
	}
	
	/**
	 * @since 1.2.4
	 */
	public void dismissTrayNotifications() {
		shouldDismissTrayNotifications().set(true);
	}
	
	/**
	 * @since 1.2.6
	 */
	public void browseActlistArchives() {
		shouldBrowseActlistArchives().set(true);
	}
	
	/**
	 * @since 1.2.10
	 */
	public void requestShowActlist() {
		shouldRequestShowActlist().set(true);
	}
	
	/**
	 * @since 1.2.10
	 */
	public void requestHideActlist() {
		shouldRequestShowActlist().set(false);
	}

	/**
	 * @since 1.2.10
	 */
	public void requestDeactivate() {
		shouldRequestDeactivate().set(true);
	}
	
}
