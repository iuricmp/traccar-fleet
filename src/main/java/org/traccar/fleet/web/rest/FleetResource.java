package org.traccar.fleet.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.traccar.fleet.domain.Company;
import org.traccar.fleet.service.CompanyService;
import org.traccar.fleet.web.rest.errors.CustomParameterizedException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class FleetResource {

    private final Logger log = LoggerFactory.getLogger(FleetResource.class);

    @Inject
    private CompanyService companyService;

    public Company getCompanyById(Long id) {
        return companyService.findOneById(id);
    }

    public Company getCompanyByDomain(HttpServletRequest request) {
        Company sistema = null;
        try {
            sistema = companyService.findFirstByDomain(getSubDomain(request));
            sistema.getName(); // will throw an exception if null
        } catch (Exception e) {
            throwSubdomainException();
        }
        return sistema;
    }

    private void throwSubdomainException() {
        throw new CustomParameterizedException(
            "Não foi possível localizar uma Sistema para a url que você está acessando. Tente de uma url do tipo: seusistema.easyflow.com.br",
            getServerAddress());
    }

    private String getServerAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("getServerAddress", e);
            return "server.com";
        }
    }

    public String getSubDomain(HttpServletRequest request) {
        String serverName = request.getServerName();
        return serverName.split("\\.")[0];
    }
}
