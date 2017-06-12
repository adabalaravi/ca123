/**
 * 
 */
package com.accenture.sdp.csmcc.common.exceptions;

/**
 * @author patrizio.pontecorvi
 *
 */
public class PropertyNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String property;
	private String clazz;
	private String method;
	/**
	 * @return the clazz
	 */
	public String getClazz() {
		return clazz;
	}
	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
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
	 * @param clazz
	 * @param method
	 */
	public PropertyNotFoundException(String property, String clazz,	String method) {
		super();
		this.property = property;
		this.clazz = clazz;
		this.method = method;
	}	
	
	/**
	 * @param property
	 * @param clazz
	 * @param method
	 * @param message
	 */
	public PropertyNotFoundException(String property, String message) {
		super(message);
		this.property = property;
	}
	
}
