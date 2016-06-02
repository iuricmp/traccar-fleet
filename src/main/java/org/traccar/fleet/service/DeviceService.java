package org.traccar.fleet.service;

import org.traccar.fleet.config.TraccarProperties;
import org.traccar.fleet.domain.Device;
import org.traccar.fleet.repository.DeviceRepository;
import org.traccar.fleet.web.rest.dto.DeviceDTO;
import org.traccar.fleet.web.rest.mapper.DeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Device.
 */
@Service
@Transactional
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceService.class);

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private DeviceMapper deviceMapper;

    @Inject
    private TraccarService traccarService;

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save
     * @return the persisted entity
     */
    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.debug("Request to save Device : {}", deviceDTO);
        Device device = deviceMapper.deviceDTOToDevice(deviceDTO);
        device = deviceRepository.save(device);
        DeviceDTO result = deviceMapper.deviceToDeviceDTO(device);
        return result;
    }

    /**
     *  Get all the devices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        Page<Device> result = deviceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one device by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DeviceDTO findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        Device device = deviceRepository.findOne(id);
        DeviceDTO deviceDTO = deviceMapper.deviceToDeviceDTO(device);
        return deviceDTO;
    }

    /**
     *  Delete the  device by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.delete(id);
    }
}
