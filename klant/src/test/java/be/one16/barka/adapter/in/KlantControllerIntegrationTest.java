package be.one16.barka.adapter.in;

import be.one16.barka.BaseIntegrationTesting;
import be.one16.barka.adapter.out.KlantJpaEntity;
import be.one16.barka.adapter.out.repository.KlantRepository;
import be.one16.barka.common.TestDataBuilder;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@EnableWebMvc
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration
@SpringBootTest(classes = KlantController.class)
@Transactional
public class KlantControllerIntegrationTest extends BaseIntegrationTesting {

    private final static String BASE_URL = "/api/klanten";

    @Autowired
    private KlantRepository klantRepository;

    @Autowired
    private MockMvc mockMvc;

    private final Gson GSON = new Gson();

    @Test
    public void testGetKlantById() throws Exception {
        KlantJpaEntity createdKlant = klantRepository.save(TestDataBuilder.generateTestKlantJpaEntity("Baantjer"));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + createdKlant.getUuid()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.klantId").value(createdKlant.getUuid().toString()))
                .andExpect(jsonPath("$.naam").value(createdKlant.getNaam()))
                .andExpect(jsonPath("$.klantType").value(createdKlant.getKlantType().toString()))
                .andExpect(jsonPath("$.straat").value(createdKlant.getStraat()))
                .andExpect(jsonPath("$.huisnummer").value(createdKlant.getHuisnummer()))
                .andExpect(jsonPath("$.bus").value(createdKlant.getBus()))
                .andExpect(jsonPath("$.postcode").value(createdKlant.getPostcode()))
                .andExpect(jsonPath("$.gemeente").value(createdKlant.getGemeente()))
                .andExpect(jsonPath("$.land").value(createdKlant.getLand()))
                .andExpect(jsonPath("$.telefoonnummer").value(createdKlant.getTelefoonnummer()))
                .andExpect(jsonPath("$.mobiel").value(createdKlant.getMobiel()))
                .andExpect(jsonPath("$.email").value(createdKlant.getEmail()))
                .andExpect(jsonPath("$.btwNummer").value(createdKlant.getBtwNummer()))
                .andExpect(jsonPath("$.opmerkingen").value(createdKlant.getOpmerkingen()));
    }

    @Test
    public void testGetKlantByIdNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetKlantenFilteredAndPaginated() throws Exception {
        klantRepository.save(TestDataBuilder.generateTestKlantJpaEntity("Baantjer"));
        klantRepository.save(TestDataBuilder.generateTestKlantJpaEntity("Ferry"));
        klantRepository.save(TestDataBuilder.generateTestKlantJpaEntity("Danielle"));

        // No filter
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)));

        // Filtered on name
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?naam=err").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].naam").value("Ferry"));

        // Page size 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].naam").value("Baantjer"))
                .andExpect(jsonPath("$.content[1].naam").value("Ferry"));

        // Page size 1, page 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=1&page=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].naam").value("Danielle"));

        // Sorted on name
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=naam").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].naam").value("Baantjer"))
                .andExpect(jsonPath("$.content[1].naam").value("Danielle"))
                .andExpect(jsonPath("$.content[2].naam").value("Ferry"));

        // Sorted on name desc
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=naam,desc").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].naam").value("Ferry"))
                .andExpect(jsonPath("$.content[1].naam").value("Danielle"))
                .andExpect(jsonPath("$.content[2].naam").value("Baantjer"));
    }

    @Test
    public void testCreateKlant() throws Exception {
        KlantDto klantToCreate = TestDataBuilder.generateTestKlantDto("Jommeke");

        String response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(klantToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<KlantJpaEntity> klantJpaEntity = klantRepository.findByUuid(UUID.fromString(response.replace("\"", ""))); //TODO: dubbel
        Assertions.assertTrue(klantJpaEntity.isPresent());

        validateKlantJpaToKlantDto(klantJpaEntity.get(), klantToCreate);
    }

    @Test
    public void testCreateKlantInvalid() throws Exception {
        KlantDto klantToCreate = TestDataBuilder.generateTestKlantDto("Jommeke");

        klantToCreate.setNaam(null);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(klantToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        klantToCreate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(klantToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateKlant() throws Exception {
        KlantJpaEntity createdKlant = klantRepository.save(TestDataBuilder.generateTestKlantJpaEntity("John"));
        KlantDto klantToUpdate = TestDataBuilder.generateTestKlantDto("Patrick");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdKlant.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(klantToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<KlantJpaEntity> updatedKlant = klantRepository.findByUuid(createdKlant.getUuid());
        assertTrue(updatedKlant.isPresent());

        validateKlantJpaToKlantDto(updatedKlant.get(), klantToUpdate);
    }

    @Test
    public void testUpdateKlantInvalid() throws Exception {
        KlantJpaEntity createdKlant = klantRepository.save(TestDataBuilder.generateTestKlantJpaEntity("Laurent"));
        KlantDto klantToUpdate = TestDataBuilder.generateTestKlantDto("Nick");

        klantToUpdate.setNaam(null);
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdKlant.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(klantToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        klantToUpdate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdKlant.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(klantToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateKlantNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        KlantDto klantToUpdate = TestDataBuilder.generateTestKlantDto("Jommeke");
        klantToUpdate.setKlantId(uuidToUse);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + uuidToUse).contentType(APPLICATION_JSON).content(GSON.toJson(klantToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteKlant() throws Exception {
        KlantJpaEntity createdKlant = klantRepository.save(TestDataBuilder.generateTestKlantJpaEntity("Jean-Pierre"));

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + createdKlant.getUuid()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<KlantJpaEntity> deletedKlant = klantRepository.findByUuid(createdKlant.getUuid());
        assertTrue(deletedKlant.isEmpty());
    }

    @Test
    public void testDeleteKlantNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private void validateKlantJpaToKlantDto(KlantJpaEntity entity, KlantDto klantDto) {
        assertEquals(klantDto.getNaam(), entity.getNaam());
        assertEquals(klantDto.getKlantType(), entity.getKlantType());
        assertEquals(klantDto.getStraat(), entity.getStraat());
        assertEquals(klantDto.getHuisnummer(), entity.getHuisnummer());
        assertEquals(klantDto.getBus(), entity.getBus());
        assertEquals(klantDto.getPostcode(), entity.getPostcode());
        assertEquals(klantDto.getGemeente(), entity.getGemeente());
        assertEquals(klantDto.getLand(), entity.getLand());
        assertEquals(klantDto.getTelefoonnummer(), entity.getTelefoonnummer());
        assertEquals(klantDto.getMobiel(), entity.getMobiel());
        assertEquals(klantDto.getEmail(), entity.getEmail());
        assertEquals(klantDto.getBtwNummer(), entity.getBtwNummer());
        assertEquals(klantDto.getOpmerkingen(), entity.getOpmerkingen());
    }
}
