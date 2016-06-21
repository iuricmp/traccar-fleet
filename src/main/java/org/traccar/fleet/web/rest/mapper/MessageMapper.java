package org.traccar.fleet.web.rest.mapper;

import org.apache.commons.lang3.StringUtils;
import org.traccar.fleet.domain.*;
import org.traccar.fleet.web.rest.dto.MessageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Message and its DTO MessageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MessageMapper {

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "device.name", target = "deviceName")
    @Mapping(source = "device.uniqueId", target = "deviceUniqueId")
    MessageDTO messageToMessageDTO(Message message);

    List<MessageDTO> messagesToMessageDTOs(List<Message> messages);

    @Mapping(source = "deviceId", target = "device")
    Message messageDTOToMessage(MessageDTO messageDTO);

    List<Message> messageDTOsToMessages(List<MessageDTO> messageDTOs);

    default Device deviceFromId(Long id) {
        if (id == null) {
            return null;
        }
        Device device = new Device();
        device.setId(id);
        return device;
    }
}
