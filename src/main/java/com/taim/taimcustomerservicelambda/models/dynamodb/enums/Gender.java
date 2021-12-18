package com.taim.taimcustomerservicelambda.models.dynamodb.enums;

public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    NOT_SPECIFIED("Not Specified");

    private String value;

    Gender(String vvalue) {
        this.value = vvalue;
    }

    public String getValue() {
        return value;
    }

    public static Gender getGender(String value){
        for (Gender gender : Gender.values()){
            if (value.equalsIgnoreCase(gender.name())){
                return gender;
            }
        }
        return null;
    }


}
