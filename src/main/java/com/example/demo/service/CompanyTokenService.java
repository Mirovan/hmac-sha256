package com.example.demo.service;

import com.example.demo.model.CompanyData;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CompanyTokenService {
    CompanyData findCompanyById(String token) throws UsernameNotFoundException;
}
