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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/traccar")
public class TraccarResource extends FleetResource {

    private final Logger log = LoggerFactory.getLogger(TraccarResource.class);

    @Inject
    private TraccarService traccarService;

    @RequestMapping(value = "/importDevices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<Void> importDevices(HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to import all Devices from Traccar");
        traccarService.importDevices(getCompanyByDomain(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
