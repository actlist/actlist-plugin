package org.silentsoft.actlist.plugin;

import java.util.List;

@CompatibleVersion("1.7.0")
public class AnalysisResult {
	
	private String minimumCompatibleVersion;
	
	private List<String> referencedActlistClassNames;
	
	private List<String> referencedActlistMethodNames;
	
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
	public List<String> getReferencedActlistClassNames() {
		return referencedActlistClassNames;
	}

	@CompatibleVersion("1.7.0")
	public void setReferencedActlistClassNames(List<String> referencedActlistClassNames) {
		this.referencedActlistClassNames = referencedActlistClassNames;
	}

	@CompatibleVersion("1.7.0")
	public List<String> getReferencedActlistMethodNames() {
		return referencedActlistMethodNames;
	}

	@CompatibleVersion("1.7.0")
	public void setReferencedActlistMethodNames(List<String> referencedActlistMethodNames) {
		this.referencedActlistMethodNames = referencedActlistMethodNames;
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
