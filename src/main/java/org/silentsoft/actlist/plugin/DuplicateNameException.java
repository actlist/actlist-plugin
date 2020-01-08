package org.silentsoft.actlist.plugin;

import org.slf4j.helpers.MessageFormatter;

/**
 * Do not use this class. This class will be removed in a future release.
 * 
 * @author silentsoft
 */
@Deprecated
@CompatibleVersion("1.0.0")
public class DuplicateNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6678093238434689242L;

	@CompatibleVersion("1.0.0")
	public DuplicateNameException(String message) {
		super(message);
	}
	
	@CompatibleVersion("1.0.0")
	public DuplicateNameException(Throwable cause) {
		super(cause);
	}
	
	@CompatibleVersion("1.0.0")
	public DuplicateNameException(String message, Throwable cause) {
		super(message, cause);
	}

	@CompatibleVersion("1.0.0")
	public DuplicateNameException(String message, Object[] parameters) {
		super(MessageFormatter.arrayFormat(message, parameters).getMessage());
	}
	
	@CompatibleVersion("1.0.0")
	public DuplicateNameException(String message, Object[] parameters, Throwable cause) {
		super(MessageFormatter.arrayFormat(message, parameters).getMessage(), cause);
	}
	
}