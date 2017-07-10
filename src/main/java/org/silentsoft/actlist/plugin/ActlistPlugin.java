package org.silentsoft.actlist.plugin;

import java.net.URL;
import java.util.LinkedHashMap;

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
 * <em><tt>NOTE : you should not write any code in main method !</tt></em></p>
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
	
	private String pluginDescription;
	
	private String pluginVersion;
	
	private String pluginAuthor;
	
	private String minimumCompatibleVersion;
	
	private PluginConfig pluginConfig;
	
	private LinkedHashMap<String, Function> functionMap;
	
	private BooleanProperty shouldShowLoadingBar;
	
	private ObjectProperty<Throwable> exceptionObject;
	
	private ObjectProperty<TrayNotification> showTrayNotificationObject;
	private ObjectProperty<TrayNotification> dismissTrayNotificationObject;
	private BooleanProperty shouldDismissTrayNotifications;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActlistPlugin(String pluginName) {
		this.pluginName = pluginName;
		this.functionMap = new LinkedHashMap<>();
		
		shouldShowLoadingBar = new SimpleBooleanProperty(false);
		exceptionObject = new SimpleObjectProperty(null);
		showTrayNotificationObject = new SimpleObjectProperty(null);
		dismissTrayNotificationObject = new SimpleObjectProperty(null);
		shouldDismissTrayNotifications = new SimpleBooleanProperty(false);
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
	 * This method is called when plugin is activated.</p>
	 * 
	 * Plugin will be activated when the user clicks to toggle button.</br>
	 * also this method will be called if this plugin was activate when Actlist is started up.</p>
	 * 
	 * <em>
	 * <b>CRITICAL</b> :</br>
	 * This method is called via FxApplicationThread.
	 * If your work needs a few seconds for finish, you <b>must</b> write your code into a new Thread. (I'm <b>hirely recomend</b> this option)
     * If not, The Actlist's UI will be hang for few seconds.
	 * </em></p>
	 * 
	 * @throws Exception
	 * @since 1.0.0
	 */
	public abstract void pluginActivated() throws Exception;
	
	/**
	 * This method is called when plugin is deactivated.</p>
	 * 
	 * Plugin will be deactivated when the user clicks the toggle button again after plugin is activated.</p>
	 * 
	 * <em>
	 * <b>CRITICAL</b> :</br>
	 * This method is called via FxApplicationThread.
	 * If your work needs a few seconds for finish, you <b>must</b> write your code into a new Thread. (I'm <b>hirely recomend</b> this option)
     * If not, The Actlist's UI will be hang for few seconds.
	 * </em></p>
	 * 
	 * @throws Exception
	 * @since 1.0.0
	 */
	public abstract void pluginDeactivated() throws Exception;
	
	/**
	 * This method is called when Actlist application is activated.</p>
	 * 
	 * It could be time that user clicks system tray icon, or press the global short cut to showing up.
	 * 
	 * @throws Exception
	 * @since 1.0.0
	 */
	public void applicationActivated() throws Exception {
		
	}
	
	/**
	 * This method is called when Actlist application is deactivated.</p>
	 * 
	 * It could be time that user clicks minimize button or system tray icon again, or press the global short cut again when after shown.
	 *  
	 * @throws Exception
	 * @since 1.0.0
	 */
	public void applicationDeactivated() throws Exception {
		
	}
	
	/**
	 * This method is called when Actlist application got close request.</p>
	 * 
	 * It could be time that user clicks close button at task-manager, or press ALT+F4.
	 * 
	 * @throws Exception
	 * @since 1.2.2
	 */
	public void applicationCloseRequested() throws Exception {
		
	}
	
	/**
	 * @since 1.0.0
	 */
	public String getPluginName() {
		return pluginName;
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

	PluginConfig getPluginConfig() {
		return pluginConfig;
	}
	
	void setPluginConfig(PluginConfig pluginConfig) {
		this.pluginConfig = pluginConfig;
	}
	
	private URL getPNG() {
		return getClass().getResource(getClass().getSimpleName().concat(".png"));
	}
	
	private URL getFXML() {
		return getClass().getResource(getClass().getSimpleName().concat(".fxml"));
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
	 * @since 1.1.0
	 */
	public <T> T getConfig(String key) throws Exception {
		return getPluginConfig().get(key);
	}
	
	/**
	 * @since 1.1.0
	 */
	public void putConfig(String key, Object value) throws Exception {
		getPluginConfig().put(key, value);
	}
	
	/**
	 * @since 1.1.0
	 */
	public void removeConfig(String key) throws Exception {
		getPluginConfig().remove(key);
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
}
