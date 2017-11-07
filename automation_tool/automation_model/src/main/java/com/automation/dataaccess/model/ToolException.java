package com.automation.dataaccess.model;

public class ToolException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7577394804430321234L;
	Exception e ; 
	
	public ToolException(Exception e) {
		this.e = e;
	}

}
