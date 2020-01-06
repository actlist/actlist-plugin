package org.silentsoft.actlist.plugin;

import org.slf4j.helpers.MessageFormatter;

/**
 * Do not use this class. This class will be removed in a future release.
 * 
 * @author silentsoft
 */
@Deprecated
public class DuplicateNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6678093238434689242L;

	public DuplicateNameException(String message) {
		super(message);
	}
	
	public DuplicateNameException(Throwable cause) {
		super(cause);
	}
	
	public DuplicateNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateNameException(String message, Object[] parameters) {
		super(MessageFormatter.arrayFormat(message, parameters).getMessage());
	}
	
	public DuplicateNameException(String message, Object[] parameters, Throwable cause) {
		super(MessageFormatter.arrayFormat(message, parameters).getMessage(), cause);
	}
	
}