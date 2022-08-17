package com.example.demo.model;

public class CompanyData {
    private String id;
    private String name;
    private String secretToken;

    public CompanyData() {
    }

    public CompanyData(String id, String name, String secretToken) {
        this.id = id;
        this.name = name;
        this.secretToken = secretToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }
}
