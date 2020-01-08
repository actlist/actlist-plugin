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
	private String[] analysisIgnoreActlistClassNames;
	private String[] analysisIgnoreActlistMethodNames;
	
	private DebugParameter() {
		
	}
	
	private DebugParameter(DebugParameterBuilder builder) {
		this.isDebugMode = builder.isDebugMode;
		this.proxyHost = builder.proxyHost;
		this.isDarkMode = builder.isDarkMode;
		this.shouldAnalyze = builder.shouldAnalyze;
		this.classesDirectoryToAnalyze = builder.classesDirectoryToAnalyze;
		this.analysisIgnoreActlistClassNames = builder.analysisIgnoreActlistClassNames;
		this.analysisIgnoreActlistMethodNames = builder.analysisIgnoreActlistMethodNames;
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
	public String[] getAnalysisIgnoreActlistClassNames() {
		return analysisIgnoreActlistClassNames;
	}
	
	@CompatibleVersion("1.7.0")
	public String[] getAnalysisIgnoreActlistMethodNames() {
		return analysisIgnoreActlistMethodNames;
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
		private String[] analysisIgnoreActlistClassNames;
		private String[] analysisIgnoreActlistMethodNames;
		
		private DebugParameterBuilder() {
			this.isDebugMode = true;
			this.proxyHost = null;
			this.isDarkMode = false;
			this.shouldAnalyze = true;
			this.classesDirectoryToAnalyze = Paths.get("target", "classes");
			this.analysisIgnoreActlistClassNames = null;
			this.analysisIgnoreActlistMethodNames = null;
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
		 * @param isDarkMode if this value set to <code>true</code>, then Plugin's L&F will be applied to dark mode. otherwise, default L&F will be applied.
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
		 * @param analysisIgnoreActlistClassNames e.g. <code>new String[] {"org.silentsoft.actlist.plugin.DebugParameter", "org.silentsoft.actlist.plugin.DebugParameter$DebugParameterBuilder"}</code>
		 * @return
		 */
		@CompatibleVersion("1.7.0")
		public DebugParameterBuilder setAnalysisIgnoreActlistClassNames(String[] analysisIgnoreActlistClassNames) {
			this.analysisIgnoreActlistClassNames = analysisIgnoreActlistClassNames;
			
			return this;
		}
		
		/**
		 * @param analysisIgnoreActlistMethodNames e.g. <code>new String[] {"org.silentsoft.actlist.plugin.ActlistPlugin.debug(org.silentsoft.actlist.plugin.DebugParameter)", "org.silentsoft.actlist.plugin.DebugParameter.custom()", "org.silentsoft.actlist.plugin.DebugParameter$DebugParameterBuilder.build()"}</code>
		 * @return
		 */
		@CompatibleVersion("1.7.0")
		public DebugParameterBuilder setAnalysisIgnoreActlistMethodNames(String[] analysisIgnoreActlistMethodNames) {
			this.analysisIgnoreActlistMethodNames = analysisIgnoreActlistMethodNames;
			
			return this;
		}

		@CompatibleVersion("1.6.0")
		public DebugParameter build() {
			return new DebugParameter(this);
		}
	}
}
