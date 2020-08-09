package org.silentsoft.actlist.plugin;

import java.nio.file.Path;
import java.nio.file.Paths;

@CompatibleVersion("1.6.0")
public class DebugParameter {

	private boolean isDebugMode;
	private String proxyHost;
	private boolean isDarkMode;
	private boolean shouldAnalyze;
	private Path classesDirectoryToAnalyze;
	private String[] analysisIgnoreReferences;
	private String loggingLevel;
	
	private DebugParameter() {
		
	}
	
	private DebugParameter(DebugParameterBuilder builder) {
		this.isDebugMode = builder.isDebugMode;
		this.proxyHost = builder.proxyHost;
		this.isDarkMode = builder.isDarkMode;
		this.shouldAnalyze = builder.shouldAnalyze;
		this.classesDirectoryToAnalyze = builder.classesDirectoryToAnalyze;
		this.analysisIgnoreReferences = builder.analysisIgnoreReferences;
		this.loggingLevel = builder.loggingLevel;
	}
	
	@CompatibleVersion("1.6.0")
	public boolean isDebugMode() {
		return isDebugMode;
	}

	@CompatibleVersion("1.6.0")
	public String getProxyHost() {
		return proxyHost;
	}

	@CompatibleVersion("1.6.0")
	public boolean isDarkMode() {
		return isDarkMode;
	}
	
	@CompatibleVersion("1.7.0")
	public boolean shouldAnalyze() {
		return shouldAnalyze;
	}
	
	@CompatibleVersion("1.7.0")
	public Path getClassesDirectoryToAnalyze() {
		return classesDirectoryToAnalyze;
	}
	
	@CompatibleVersion("1.7.0")
	public String[] getAnalysisIgnoreReferences() {
		return analysisIgnoreReferences;
	}
	
	@CompatibleVersion("2.0.1")
	public String getLoggingLevel() {
		return loggingLevel;
	}
	
	@CompatibleVersion("1.6.0")
	public static DebugParameterBuilder custom() {
		return new DebugParameterBuilder();
	}
	
	@CompatibleVersion("1.6.0")
	public static class DebugParameterBuilder {
		private boolean isDebugMode;
		private String proxyHost;
		private boolean isDarkMode;
		private boolean shouldAnalyze;
		private Path classesDirectoryToAnalyze;
		private String[] analysisIgnoreReferences;
		private String loggingLevel;
		
		private DebugParameterBuilder() {
			this.isDebugMode = true;
			this.proxyHost = null;
			this.isDarkMode = false;
			this.shouldAnalyze = true;
			this.classesDirectoryToAnalyze = Paths.get("target", "classes");
			this.analysisIgnoreReferences = null;
			this.loggingLevel = "DEBUG";
		}

		/**
		 * @param isDebugMode if this value set to <code>false</code>, then {@link ActlistPlugin#isDebugMode()} will returns <code>false</code>. otherwise, <code>true</code>.
		 * @return
		 */
		@CompatibleVersion("1.6.0")
		public DebugParameterBuilder setDebugMode(boolean isDebugMode) {
			this.isDebugMode = isDebugMode;
			
			return this;
		}

		/**
		 * @param proxyHost e.g. "http://1.2.3.4:8080"
		 * @return
		 */
		@CompatibleVersion("1.6.0")
		public DebugParameterBuilder setProxyHost(String proxyHost) {
			this.proxyHost = proxyHost;
			
			return this;
		}

		/**
		 * @param isDarkMode if this value set to <code>true</code>, then Plugin's L&amp;F will be applied to dark mode. otherwise, default L&amp;F will be applied.
		 * @return
		 */
		@CompatibleVersion("1.6.0")
		public DebugParameterBuilder setDarkMode(boolean isDarkMode) {
			this.isDarkMode = isDarkMode;
			
			return this;
		}

		/**
		 * @param shouldAnalyze if this value set to <code>true</code>, then entire <code>.class</code> files which is located {@link #classesDirectoryToAnalyze} will be analyzed to determine the minimum compatible version of the Plugin.
		 * @return
		 */
		@CompatibleVersion("1.7.0")
		public DebugParameterBuilder setAnalyze(boolean shouldAnalyze) {
			this.shouldAnalyze = shouldAnalyze;
			
			return this;
		}
		
		/**
		 * @param classesDirectoryToAnalyze e.g. <code>Paths.get("target", "classes")</code>
		 * @return
		 */
		@CompatibleVersion("1.7.0")
		public DebugParameterBuilder setClassesDirectoryToAnalyze(Path classesDirectoryToAnalyze) {
			this.classesDirectoryToAnalyze = classesDirectoryToAnalyze;
			
			return this;
		}
		
		/**
		 * @param analysisIgnoreReferences e.g. <code>new String[] {"org.silentsoft.actlist.plugin.DebugParameter", "org.silentsoft.actlist.plugin.DebugParameter$DebugParameterBuilder", "org.silentsoft.actlist.plugin.ActlistPlugin.debug(org.silentsoft.actlist.plugin.DebugParameter)", "org.silentsoft.actlist.plugin.DebugParameter.custom()", "org.silentsoft.actlist.plugin.DebugParameter$DebugParameterBuilder.build()"}</code>
		 * @return
		 */
		@CompatibleVersion("1.7.0")
		public DebugParameterBuilder setAnalysisIgnoreReferences(String[] analysisIgnoreReferences) {
			this.analysisIgnoreReferences = analysisIgnoreReferences;
			
			return this;
		}
		
		/**
		 * @param loggingLevel e.g. <code>TRACE, DEBUG, INFO, WARN, ERROR</code>
		 * @return
		 */
		@CompatibleVersion("2.0.1")
		public DebugParameterBuilder setLoggingLevel(String loggingLevel) {
			this.loggingLevel = loggingLevel;
			
			return this;
		}
		
		@CompatibleVersion("1.6.0")
		public DebugParameter build() {
			return new DebugParameter(this);
		}
	}
}
