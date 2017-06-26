package org.silentsoft.actlist.plugin.tray;

import javafx.util.Duration;

public final class TrayNotification {

	private String title;
	
	private String message;
	
	private Duration duration;
	
	public TrayNotification(String message) {
		this(null, message, null);
	}
	
	public TrayNotification(String message, Duration duration) {
		this(null, message, duration);
	}
	
	public TrayNotification(String title, String message) {
		this(title, message, null);
	}
	
	public TrayNotification(String title, String message, Duration duration) {
		this.title = title;
		this.message = message;
		this.duration = duration;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public Duration getDuration() {
		return duration;
	}
	
}
