package com.mitrais.carrot.utils;

/**
 * CustomErrorType this is class to show custom error messages
 *
 * @author wirdan_s773
 */
public class CustomErrorMessage {
    /**
     * Error message to show
     */
    private String errorMessage;

    /**
     * Constructor of CustomErrorType
     *
     * @param errorMessage the custom error message to show
     */
    public CustomErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * getErrorMessage get the custom error message
     *
     * @return errorMessage the custom error message to show
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
