package org.silentsoft.actlist.plugin;

public class DebugParameter {

	private boolean isDebugMode;
	private String proxyHost;
	private boolean isDarkMode;
	
	private DebugParameter() {
		
	}
	
	private DebugParameter(DebugParameterBuilder builder) {
		this.isDebugMode = builder.isDebugMode;
		this.proxyHost = builder.proxyHost;
		this.isDarkMode = builder.isDarkMode;
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

	public static DebugParameterBuilder custom() {
		return new DebugParameterBuilder();
	}
	
	public static class DebugParameterBuilder {
		private boolean isDebugMode;
		private String proxyHost;
		private boolean isDarkMode;
		
		private DebugParameterBuilder() {
			this.isDebugMode = true;
			this.proxyHost = null;
			this.isDarkMode = false;
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

		public DebugParameter build() {
			return new DebugParameter(this);
		}
	}
}
