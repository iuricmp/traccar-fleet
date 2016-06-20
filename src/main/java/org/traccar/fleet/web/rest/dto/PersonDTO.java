package org.traccar.fleet.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.traccar.fleet.domain.enumeration.PersonType;

/**
 * A DTO for the Person entity.
 */
public class PersonDTO implements Serializable {

    private Long id;

    @NotNull
    private PersonType personType;

    @NotNull
    @Size(max = 14)
    private String federalId;

    @Size(max = 200)
    private String name;

    @Size(max = 100)
    private String tradeName;

    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String alfaId;

    private Long externalId;


    private Long companyId;
    

    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }
    public String getFederalId() {
        return federalId;
    }

    public void setFederalId(String federalId) {
        this.federalId = federalId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAlfaId() {
        return alfaId;
    }

    public void setAlfaId(String alfaId) {
        this.alfaId = alfaId;
    }
    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;

        if ( ! Objects.equals(id, personDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + id +
            ", personType='" + personType + "'" +
            ", federalId='" + federalId + "'" +
            ", name='" + name + "'" +
            ", tradeName='" + tradeName + "'" +
            ", email='" + email + "'" +
            ", alfaId='" + alfaId + "'" +
            ", externalId='" + externalId + "'" +
            '}';
    }
}
