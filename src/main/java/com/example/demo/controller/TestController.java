package com.example.demo.controller;

import com.example.demo.service.CompanyTokenService;
import com.example.demo.model.CompanyData;
import com.example.demo.model.IndividualDataWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    private final CompanyTokenService companyTokenService;

    public TestController(CompanyTokenService companyTokenService) {
        this.companyTokenService = companyTokenService;
    }

    @GetMapping("/api/hello1")
    public String hello(Principal principal) {
        CompanyData company = companyTokenService.findCompanyById(principal.getName());
        return "Hello " + company.getName();
    }

    @PostMapping("/api/hello2")
    public String hello2(@RequestBody(required = false) IndividualDataWrapper individualDataWrapper, Principal principal) {
        CompanyData company = companyTokenService.findCompanyById(principal.getName());
        return "Hello " + company.getName();
    }

}
