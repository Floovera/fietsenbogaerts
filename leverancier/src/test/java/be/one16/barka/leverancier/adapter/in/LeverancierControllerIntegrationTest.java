package be.one16.barka.leverancier.adapter.in;

import be.one16.barka.leverancier.BarkaLeveranciersApplication;
import be.one16.barka.leverancier.BaseIntegrationTesting;
import be.one16.barka.leverancier.adapter.out.ContactJpaEntity;
import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import be.one16.barka.leverancier.adapter.out.repository.ContactRepository;
import be.one16.barka.leverancier.adapter.out.repository.LeverancierRepository;
import be.one16.barka.leverancier.common.TestDataBuilder;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;
import java.util.UUID;

import static be.one16.barka.leverancier.common.ContactMethode.EMAIL;
import static be.one16.barka.leverancier.common.ContactMethode.NUMMER;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration
@SpringBootTest(classes = BarkaLeveranciersApplication.class)
@Transactional
public class LeverancierControllerIntegrationTest extends BaseIntegrationTesting {

    private final static String BASE_URL = "/api/leveranciers";

    @Autowired
    private LeverancierRepository leverancierRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MockMvc mockMvc;

    private final Gson GSON = new Gson();

    @Test
    public void testGetLeverancierById() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));

        mockMvc.perform(get(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leverancierId").value(createdLeverancier.getUuid().toString()))
                .andExpect(jsonPath("$.naam").value(createdLeverancier.getNaam()))
                .andExpect(jsonPath("$.straat").value(createdLeverancier.getStraat()))
                .andExpect(jsonPath("$.huisnummer").value(createdLeverancier.getHuisnummer()))
                .andExpect(jsonPath("$.bus").value(createdLeverancier.getBus()))
                .andExpect(jsonPath("$.postcode").value(createdLeverancier.getPostcode()))
                .andExpect(jsonPath("$.gemeente").value(createdLeverancier.getGemeente()))
                .andExpect(jsonPath("$.land").value(createdLeverancier.getLand()))
                .andExpect(jsonPath("$.telefoonnummer").value(createdLeverancier.getTelefoonnummer()))
                .andExpect(jsonPath("$.mobiel").value(createdLeverancier.getMobiel()))
                .andExpect(jsonPath("$.fax").value(createdLeverancier.getFax()))
                .andExpect(jsonPath("$.email").value(createdLeverancier.getEmail()))
                .andExpect(jsonPath("$.btwNummer").value(createdLeverancier.getBtwNummer()))
                .andExpect(jsonPath("$.opmerkingen").value(createdLeverancier.getOpmerkingen()));
    }

    @Test
    public void testGetLeverancierByIdNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();
        mockMvc.perform(get(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetContactenOfLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactJpaEntity createdContactOne = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancier));
        ContactJpaEntity createdContactTwo = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Sven Agu", "CEO", EMAIL, "sven.agu@agu.com", createdLeverancier));

        mockMvc.perform(get(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].contactId").value(createdContactOne.getUuid().toString()))
                .andExpect(jsonPath("$[0].naam").value(createdContactOne.getNaam()))
                .andExpect(jsonPath("$[0].onderwerp").value(createdContactOne.getOnderwerp()))
                .andExpect(jsonPath("$[0].contactMethode").value(createdContactOne.getContactMethode().toString()))
                .andExpect(jsonPath("$[0].gegevens").value(createdContactOne.getGegevens()))
                .andExpect(jsonPath("$[1].contactId").value(createdContactTwo.getUuid().toString()))
                .andExpect(jsonPath("$[1].naam").value(createdContactTwo.getNaam()))
                .andExpect(jsonPath("$[1].onderwerp").value(createdContactTwo.getOnderwerp()))
                .andExpect(jsonPath("$[1].contactMethode").value(createdContactTwo.getContactMethode().toString()))
                .andExpect(jsonPath("$[1].gegevens").value(createdContactTwo.getGegevens()));
    }

    @Test
    public void testGetContactenOfLeverancierNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        mockMvc.perform(get(BASE_URL + "/" + uuidToUse + "/contacten"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void testGetContactOfLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactJpaEntity createdContact = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancier));

        mockMvc.perform(get(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactId").value(createdContact.getUuid().toString()))
                .andExpect(jsonPath("$.naam").value(createdContact.getNaam()))
                .andExpect(jsonPath("$.onderwerp").value(createdContact.getOnderwerp()))
                .andExpect(jsonPath("$.contactMethode").value(createdContact.getContactMethode().toString()))
                .andExpect(jsonPath("$.gegevens").value(createdContact.getGegevens()));
    }

    @Test
    public void testGetContactOfLeverancierOtherLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancierOne = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        LeverancierJpaEntity createdLeverancierTwo = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU US"));
        ContactJpaEntity createdContact = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancierOne));

        mockMvc.perform(get(BASE_URL + "/" + createdLeverancierTwo.getUuid() + "/contacten/" + createdContact.getUuid()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetContactOfLeverancierNotExistingContact() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));

        mockMvc.perform(get(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetLeveranciersFilteredAndPaginated() throws Exception {
        leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Specialized Belgium"));
        leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Koga Benelux"));
        leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Scott Germany"));

        // No filter
        mockMvc.perform(get(BASE_URL).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)));

        // Filtered on name
        mockMvc.perform(get(BASE_URL + "?naam=be").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].naam").value("Specialized Belgium"))
                .andExpect(jsonPath("$.content[1].naam").value("Koga Benelux"));

        // Page size 2
        mockMvc.perform(get(BASE_URL + "?size=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].naam").value("Specialized Belgium"))
                .andExpect(jsonPath("$.content[1].naam").value("Koga Benelux"));

        // Page size 1, page 2
        mockMvc.perform(get(BASE_URL + "?size=1&page=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].naam").value("Scott Germany"));

        // Sorted on name
        mockMvc.perform(get(BASE_URL + "?sort=naam").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].naam").value("Koga Benelux"))
                .andExpect(jsonPath("$.content[1].naam").value("Scott Germany"))
                .andExpect(jsonPath("$.content[2].naam").value("Specialized Belgium"));

        // Sorted on name desc
        mockMvc.perform(get(BASE_URL + "?sort=naam,desc").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].naam").value("Specialized Belgium"))
                .andExpect(jsonPath("$.content[1].naam").value("Scott Germany"))
                .andExpect(jsonPath("$.content[2].naam").value("Koga Benelux"));
    }

    @Test
    public void testCreateLeverancier() throws Exception {
        LeverancierDto leverancierToCreate = TestDataBuilder.generateTestLeverancierDto("Schimano BE");

        String response = mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<LeverancierJpaEntity> leverancierJpaEntity = leverancierRepository.findByUuid(UUID.fromString(response));
        assertTrue(leverancierJpaEntity.isPresent());

        validateLeverancierJpaToLeverancierDto(leverancierJpaEntity.get(), leverancierToCreate);
    }

    @Test
    public void testCreateLeverancierInvalid() throws Exception {
        LeverancierDto leverancierToCreate = TestDataBuilder.generateTestLeverancierDto("Giant NL");

        leverancierToCreate.setNaam(null);
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        leverancierToCreate.setNaam("");
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateContactOfLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactDto contactToCreate = TestDataBuilder.generateTestContactDto("Iljo Keise", "Tester", NUMMER, "0422558899");

        String response = mockMvc.perform(post(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten").contentType(APPLICATION_JSON).content(GSON.toJson(contactToCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<ContactJpaEntity> contactJpaEntity = contactRepository.findByUuid(UUID.fromString(response));
        assertTrue(contactJpaEntity.isPresent());

        validateContactJpaToContactDto(contactJpaEntity.get(), contactToCreate);
    }

    @Test
    public void testCreateContactOfLeverancierInvalidContact() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactDto contactToCreate = TestDataBuilder.generateTestContactDto("Iljo Keise", "Tester", NUMMER, "0422558899");

        contactToCreate.setNaam(null);
        mockMvc.perform(post(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten").contentType(APPLICATION_JSON).content(GSON.toJson(contactToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToCreate.setNaam("");
        mockMvc.perform(post(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten").contentType(APPLICATION_JSON).content(GSON.toJson(contactToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToCreate.setNaam("Iljo Keise");
        contactToCreate.setContactMethode(null);
        mockMvc.perform(post(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten").contentType(APPLICATION_JSON).content(GSON.toJson(contactToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToCreate.setContactMethode(NUMMER);
        contactToCreate.setGegevens(null);
        mockMvc.perform(post(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten").contentType(APPLICATION_JSON).content(GSON.toJson(contactToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToCreate.setGegevens("");
        mockMvc.perform(post(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten").contentType(APPLICATION_JSON).content(GSON.toJson(contactToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateContactOfLeverancierNotExistingLeverancier() throws Exception {
        ContactDto contactToCreate = TestDataBuilder.generateTestContactDto("Iljo Keise", "Tester", NUMMER, "0422558899");

        mockMvc.perform(post(BASE_URL + "/" + UUID.randomUUID() + "/contacten").contentType(APPLICATION_JSON).content(GSON.toJson(contactToCreate)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Castelli"));
        LeverancierDto leverancierToUpdate = TestDataBuilder.generateTestLeverancierDto("Castelli Benelux");

        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<LeverancierJpaEntity> updatedLeverancier = leverancierRepository.findByUuid(createdLeverancier.getUuid());
        assertTrue(updatedLeverancier.isPresent());

        validateLeverancierJpaToLeverancierDto(updatedLeverancier.get(), leverancierToUpdate);
    }

    @Test
    public void testUpdateLeverancierInvalid() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Mantel"));
        LeverancierDto leverancierToUpdate = TestDataBuilder.generateTestLeverancierDto("Mantel Benelux");

        leverancierToUpdate.setNaam(null);
        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        leverancierToUpdate.setNaam("");
        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateLeverancierNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        LeverancierDto leverancierToUpdate = TestDataBuilder.generateTestLeverancierDto("Peleton De Paris");
        leverancierToUpdate.setLeverancierId(uuidToUse);

        mockMvc.perform(put(BASE_URL + "/" + uuidToUse).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateContactOfLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactJpaEntity createdContact = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancier));
        ContactDto contactToUpdate = TestDataBuilder.generateTestContactDto("Bart Swings", "Magazijn", EMAIL, "bart.swings@agu.com");

        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ContactJpaEntity> updatedContact = contactRepository.findByUuid(createdContact.getUuid());
        assertTrue(updatedContact.isPresent());

        validateContactJpaToContactDto(updatedContact.get(), contactToUpdate);
    }

    @Test
    public void testUpdateContactOfLeverancierInvalidContact() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactJpaEntity createdContact = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancier));
        ContactDto contactToUpdate = TestDataBuilder.generateTestContactDto("Bart Swings", "Magazijn", EMAIL, "bart.swings@agu.com");

        contactToUpdate.setNaam(null);
        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToUpdate.setNaam("");
        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToUpdate.setNaam("Iljo Keise");
        contactToUpdate.setContactMethode(null);
        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToUpdate.setContactMethode(NUMMER);
        contactToUpdate.setGegevens(null);
        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        contactToUpdate.setGegevens("");
        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateContactOfLeverancierNotExistingContact() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactDto contactToUpdate = TestDataBuilder.generateTestContactDto("Bart Swings", "Magazijn", EMAIL, "bart.swings@agu.com");

        mockMvc.perform(put(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + UUID.randomUUID()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateContactOfLeverancierOtherLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactJpaEntity createdContact = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancier));
        ContactDto contactToUpdate = TestDataBuilder.generateTestContactDto("Bart Swings", "Magazijn", EMAIL, "bart.swings@agu.com");

        mockMvc.perform(put(BASE_URL + "/" + UUID.randomUUID() + "/contacten/" + createdContact.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(contactToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Vive le velo"));

        mockMvc.perform(delete(BASE_URL + "/" + createdLeverancier.getUuid()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<LeverancierJpaEntity> deletedLeverancier = leverancierRepository.findByUuid(createdLeverancier.getUuid());
        assertTrue(deletedLeverancier.isEmpty());
    }

    @Test
    public void testDeleteLeverancierNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        mockMvc.perform(delete(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteContactOfLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactJpaEntity createdContact = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancier));

        mockMvc.perform(delete(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + createdContact.getUuid()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ContactJpaEntity> deletedContact = contactRepository.findByUuid(createdContact.getUuid());
        assertTrue(deletedContact.isEmpty());
    }

    @Test
    public void testDeleteContactOfLeverancierNotExistingContact() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));

        mockMvc.perform(delete(BASE_URL + "/" + createdLeverancier.getUuid() + "/contacten/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteContactOfLeverancierOtherLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));
        ContactJpaEntity createdContact = contactRepository.save(TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", createdLeverancier));

        mockMvc.perform(delete(BASE_URL + "/" + UUID.randomUUID() + "/contacten/" + createdContact.getUuid()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private void validateLeverancierJpaToLeverancierDto(LeverancierJpaEntity entity, LeverancierDto leverancierDto) {
        assertEquals(leverancierDto.getNaam(), entity.getNaam());
        assertEquals(leverancierDto.getStraat(), entity.getStraat());
        assertEquals(leverancierDto.getHuisnummer(), entity.getHuisnummer());
        assertEquals(leverancierDto.getBus(), entity.getBus());
        assertEquals(leverancierDto.getPostcode(), entity.getPostcode());
        assertEquals(leverancierDto.getGemeente(), entity.getGemeente());
        assertEquals(leverancierDto.getLand(), entity.getLand());
        assertEquals(leverancierDto.getTelefoonnummer(), entity.getTelefoonnummer());
        assertEquals(leverancierDto.getMobiel(), entity.getMobiel());
        assertEquals(leverancierDto.getFax(), entity.getFax());
        assertEquals(leverancierDto.getEmail(), entity.getEmail());
        assertEquals(leverancierDto.getBtwNummer(), entity.getBtwNummer());
        assertEquals(leverancierDto.getOpmerkingen(), entity.getOpmerkingen());
    }

    private void validateContactJpaToContactDto(ContactJpaEntity entity, ContactDto contactDto) {
        assertEquals(contactDto.getNaam(), entity.getNaam());
        assertEquals(contactDto.getOnderwerp(), entity.getOnderwerp());
        assertEquals(contactDto.getContactMethode(), entity.getContactMethode());
        assertEquals(contactDto.getGegevens(), entity.getGegevens());
    }
}
