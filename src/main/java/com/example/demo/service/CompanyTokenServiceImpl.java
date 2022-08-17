package com.example.demo.service;

import com.example.demo.model.CompanyData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CompanyTokenServiceImpl implements CompanyTokenService {

    //ToDo: имитация БД, необходимо добавить симметричный алгоритм шифрования для secretToken
    private final Map<String, CompanyData> companies =
            Map.of(
                    "111", new CompanyData("111", "Kristalgroup", "qwerty"),
                    "222", new CompanyData("222", "OOOZavtra", "asdfgh")
            );

    @Override
    public CompanyData findCompanyById(String companyId) {
        System.out.println(companyId + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");

        Optional<String> companyOpt = companies.keySet().stream()
                .filter(item -> item.equals(companyId))
                .findFirst();

        if ( companyOpt.isPresent() ) {
            return companies.get(companyId);
        } else {
            return null;
        }
//        System.out.println(companies1.get(token) + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");

    }



}