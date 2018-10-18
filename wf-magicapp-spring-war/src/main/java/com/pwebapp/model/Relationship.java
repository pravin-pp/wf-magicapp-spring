package com.pwebapp.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Relationship {

    @NotEmpty(message = "Relationship Name should not be blank")
    private String relationName;

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    @Override
    public String toString() {
        return "Relationship [relationName=" + relationName + "]";
    }

}
