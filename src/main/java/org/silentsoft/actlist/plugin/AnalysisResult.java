package org.silentsoft.actlist.plugin;

import java.util.List;

@CompatibleVersion("1.7.0")
public final class AnalysisResult {
	
	private String minimumCompatibleVersion;
	
	private List<String> referencedClassNames;
	
	private List<String> referencedMethodNames;

	@CompatibleVersion("1.7.0")
	public String getMinimumCompatibleVersion() {
		return minimumCompatibleVersion;
	}

	@CompatibleVersion("1.7.0")
	public void setMinimumCompatibleVersion(String minimumCompatibleVersion) {
		this.minimumCompatibleVersion = minimumCompatibleVersion;
	}
	
	@CompatibleVersion("1.7.0")
	public List<String> getReferencedClassNames() {
		return referencedClassNames;
	}

	@CompatibleVersion("1.7.0")
	public void setReferencedClassNames(List<String> referencedClassNames) {
		this.referencedClassNames = referencedClassNames;
	}

	@CompatibleVersion("1.7.0")
	public List<String> getReferencedMethodNames() {
		return referencedMethodNames;
	}

	@CompatibleVersion("1.7.0")
	public void setReferencedMethodNames(List<String> referencedMethodNames) {
		this.referencedMethodNames = referencedMethodNames;
	}
	
}
