package org.silentsoft.actlist.plugin;

import java.util.List;

@CompatibleVersion("1.7.0")
public final class AnalysisResult {
	
	private String minimumCompatibleVersion;
	
	private List<String> references;
	
	@CompatibleVersion("1.7.0")
	public String getMinimumCompatibleVersion() {
		return minimumCompatibleVersion;
	}

	@CompatibleVersion("1.7.0")
	public void setMinimumCompatibleVersion(String minimumCompatibleVersion) {
		this.minimumCompatibleVersion = minimumCompatibleVersion;
	}
	
	@CompatibleVersion("1.7.0")
	public List<String> getReferences() {
		return references;
	}
	
	@CompatibleVersion("1.7.0")
	public void setReferences(List<String> references) {
		this.references = references;
	}
	
}
