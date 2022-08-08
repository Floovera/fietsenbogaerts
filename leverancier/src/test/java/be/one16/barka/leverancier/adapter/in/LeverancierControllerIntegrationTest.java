package be.one16.barka.leverancier.adapter.in;

import be.one16.barka.leverancier.BarkaLeveranciersApplication;
import be.one16.barka.leverancier.BaseIntegrationTesting;
import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import be.one16.barka.leverancier.adapter.out.repository.LeverancierRepository;
import be.one16.barka.leverancier.common.TestDataBuilder;
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
    private MockMvc mockMvc;

    private final Gson GSON = new Gson();

    @Test
    public void testGetLeverancierById() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe"));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON))
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
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetLeveranciersFilteredAndPaginated() throws Exception {
        leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Specialized Belgium"));
        leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Koga Benelux"));
        leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Scott Germany"));

        // No filter
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)));

        // Filtered on name
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?naam=be").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].naam").value("Specialized Belgium"))
                .andExpect(jsonPath("$.content[1].naam").value("Koga Benelux"));

        // Page size 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].naam").value("Specialized Belgium"))
                .andExpect(jsonPath("$.content[1].naam").value("Koga Benelux"));

        // Page size 1, page 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=1&page=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].naam").value("Scott Germany"));

        // Sorted on name
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=naam").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].naam").value("Koga Benelux"))
                .andExpect(jsonPath("$.content[1].naam").value("Scott Germany"))
                .andExpect(jsonPath("$.content[2].naam").value("Specialized Belgium"));

        // Sorted on name desc
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=naam,desc").contentType(APPLICATION_JSON))
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

        String response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<LeverancierJpaEntity> leverancierJpaEntity = leverancierRepository.findByUuid(UUID.fromString(response));
        Assertions.assertTrue(leverancierJpaEntity.isPresent());

        validateLeverancierJpaToLeverancierDto(leverancierJpaEntity.get(), leverancierToCreate);
    }

    @Test
    public void testCreateLeverancierInvalid() throws Exception {
        LeverancierDto leverancierToCreate = TestDataBuilder.generateTestLeverancierDto("Giant NL");

        leverancierToCreate.setNaam(null);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        leverancierToCreate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Castelli"));
        LeverancierDto leverancierToUpdate = TestDataBuilder.generateTestLeverancierDto("Castelli Benelux");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
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
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        leverancierToUpdate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdLeverancier.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateLeverancierNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        LeverancierDto leverancierToUpdate = TestDataBuilder.generateTestLeverancierDto("Peleton De Paris");
        leverancierToUpdate.setLeverancierId(uuidToUse);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + uuidToUse).contentType(APPLICATION_JSON).content(GSON.toJson(leverancierToUpdate)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteLeverancier() throws Exception {
        LeverancierJpaEntity createdLeverancier = leverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("Vive le velo"));

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + createdLeverancier.getUuid()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<LeverancierJpaEntity> deletedLeverancier = leverancierRepository.findByUuid(createdLeverancier.getUuid());
        assertTrue(deletedLeverancier.isEmpty());
    }

    @Test
    public void testDeleteLeverancierNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(status().isNotFound());
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
}
