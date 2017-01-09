# Actlist Plugin
> Build your own Actlist plugin.

With Actlist plugin, You can create anything.

![](http://silentsoft.org/actlist/images/preview.png)

## Development setup

To create an Actlist plugin, you need to do some of the following:
* Clone projects as following command or clone via GUI.
```
git clone https://github.com/silentsoft/silentsoft-io.git
git clone https://github.com/silentsoft/silentsoft-core.git
git clone https://github.com/silentsoft/actlist-plugin.git
```
* Create a new Java project and configure to Maven project.
* Add dependency as like below:
```
<dependency>
	<groupId>org.silentsoft</groupId>
	<artifactId>actlist-plugin</artifactId>
	<version>1.0.0</version>
</dependency>
```
* Generate executable main class called `Plugin` where in default package (please do not assign package).
* Inherit the `ActlistPlugin` class in your `Plugin` class.
* (Option) to make a plugin that contains graphic things, you can write the `Plugin.fxml` file where in the same location.
* (Option) you can set the plugin's icon image that display where in about menu (Right click > About) through `Plugin.png`
* Done.

Here is an example source code of `Plugin.java`
```
import org.silentsoft.actlist.plugin.ActlistPlugin;

public class Plugin extends ActlistPlugin {
    
    public static void main(String args[]) throws Exception {}
    
    public Plugin() throws Exception {
        super("Example Plugin");
        
        setPluginDescription("You can set the description of your plugin");
        setPluginVersion("1.0.0");
        setPluginAuthor("Silentsoft (http://silentsoft.org)");
    }
    
    @Override
    protected void initialize() throws Exception {
        System.out.println("Hello, World !");
    }
    
    @Override
    public void pluginActivated() throws Exception {
        System.out.println("plugin is activated.");
    }
    
    @Override
    public void pluginDeactivated() throws Exception {
        System.out.println("plugin is deactivated.");
    }

}
```

## How to export as jar file

* (Only the first time) Plugin.java > Right click > `Run as Java Application`
  
  ![](http://silentsoft.org/actlist/images/export-1.png)

* Project > Right click > `Export`
  
  ![](http://silentsoft.org/actlist/images/export-2.png)

* Select `Runnable JAR file`
  
  ![](http://silentsoft.org/actlist/images/export-3.png)

* Select your project in `Launch configuration` combo box and define export destination via `Browse...` button then choose `Extract required libraries into generated JAR` option
  
  ![](http://silentsoft.org/actlist/images/export-4.png)

* Basically, the Actlist application contains all the libraries that required plugin things. So, you must to delete libraries within your jar file for reduce size of file. (This action will save about 7.2 MB and the jar file will be about 45 KB from 7.24 MB)
* If you are not added any 3rd party libraries, then the inside of the jar file is like as below.
  ```
  /META-INF  
  Plugin.class  
  (option) Plugin.fxml  
  (option) Plugin.png
  ```
* Finally, put the jar file into `/plugins/` directory that under the Actlist installed path and (re)start to Actlist.

## How to define plugin's name

* It's easy to defining a plugin's name by super constructor parameter. just pass whatever you want as like below:
  ```
  public Plugin() throws Exception {
      super("whatever you want");
  }
  ```
  
  ![](http://silentsoft.org/actlist/images/how-to-define-plugin-name.png)

## How to define `about` menu

* You can easily decorate 'about' menu (Right click > About) by three methods.
  ```
  public Plugin() throws Exception {
      super("whatever you want");
      
      setPluginDescription("description here");
      setPluginVersion("1.0.0");
      setPluginAuthor("Silentsoft (http://silentsoft.org)");
  }
  ```
  
  ![](http://silentsoft.org/actlist/images/how-to-define-about-menu.png)

## How to add an image icon on `about` menu

* Just place `Plugin.png` to same location of `Plugin.java`

## How to add `function` (context menu)

* With `putFunction` method at `initialize` method, you can add function (context menu)
  ```
  @Override
  protected void initialize() throws Exception {
  	  putFunction("printHelloWorld", new TextFunction("Hello World", () -> {
		  System.out.println("Hello, World !");
	  }));
	  putFunction("printGoodMorning", new TextFunction("Good morning", () -> {
		  System.out.println("Good morning !");
	  }));
	  putFunction("printGoodNight", new TextFunction("Good Night", () -> {
		  System.out.println("Good night !");
	  }));
  }
  ```
  
  ![](http://silentsoft.org/actlist/images/how-to-add-function-context-menu.png)

## How to generate graphic things

* The Actlist is based on `JavaFx`. you can easily generate graphic plugin with `JavaFx` via `SceneBuilder`. Just create simple `Plugin.fxml` file and place `Plugin.fxml` to same location of `Plugin.java`

## How to show or hide the loading bar

* You can show or hide the loading bar when you create a graphic plugin.
  Way to show the loading bar:
  ```
  showLoadingBar();
  ```
  Way to hide the loading bar:
  ```
  hideLoadingBar();
  ```

## How to show message box

* The Actlist has built in `MessageBox`. See the sample source code below:
  ```
  Optional<ButtonType> result = MessageBox.showConfirm("Are you human?");
  result.ifPresent((buttonType) -> {
      if (buttonType == ButtonType.OK) {
          System.out.println("OK. You are human");
	  }
  });
  ```

## How to save and load the config information

* You can easily save and load via `putConfig(key, value)` and `getConfig(key)`

## How to throw exception

* You can tell to the Actlist through the `throwException(e)` method when you can not control the exception. (This action will cause to plugin to disable with red color and possible to  check the exception message via double click)
  
  ![](http://silentsoft.org/actlist/images/how-to-throw-exception.png)