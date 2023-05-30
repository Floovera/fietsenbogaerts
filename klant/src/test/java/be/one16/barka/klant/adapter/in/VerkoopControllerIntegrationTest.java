package be.one16.barka.klant.adapter.in;

import be.one16.barka.klant.BarkaKlantenApplication;
import be.one16.barka.klant.BaseIntegrationTesting;
import be.one16.barka.klant.adapter.in.klant.KlantDto;
import be.one16.barka.klant.adapter.in.verkoop.VerkoopDto;
import be.one16.barka.klant.adapter.out.repository.VerkoopRepository;
import be.one16.barka.klant.adapter.out.verkoop.VerkoopJpaEntity;
import be.one16.barka.klant.common.TestDataBuilder;
import com.google.gson.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDate;
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
@SpringBootTest(classes = BarkaKlantenApplication.class)
@Transactional
public class VerkoopControllerIntegrationTest extends BaseIntegrationTesting {

    private final static String BASE_URL = "/api/verkopen";

    @Autowired
    private VerkoopRepository verkoopRepository;

    @Autowired
    private MockMvc mockMvc;

    private Gson GSON;

    @BeforeEach
    void setUp() {
        GSON = new Gson().newBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter())
                .create();
    }

    @Test
    public void testGetVerkoopById() throws Exception {
        VerkoopJpaEntity createdVerkoop = verkoopRepository.save(TestDataBuilder.generateTestVerkoopJpaEntity("Fietsbel"));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + createdVerkoop.getUuid()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.verkoopId").value(createdVerkoop.getUuid().toString()))
                .andExpect(jsonPath("$.naam").value(createdVerkoop.getNaam()))
                .andExpect(jsonPath("$.opmerkingen").value(createdVerkoop.getOpmerkingen()));
    }

    @Test
    public void testGetVerkoopByIdNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetVerkopenFilteredAndPaginated() throws Exception {
        verkoopRepository.save(TestDataBuilder.generateTestVerkoopJpaEntity("Fietsbel"));
        verkoopRepository.save(TestDataBuilder.generateTestVerkoopJpaEntity("Licht"));
        verkoopRepository.save(TestDataBuilder.generateTestVerkoopJpaEntity("Zadel"));

        // No filter
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)));

        // Filtered on name
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?naam=ich").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].naam").value("Licht"));

        // Page size 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].naam").value("Fietsbel"))
                .andExpect(jsonPath("$.content[1].naam").value("Licht"));

        // Page size 1, page 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=1&page=1").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].naam").value("Licht"));

        // Sorted on name
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=naam").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].naam").value("Fietsbel"))
                .andExpect(jsonPath("$.content[1].naam").value("Licht"))
                .andExpect(jsonPath("$.content[2].naam").value("Zadel"));

        // Sorted on name desc
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=naam,desc").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].naam").value("Zadel"))
                .andExpect(jsonPath("$.content[1].naam").value("Licht"))
                .andExpect(jsonPath("$.content[2].naam").value("Fietsbel"));
    }

    @Test
    public void testCreateVerkoop() throws Exception {
        VerkoopDto verkoopToCreate = TestDataBuilder.generateTestVerkoopDto("Drink fles houder");

        String response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<VerkoopJpaEntity> verkoopJpaEntity = verkoopRepository.findByUuid(UUID.fromString(response));
        Assertions.assertTrue(verkoopJpaEntity.isPresent());

        validateVerkoopJpaToVerkoopDto(verkoopJpaEntity.get(), verkoopToCreate);
    }

    @Test
    public void testCreateVerkoopWithClient() throws Exception {
        KlantDto klant = TestDataBuilder.generateTestKlantDto("Floo");
        UUID klantUUID = klant.getKlantId();
        VerkoopDto verkoopToCreate = TestDataBuilder.generateTestVerkoopWithClientDto("Drink fles houder",klantUUID);


        String response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<VerkoopJpaEntity> verkoopJpaEntity = verkoopRepository.findByUuid(UUID.fromString(response));
        Assertions.assertTrue(verkoopJpaEntity.isPresent());

        validateVerkoopJpaToVerkoopDto(verkoopJpaEntity.get(), verkoopToCreate);
    }

    @Test
    public void testCreateVerkoopInvalid() throws Exception {
        VerkoopDto verkoopToCreate = TestDataBuilder.generateTestVerkoopDto("Band");

        verkoopToCreate.setNaam(null);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verkoopToCreate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateVerkoop() throws Exception {
        VerkoopJpaEntity createdVerkoop = verkoopRepository.save(TestDataBuilder.generateTestVerkoopJpaEntity("Wiel"));
        VerkoopDto verkoopToUpdate = TestDataBuilder.generateTestVerkoopDto("Spaken");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdVerkoop.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<VerkoopJpaEntity> updatedVerkoop = verkoopRepository.findByUuid(createdVerkoop.getUuid());
        assertTrue(updatedVerkoop.isPresent());

        validateVerkoopJpaToVerkoopDto(updatedVerkoop.get(), verkoopToUpdate);
    }

    @Test
    public void testUpdateVerkoopInvalid() throws Exception {
        VerkoopJpaEntity createdVerkoop = verkoopRepository.save(TestDataBuilder.generateTestVerkoopJpaEntity("Bagagedrager"));
        VerkoopDto verkoopToUpdate = TestDataBuilder.generateTestVerkoopDto("Rekker");

        verkoopToUpdate.setNaam(null);
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdVerkoop.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verkoopToUpdate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdVerkoop.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateVerkoopNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        VerkoopDto verkoopToUpdate = TestDataBuilder.generateTestVerkoopDto("Band");
        verkoopToUpdate.setVerkoopId(uuidToUse);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + uuidToUse).contentType(APPLICATION_JSON).content(GSON.toJson(verkoopToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteVerkoop() throws Exception {
        VerkoopJpaEntity createdVerkoop = verkoopRepository.save(TestDataBuilder.generateTestVerkoopJpaEntity("Ketting"));

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + createdVerkoop.getUuid()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<VerkoopJpaEntity> deletedVerkoop = verkoopRepository.findByUuid(createdVerkoop.getUuid());
        assertTrue(deletedVerkoop.isEmpty());
    }

    @Test
    public void testDeleteVerkoopNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private void validateVerkoopJpaToVerkoopDto(VerkoopJpaEntity entity, VerkoopDto verkoopDto) {
        assertEquals(verkoopDto.getNaam(), entity.getNaam());
        assertEquals(verkoopDto.getOpmerkingen(), entity.getOpmerkingen());
    }
}
