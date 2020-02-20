package org.silentsoft.actlist.plugin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.silentsoft.core.util.FileUtil;
import org.silentsoft.core.util.JSONUtil;

public class PluginConfigTest {
	
	private Path configFilePath = Paths.get(System.getProperty("user.dir"), "plugins", "config", "junit.config");

	@Test
	public void test() throws Exception {
		new PluginConfig("junit").put("key", "value");
		{
			PluginConfig config = JSONUtil.JSONToObject(FileUtil.readFile(configFilePath.toFile()), PluginConfig.class);
			Assert.assertEquals("value", config.get("key"));
			
			config.remove("key");
		}
		{
			PluginConfig config = JSONUtil.JSONToObject(FileUtil.readFile(configFilePath.toFile()), PluginConfig.class);
			Assert.assertNull(config.get("key"));
		}
	}
	
	@After
	public void after() throws Exception {
		Files.delete(configFilePath);
	}
	
}
