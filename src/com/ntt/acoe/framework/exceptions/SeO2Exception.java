package com.ntt.acoe.framework.exceptions;

/**
 * 
 * 
 *
  * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class SeO2Exception extends Exception {

	private static final long serialVersionUID = 1L;

	public SeO2Exception() {
	}

	public SeO2Exception(String message) {
		super(message);
	}

	public SeO2Exception(Throwable cause) {
		super(cause);
	}

	public SeO2Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public SeO2Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
