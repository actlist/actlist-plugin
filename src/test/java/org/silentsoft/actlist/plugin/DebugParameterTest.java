package org.silentsoft.actlist.plugin;

import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;


public class DebugParameterTest {
	
	@Test
	public void test() {
		Assert.assertTrue(DebugParameter.custom().setDebugMode(true).build().isDebugMode());
		Assert.assertFalse(DebugParameter.custom().setDebugMode(false).build().isDebugMode());
		
		Assert.assertEquals("1.2.3.4", DebugParameter.custom().setProxyHost("1.2.3.4").build().getProxyHost());
		
		Assert.assertTrue(DebugParameter.custom().setDarkMode(true).build().isDarkMode());
		Assert.assertFalse(DebugParameter.custom().setDarkMode(false).build().isDarkMode());
		
		Assert.assertTrue(DebugParameter.custom().setAnalyze(true).build().shouldAnalyze());
		Assert.assertFalse(DebugParameter.custom().setAnalyze(false).build().shouldAnalyze());
		
		Assert.assertEquals(Paths.get("/"), DebugParameter.custom().setClassesDirectoryToAnalyze(Paths.get("/")).build().getClassesDirectoryToAnalyze());
		
		Assert.assertArrayEquals(new String[] {"ignore"}, DebugParameter.custom().setAnalysisIgnoreReferences(new String[] {"ignore"}).build().getAnalysisIgnoreReferences());
	}

}
