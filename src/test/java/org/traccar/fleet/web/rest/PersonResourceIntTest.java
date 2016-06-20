package org.traccar.fleet.web.rest;

import org.traccar.fleet.FleetApp;
import org.traccar.fleet.domain.Person;
import org.traccar.fleet.repository.PersonRepository;
import org.traccar.fleet.service.PersonService;
import org.traccar.fleet.web.rest.dto.PersonDTO;
import org.traccar.fleet.web.rest.mapper.PersonMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.traccar.fleet.domain.enumeration.PersonType;

/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FleetApp.class)
@WebAppConfiguration
@IntegrationTest
public class PersonResourceIntTest {


    private static final PersonType DEFAULT_PERSON_TYPE = PersonType.COMPANY;
    private static final PersonType UPDATED_PERSON_TYPE = PersonType.INDIVIDUAL;
    private static final String DEFAULT_FEDERAL_ID = "AAAAAAAAAAAAAA";
    private static final String UPDATED_FEDERAL_ID = "BBBBBBBBBBBBBB";
    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_TRADE_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_TRADE_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_EMAIL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_ALFA_ID = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ALFA_ID = "BBBBBBBBBBBBBBBBBBBB";

    private static final Long DEFAULT_EXTERNAL_ID = 1L;
    private static final Long UPDATED_EXTERNAL_ID = 2L;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private PersonMapper personMapper;

    @Inject
    private PersonService personService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPersonMockMvc;

    private Person person;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource();
        ReflectionTestUtils.setField(personResource, "personService", personService);
        ReflectionTestUtils.setField(personResource, "personMapper", personMapper);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        person = new Person();
        person.setPersonType(DEFAULT_PERSON_TYPE);
        person.setFederalId(DEFAULT_FEDERAL_ID);
        person.setName(DEFAULT_NAME);
        person.setTradeName(DEFAULT_TRADE_NAME);
        person.setEmail(DEFAULT_EMAIL);
        person.setAlfaId(DEFAULT_ALFA_ID);
        person.setExternalId(DEFAULT_EXTERNAL_ID);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.personToPersonDTO(person);

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = people.get(people.size() - 1);
        assertThat(testPerson.getPersonType()).isEqualTo(DEFAULT_PERSON_TYPE);
        assertThat(testPerson.getFederalId()).isEqualTo(DEFAULT_FEDERAL_ID);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getTradeName()).isEqualTo(DEFAULT_TRADE_NAME);
        assertThat(testPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPerson.getAlfaId()).isEqualTo(DEFAULT_ALFA_ID);
        assertThat(testPerson.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
    }

    @Test
    @Transactional
    public void checkPersonTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setPersonType(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.personToPersonDTO(person);

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isBadRequest());

        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFederalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setFederalId(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.personToPersonDTO(person);

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isBadRequest());

        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the people
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
                .andExpect(jsonPath("$.[*].personType").value(hasItem(DEFAULT_PERSON_TYPE.toString())))
                .andExpect(jsonPath("$.[*].federalId").value(hasItem(DEFAULT_FEDERAL_ID.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].tradeName").value(hasItem(DEFAULT_TRADE_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].alfaId").value(hasItem(DEFAULT_ALFA_ID.toString())))
                .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.personType").value(DEFAULT_PERSON_TYPE.toString()))
            .andExpect(jsonPath("$.federalId").value(DEFAULT_FEDERAL_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.tradeName").value(DEFAULT_TRADE_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.alfaId").value(DEFAULT_ALFA_ID.toString()))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = new Person();
        updatedPerson.setId(person.getId());
        updatedPerson.setPersonType(UPDATED_PERSON_TYPE);
        updatedPerson.setFederalId(UPDATED_FEDERAL_ID);
        updatedPerson.setName(UPDATED_NAME);
        updatedPerson.setTradeName(UPDATED_TRADE_NAME);
        updatedPerson.setEmail(UPDATED_EMAIL);
        updatedPerson.setAlfaId(UPDATED_ALFA_ID);
        updatedPerson.setExternalId(UPDATED_EXTERNAL_ID);
        PersonDTO personDTO = personMapper.personToPersonDTO(updatedPerson);

        restPersonMockMvc.perform(put("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = people.get(people.size() - 1);
        assertThat(testPerson.getPersonType()).isEqualTo(UPDATED_PERSON_TYPE);
        assertThat(testPerson.getFederalId()).isEqualTo(UPDATED_FEDERAL_ID);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getTradeName()).isEqualTo(UPDATED_TRADE_NAME);
        assertThat(testPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPerson.getAlfaId()).isEqualTo(UPDATED_ALFA_ID);
        assertThat(testPerson.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeDelete - 1);
    }
}
