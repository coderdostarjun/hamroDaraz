package com.hamroDaraz.daraz.exception;

public class ResourceNotFoundException extends  RuntimeException{
    String resourceName;
    String fieldName;
    Long fieldValue;
    String stringFieldValue;
    String message;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue)
    {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String stringFieldValue)
    {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,stringFieldValue));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.stringFieldValue=stringFieldValue;
    }
    public ResourceNotFoundException(String message)
    {
        super(String.format(message));
        this.message=message;
    }

}
