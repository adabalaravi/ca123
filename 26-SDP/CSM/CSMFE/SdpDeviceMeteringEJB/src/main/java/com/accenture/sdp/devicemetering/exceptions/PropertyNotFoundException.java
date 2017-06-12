package com.accenture.sdp.devicemetering.exceptions;

public class PropertyNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String property;
	private String classMethod;

	/**
	 * @return the classMethod
	 */
	public String getClassMethod() {
		return classMethod;
	}
	/**
	 * @param classMethod the classMethod to set
	 */
	public void setClassMethod(String classMethod) {
		this.classMethod = classMethod;
	}
	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}
	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}
	/**
	 * @param property
	 * @param classMethod
	 */
	public PropertyNotFoundException(String property, String classMethod) {
		super();
		this.property = property;
		this.classMethod = classMethod;
	}	
	
	/**
	 * @param property
	 * @param classMethod
	 * @param message
	 */
	public PropertyNotFoundException(String property, String classMethod, String message) {
		super(message);
		this.property = property;
		this.classMethod = classMethod;
	}
	
	/**
	 * @param property
	 * @param classMethod
	 * @param message
	 * @param cause
	 */
	public PropertyNotFoundException(String property, String classMethod, String message, Throwable cause) {
		super(message, cause);
		this.property = property;
		this.classMethod = classMethod;
	}
}
