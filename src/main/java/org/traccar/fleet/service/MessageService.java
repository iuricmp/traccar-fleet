package org.traccar.fleet.service;

import org.apache.commons.lang3.StringUtils;
import org.traccar.fleet.domain.Device;
import org.traccar.fleet.domain.Message;
import org.traccar.fleet.repository.MessageRepository;
import org.traccar.fleet.web.rest.dto.MessageDTO;
import org.traccar.fleet.web.rest.mapper.MessageMapper;
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

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private MessageMapper messageMapper;
    @Inject
    private DeviceService deviceService;

    /**
     * Save a message.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    public MessageDTO save(MessageDTO messageDTO) {
        log.debug("Request to save Message : {}", messageDTO);
        Message message = messageMapper.messageDTOToMessage(messageDTO);
        Device  device  = message.getDevice();
        if (messageDTO.getDeviceId() != null) {
            message.setDevice(new Device(messageDTO.getDeviceId()));
        } else if (isNotBlank(messageDTO.getDeviceUniqueId())) {
            message.setDevice(deviceService.findOneByUniqueId(messageDTO.getDeviceUniqueId()));
        }
        message = messageRepository.save(message);
        MessageDTO result = messageMapper.messageToMessageDTO(message);
        return result;
    }

    /**
     *  Get all the messages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Message> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        Page<Message> result = messageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one message by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MessageDTO findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        Message message = messageRepository.findOne(id);
        MessageDTO messageDTO = messageMapper.messageToMessageDTO(message);
        return messageDTO;
    }

    /**
     *  Delete the  message by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.delete(id);
    }
}
