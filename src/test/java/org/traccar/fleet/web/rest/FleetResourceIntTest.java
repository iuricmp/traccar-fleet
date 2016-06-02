package org.traccar.fleet.web.rest;

import org.junit.Before;
import org.traccar.fleet.domain.Company;
import org.traccar.fleet.repository.CompanyRepository;

import javax.inject.Inject;

public class FleetResourceIntTest {

    private static final String  DEFAULT_NAME      = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String  DEFAULT_DOMAIN    = "AAAAAAAAAAAAAAA";
    private static final Boolean DEFAULT_ACTIVATED = false;

    @Inject
    private CompanyRepository companyRepository;

    private Company company;

    public Company initCompany() {
        company = new Company();
        company.setName(DEFAULT_NAME);
        company.setDomain(DEFAULT_DOMAIN);
        company.setActivated(DEFAULT_ACTIVATED);
        return companyRepository.save(company);
    }
}
