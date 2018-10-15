package com.mitrais.carrot.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * custom RuntimeException with Http Status Not Found 404
 * 
 * @author Febri_MW251
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * adding static serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * resourceName string
	 */
    private final String resourceName;

    /**
     * string fieldName
     */
    private final String fieldName;

    /**
     * string fieldValue
     */
    private final Object fieldValue;

    /**
     * adding error custom response
     * 
     * @param resourceName String resource
     * @param fieldName field name of data not found
     * @param fieldValue falue of field
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * get resource name
     * 
     * @return String of  resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * get fieldName name
     * 
     * @return String of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * get fieldValue Object
     * 
     * @return Object of fieldValue
     */
    public Object getFieldValue() {
        return fieldValue;
    }
}
