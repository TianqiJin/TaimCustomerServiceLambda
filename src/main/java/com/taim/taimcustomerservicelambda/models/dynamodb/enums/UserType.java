package com.taim.taimcustomerservicelambda.models.dynamodb.enums;

public enum UserType{
    INDIVIDUAL("Individual"),
    COMPANY("Company");

    private String value;

    UserType(String vvalue){
        this.value = vvalue;
    }

    public String getValue() {
        return value;
    }

    public static UserType getUserType(String value){
        for (UserType s : UserType.values()){
            if (s.name().equalsIgnoreCase(value)){
                return s;
            }
        }
        return null;
    }

}
