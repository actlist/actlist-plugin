package org.silentsoft.actlist.plugin;

import java.net.URL;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Please generate executable main class called '<tt>Plugin</tt>' where in default package(please do not assign package).</br>
 * <em><tt>NOTE : you should not write any code in main method !</tt></em></p>
 * 
 * The Actlist core find the '<tt>Plugin</tt>' class in your jar that not defined any package.</br>
 * then export your project to runnable jar file(option : *'Extract required libraries into generated JAR') and put into <tt>/plugins</tt> directory that under the Actlist installed path.</p>
 * 
 * To make a plugin that contains graphic things, you can write the fxml file with your <tt>Plguin</tt> name.</p>
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
	
	private HashMap<String, Function> functionMap;
	
	private ObservableMap<String, String> configMap;

	public ActlistPlugin(String pluginName) {
		this(pluginName, null);
	}
	
	/**
	 * @param pluginName for display to user.
	 * @param pluginDescription for install tooltip.
	 */
	public ActlistPlugin(String pluginName, String pluginDescription) {
		this.pluginName = pluginName;
		this.pluginDescription = pluginDescription;
		this.functionMap = new HashMap<>();
		this.configMap = FXCollections.observableHashMap();
	}
	
	/**
	 * Please write code inside of this method to initialize the plugin when first time.</p>
	 * <em>
	 * CRITICAL : </br>
	 * Please do not change this method's access modifier to public.</br>
	 * b/c name that '<tt>initialize</tt>' is method that called by FXMLLoader automatically.</br>
	 * so you should know that may occur change plugin's life cycle what if you change this method's access modifier to public from protected.
	 * </em>
	 */
	protected abstract void initialize() throws Exception;
	
	public String getPluginName() {
		return pluginName;
	}
	
	public String getPlguinDescription() {
		return pluginDescription;
	}
	
	private URL getFXML() {
		return getClass().getResource(getClass().getSimpleName().concat(".fxml"));
	}
	
	public boolean existsGraphic() {
		boolean result = true;
		try {
			getFXML().openStream().close();
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	private Node graphic;
	public Node getGraphic() throws Exception {
		if (graphic == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
			fxmlLoader.setController(this);
			graphic = fxmlLoader.load();
		}
		return graphic;
	}
	
	HashMap<String, Function> getFunctionMap() {
		return functionMap;
	}
	
	ObservableMap<String, String> getConfigMap() {
		return configMap;
	}
	
	void setConfigMap(ObservableMap<String, String> configMap) {
		this.configMap = configMap;
	}
	
	/**
	 * @param functionName for display to user.
	 * @param function executes when user choosed.
	 */
	protected void putFunction(String functionName, Function function) throws DuplicateNameException {
		if (getFunctionMap().containsKey(functionName)) {
			throw new DuplicateNameException("The function '{}' is already exists !", new Object[]{ functionName });
		}
		
		getFunctionMap().put(functionName, function);
	}
	
	protected void removeFunction(String functionName) {
		getFunctionMap().remove(functionName);
	}
	
	protected void replaceFunction(String functionName, Function function) {
		getFunctionMap().replace(functionName, function);
	}
	
	public void applicationActivated() throws Exception {
		
	}
	
	public void applicationDeactivated() throws Exception {
		
	}
	
	public void pluginActivated() throws Exception {
		
	}
	
	public void pluginDeactivated() throws Exception {
		
	}
	
	public void putConfig(String key, String value) {
		getConfigMap().put(key, value);
	}
	
	public String getConfig(String key) {
		return getConfigMap().get(key);
	}
}
