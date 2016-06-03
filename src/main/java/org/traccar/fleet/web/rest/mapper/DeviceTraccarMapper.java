package org.traccar.fleet.web.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.traccar.fleet.domain.Device;
import org.traccar.fleet.web.rest.dto.DeviceDTO;
import org.traccar.fleet.web.rest.dto.DeviceTraccarDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface DeviceTraccarMapper {

    Device deviceTraccarDTOToDevice(DeviceTraccarDTO device);

    List<Device> devicesTraccarToDevices(List<DeviceTraccarDTO> devicesDto);

}
