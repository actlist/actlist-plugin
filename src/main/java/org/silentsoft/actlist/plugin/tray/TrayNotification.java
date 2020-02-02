package org.silentsoft.actlist.plugin.tray;

import org.silentsoft.actlist.plugin.CompatibleVersion;

import javafx.util.Duration;

@CompatibleVersion("1.2.4")
public final class TrayNotification {

	private String title;
	
	private String message;
	
	private Duration duration;
	
	@CompatibleVersion("1.2.4")
	public TrayNotification(String message) {
		this(null, message, null);
	}
	
	@CompatibleVersion("1.2.4")
	public TrayNotification(String message, Duration duration) {
		this(null, message, duration);
	}
	
	@CompatibleVersion("1.2.4")
	public TrayNotification(String title, String message) {
		this(title, message, null);
	}
	
	@CompatibleVersion("1.2.4")
	public TrayNotification(String title, String message, Duration duration) {
		this.title = title;
		this.message = message;
		this.duration = duration;
	}

	@CompatibleVersion("1.2.4")
	public String getTitle() {
		return title;
	}

	@CompatibleVersion("1.2.4")
	public String getMessage() {
		return message;
	}

	@CompatibleVersion("1.2.4")
	public Duration getDuration() {
		return duration;
	}
	
}
