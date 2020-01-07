package org.silentsoft.actlist.plugin;

import java.util.List;

public class AnalysisResult {
	
	private String minimumCompatibleVersion;
	
	private List<String> referencedMethodNames;

	public String getMinimumCompatibleVersion() {
		return minimumCompatibleVersion;
	}

	public void setMinimumCompatibleVersion(String minimumCompatibleVersion) {
		this.minimumCompatibleVersion = minimumCompatibleVersion;
	}

	public List<String> getReferencedMethodNames() {
		return referencedMethodNames;
	}

	public void setReferencedMethodNames(List<String> referencedMethodNames) {
		this.referencedMethodNames = referencedMethodNames;
	}

}
