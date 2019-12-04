package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CompanyConfig {
    @Value("${info.app.name}")
    private String companyAppName;

    @Value("${info.app.owner.name}")
    private String companyOwnerName;

    @Value("${info.app.owner.surname}")
    private String companyOwnerSurname;

    @Value("${info.app.administrator.address.street}")
    private String companyAddressStreet;

    @Value("${info.app.administrator.address.number}")
    private String companyAddressNumber;
}
