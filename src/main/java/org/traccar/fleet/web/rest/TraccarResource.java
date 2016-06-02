package org.traccar.fleet.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.traccar.fleet.service.TraccarService;
import org.traccar.fleet.web.rest.dto.DeviceTraccarDTO;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/traccar")
public class TraccarResource {

    private final Logger log = LoggerFactory.getLogger(TraccarResource.class);

    @Inject
    private TraccarService traccarService;

    @RequestMapping(value = "/importDevices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<Void> importDevices() throws URISyntaxException {
        log.debug("REST request to import all Devices from Traccar");
        List<DeviceTraccarDTO> devices = traccarService.getDevices();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
