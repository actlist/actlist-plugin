package org.silentsoft.actlist.plugin;

import java.util.HashMap;

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
	}
	
	public String getPluginName() {
		return pluginName;
	}
	
	public String getPlguinDescription() {
		return pluginDescription;
	}
	
	private Node graphic;
	public Node getGraphic() throws Exception {
		if (graphic == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getClass().getSimpleName().concat(".fxml")));
			fxmlLoader.setController(this);
			graphic = fxmlLoader.load();
		}
		return graphic;
	}
	
	HashMap<String, Function> getFunctionMap() {
		return functionMap;
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
	
	public void applicationActivated() {
		
	}
	
	public void applicationDeactivated() {
		
	}
	
	public void pluginActivated() {
		
	}
	
	public void pluginDeactivated() {
		
	}
	
}
