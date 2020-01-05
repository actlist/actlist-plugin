package org.silentsoft.actlist.plugin;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DebugParameter {

	private boolean isDebugMode;
	private String proxyHost;
	private boolean isDarkMode;
	private boolean shouldAnalyze;
	private Path classesDirectoryToAnalyze;
	
	private DebugParameter() {
		
	}
	
	private DebugParameter(DebugParameterBuilder builder) {
		this.isDebugMode = builder.isDebugMode;
		this.proxyHost = builder.proxyHost;
		this.isDarkMode = builder.isDarkMode;
		this.shouldAnalyze = builder.shouldAnalyze;
		this.classesDirectoryToAnalyze = builder.classesDirectoryToAnalyze;
	}
	
	public boolean isDebugMode() {
		return isDebugMode;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public boolean isDarkMode() {
		return isDarkMode;
	}
	
	public boolean shouldAnalyze() {
		return shouldAnalyze;
	}
	
	public Path getClassesDirectoryToAnalyze() {
		return classesDirectoryToAnalyze;
	}

	public static DebugParameterBuilder custom() {
		return new DebugParameterBuilder();
	}
	
	public static class DebugParameterBuilder {
		private boolean isDebugMode;
		private String proxyHost;
		private boolean isDarkMode;
		private boolean shouldAnalyze;
		private Path classesDirectoryToAnalyze;
		
		private DebugParameterBuilder() {
			this.isDebugMode = true;
			this.proxyHost = null;
			this.isDarkMode = false;
			this.shouldAnalyze = true;
			this.classesDirectoryToAnalyze = Paths.get("target", "classes");
		}

		/**
		 * @param isDebugMode if this value set to <code>false</code>, then {@link ActlistPlugin#isDebugMode()} will returns <code>false</code>. otherwise, <code>true</code>.
		 * @return
		 */
		public DebugParameterBuilder setDebugMode(boolean isDebugMode) {
			this.isDebugMode = isDebugMode;
			
			return this;
		}

		/**
		 * @param proxyHost e.g. "http://1.2.3.4:8080"
		 * @return
		 */
		public DebugParameterBuilder setProxyHost(String proxyHost) {
			this.proxyHost = proxyHost;
			
			return this;
		}

		/**
		 * @param isDarkMode if this value set to <code>true</code>, then Plugin's L&F will be applied to dark mode. otherwise, default L&F will be applied.
		 * @return
		 */
		public DebugParameterBuilder setDarkMode(boolean isDarkMode) {
			this.isDarkMode = isDarkMode;
			
			return this;
		}
		
		/**
		 * @param shouldAnalyze if this value set to <code>true</code>, then entire <code>.class</code> files which is located {@link #classesDirectoryToAnalyze} will be analyzed to determine the minimum compatible version of the Plugin.
		 * @return
		 */
		public DebugParameterBuilder setAnalyze(boolean shouldAnalyze) {
			this.shouldAnalyze = shouldAnalyze;
			
			return this;
		}
		
		/**
		 * @param classesDirectoryToAnalyze e.g. <code>Paths.get("target", "classes")</code>
		 * @return
		 */
		public DebugParameterBuilder setClassesDirectoryToAnalyze(Path classesDirectoryToAnalyze) {
			this.classesDirectoryToAnalyze = classesDirectoryToAnalyze;
			
			return this;
		}

		public DebugParameter build() {
			return new DebugParameter(this);
		}
	}
}
