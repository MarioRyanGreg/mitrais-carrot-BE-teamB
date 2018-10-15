package com.mitrais.carrot.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * custom validation RuntimeException
 *   
 * @author Febri_MW251
 *
 */
@ResponseStatus(value = HttpStatus.OK)
public class ValidationException extends RuntimeException {

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
     * @param resourceName resource of error
     * @param fieldName field name of error
     * @param fieldValue value of field that expected
     */
    public ValidationException(String resourceName, String fieldName, Object fieldValue) {
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
