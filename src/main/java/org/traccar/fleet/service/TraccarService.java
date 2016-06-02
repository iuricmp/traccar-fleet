package org.traccar.fleet.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.traccar.fleet.config.TraccarProperties;
import org.traccar.fleet.web.rest.dto.DeviceTraccarDTO;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Service
public class TraccarService {

    private static final String GET_DEVICES = "/api/devices";
    
    @Inject
    private TraccarProperties traccarProperties;

    public List<DeviceTraccarDTO> getDevices() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<DeviceTraccarDTO[]> response = restTemplate
            .exchange(traccarProperties.getUrl() + GET_DEVICES, HttpMethod.GET, getHttpEntity(), DeviceTraccarDTO[].class);
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            throw new RuntimeException("Traccar communication failure.\n" + response.getBody());
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
