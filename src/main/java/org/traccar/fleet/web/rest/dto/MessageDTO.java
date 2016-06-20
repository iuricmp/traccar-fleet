package org.traccar.fleet.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Message entity.
 */
public class MessageDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime messageTime;

    private Integer macroNumber;

    @Size(max = 4000)
    private String macroText;


    private Long deviceId;
    

    private String deviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(ZonedDateTime messageTime) {
        this.messageTime = messageTime;
    }
    public Integer getMacroNumber() {
        return macroNumber;
    }

    public void setMacroNumber(Integer macroNumber) {
        this.macroNumber = macroNumber;
    }
    public String getMacroText() {
        return macroText;
    }

    public void setMacroText(String macroText) {
        this.macroText = macroText;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;

        if ( ! Objects.equals(id, messageDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + id +
            ", messageTime='" + messageTime + "'" +
            ", macroNumber='" + macroNumber + "'" +
            ", macroText='" + macroText + "'" +
            '}';
    }
}
