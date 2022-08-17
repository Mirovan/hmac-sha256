package com.example.demo.model;

public class IndividualDataWrapper {
    private String companyId;
    private IndividualData individualData;

    public IndividualDataWrapper() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public IndividualData getIndividualData() {
        return individualData;
    }

    public void setIndividualData(IndividualData individualData) {
        this.individualData = individualData;
    }
}
