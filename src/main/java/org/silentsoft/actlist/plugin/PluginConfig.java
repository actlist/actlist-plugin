package org.silentsoft.actlist.plugin;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

import org.silentsoft.core.util.FileUtil;
import org.silentsoft.core.util.JSONUtil;

public class PluginConfig {

	private String plugin;
	
	private HashMap<String, Object> config;
	
	public PluginConfig() {
		this(null);
	}
	
	public PluginConfig(String plugin) {
		this.plugin = plugin;
		config = new HashMap<String, Object>();
	}
	
	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public HashMap<String, Object> getConfig() {
		return config;
	}

	public void setConfig(HashMap<String, Object> config) {
		this.config = config;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) throws Exception {
		return (T) config.get(key);
	}
	
	public void put(String key, Object value) throws Exception {
		config.put(key, value);
		commit();
	}
	
	public void remove(String key) throws Exception {
		config.remove(key);
		commit();
	}

	final void commit() throws Exception {
		File configFile = Paths.get(System.getProperty("user.dir"), "plugins", "config", plugin.concat(".config")).toFile();
		
		if (configFile.getParentFile().exists() == false) {
			configFile.getParentFile().mkdirs();
		}
		
		if (configFile.exists() == false) {
			configFile.createNewFile();
		}
		
		FileUtil.saveFile(configFile, JSONUtil.ObjectToString(this));
	}
	
}
