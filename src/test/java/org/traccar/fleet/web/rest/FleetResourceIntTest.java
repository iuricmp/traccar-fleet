package org.traccar.fleet.web.rest;

import org.junit.Before;
import org.traccar.fleet.domain.Company;
import org.traccar.fleet.domain.Device;
import org.traccar.fleet.repository.CompanyRepository;
import org.traccar.fleet.repository.DeviceRepository;

import javax.inject.Inject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FleetResourceIntTest {

    private static final String        DEFAULT_NAME        = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String        DEFAULT_DEVICE_NAME = "AAAAA";
    private static final String        DEFAULT_DOMAIN      = "AAAAAAAAAAAAAAA";
    private static final Boolean       DEFAULT_ACTIVATED   = false;
    private static final String        DEFAULT_UNIQUE_ID   = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String        DEFAULT_STATUS      = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final ZonedDateTime DEFAULT_LAST_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());

    private static final Long DEFAULT_TRACCAR_ID = 1L;
    private static final Long UPDATED_TRACCAR_ID = 2L;

    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private DeviceRepository  deviceRepository;

    private Company company;
    private Device  device;

    public Company initCompany() {
        company = new Company();
        company.setName(DEFAULT_NAME);
        company.setDomain(DEFAULT_DOMAIN);
        company.setActivated(DEFAULT_ACTIVATED);
        return companyRepository.save(company);
    }

    public Device initDevice() {
        device = new Device();
        device.setName(DEFAULT_DEVICE_NAME);
        device.setUniqueId(DEFAULT_UNIQUE_ID);
        device.setStatus(DEFAULT_STATUS);
        device.setLastUpdate(DEFAULT_LAST_UPDATE);
        device.setTraccarId(DEFAULT_TRACCAR_ID);
        company = initCompany();
        device.setCompany(company);
        deviceRepository.save(device);
        return device;
    }
}
