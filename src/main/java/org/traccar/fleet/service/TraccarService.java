package org.traccar.fleet.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.traccar.fleet.config.TraccarProperties;
import org.traccar.fleet.domain.Company;
import org.traccar.fleet.domain.Device;
import org.traccar.fleet.web.rest.DeviceResource;
import org.traccar.fleet.web.rest.dto.DeviceTraccarDTO;
import org.traccar.fleet.web.rest.errors.CustomParameterizedException;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Service
public class TraccarService {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);

    private static final String GET_DEVICES = "/api/devices";

    @Inject
    private TraccarProperties traccarProperties;
    @Inject
    private DeviceService     deviceService;

    @Transactional
    public void importDevices(Company company) {
        List<DeviceTraccarDTO> devices = getDevices();
        for (DeviceTraccarDTO dto : devices) {
            Device device = deviceService.findOneByUniqueId(dto.getUniqueId());
            if (device == null) {
                device = new Device();
                device.setCompany(company);
            }
            device.setName(dto.getName());
            device.setTraccarId(dto.getId());
            device.setStatus(dto.getStatus());
            device.setUniqueId(dto.getUniqueId());
            deviceService.save(device);
        }
    }

    public List<DeviceTraccarDTO> getDevices() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<DeviceTraccarDTO[]> response = restTemplate
            .exchange(traccarProperties.getUrl() + GET_DEVICES, HttpMethod.GET, getHttpEntity(), DeviceTraccarDTO[].class);
        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            log.error("Traccar communication failure", response.getBody().toString());
            throw new CustomParameterizedException("Traccar communication failure");
        }
        return Arrays.asList(response.getBody());
    }

    protected HttpEntity<?> getHttpEntity() {
        return new HttpEntity<String>(getHttpHeaders());
    }

    protected HttpHeaders getHttpHeaders() {
        String plainCreds       = traccarProperties.getUsername() + ":" + traccarProperties.getPassword();
        byte[] plainCredsBytes  = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds      = new String(base64CredsBytes);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic " + base64Creds);
        return requestHeaders;
    }

}
