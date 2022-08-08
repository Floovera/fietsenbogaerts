package be.one16.barka.magazijn.adapters.in;

import be.one16.barka.magazijn.BarkaMagazijnApplication;
import be.one16.barka.magazijn.BaseIntegrationTesting;
import be.one16.barka.magazijn.adapters.out.ArtikelJpaEntity;
import be.one16.barka.magazijn.adapters.out.ArtikelLeverancierJpaEntity;
import be.one16.barka.magazijn.adapters.out.repository.ArtikelLeverancierRepository;
import be.one16.barka.magazijn.adapters.out.repository.ArtikelRepository;
import be.one16.barka.magazijn.common.TestDataBuilder;
import com.google.gson.Gson;
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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

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
@SpringBootTest(classes = BarkaMagazijnApplication.class)
@Transactional
public class ArtikelControllerIntegrationTest extends BaseIntegrationTesting {

    private final static String BASE_URL = "/api/artikels";

    @Autowired
    private ArtikelRepository artikelRepository;

    @Autowired
    private ArtikelLeverancierRepository artikelLeverancierRepository;

    @Autowired
    private MockMvc mockMvc;

    private final Gson GSON = new Gson();

    @Test
    public void testGetArtikelById() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancier = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelJpaEntity createdArtikel = artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancier, "ART-001", "Dynamic", "Dynamic Lens Love Spray 33ml"));

        mockMvc.perform(get(BASE_URL + "/" + createdArtikel.getUuid()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artikelId").value(createdArtikel.getUuid().toString()))
                .andExpect(jsonPath("$.merk").value(createdArtikel.getMerk()))
                .andExpect(jsonPath("$.code").value(createdArtikel.getCode()))
                .andExpect(jsonPath("$.omschrijving").value(createdArtikel.getOmschrijving()))
                .andExpect(jsonPath("$.leverancierId").value(createdArtikel.getLeverancier().getUuid().toString()))
                .andExpect(jsonPath("$.leverancier").value(createdArtikel.getLeverancier().getNaam()))
                .andExpect(jsonPath("$.aantalInStock").value(createdArtikel.getAantalInStock()))
                .andExpect(jsonPath("$.minimumInStock").value(createdArtikel.getMinimumInStock()))
                .andExpect(jsonPath("$.aankoopPrijs").value(createdArtikel.getAankoopPrijs()))
                .andExpect(jsonPath("$.verkoopPrijs").value(createdArtikel.getVerkoopPrijs()))
                .andExpect(jsonPath("$.actuelePrijs").value(createdArtikel.getActuelePrijs()));
    }

    @Test
    public void testGetArtikelByIdNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();
        mockMvc.perform(get(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetArtikelsFilteredAndPaginated() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancierOne = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelLeverancierJpaEntity createdLeverancierTwo = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("ABUS BE"));

        artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancierOne, "ART-010", "Dynamic", "Dynamic Lens Love Spray 33ml"));
        artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancierOne, "ART-011", "Dynamic", "Dynamic Binnenband 700x23/32"));
        artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancierTwo, "ART-012", "Abus", "Abus Binnenband 700x35/45"));

        // No filter
        mockMvc.perform(get(BASE_URL).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)));

        // Filter on code
        mockMvc.perform(get(BASE_URL + "?code=11").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].code").value("ART-011"));

        // Filter on merk
        mockMvc.perform(get(BASE_URL + "?merk=Dynam").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].merk").value("Dynamic"))
                .andExpect(jsonPath("$.content[1].merk").value("Dynamic"));

        // Filter on omschrijving
        mockMvc.perform(get(BASE_URL + "?omschrijving=band").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].omschrijving").value("Dynamic Binnenband 700x23/32"))
                .andExpect(jsonPath("$.content[1].omschrijving").value("Abus Binnenband 700x35/45"));

        // Filter on leverancier
        mockMvc.perform(get(BASE_URL + "?leverancier=" + createdLeverancierOne.getUuid()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].leverancierId").value(createdLeverancierOne.getUuid().toString()))
                .andExpect(jsonPath("$.content[0].leverancier").value(createdLeverancierOne.getNaam()))
                .andExpect(jsonPath("$.content[0].leverancierId").value(createdLeverancierOne.getUuid().toString()))
                .andExpect(jsonPath("$.content[0].leverancier").value(createdLeverancierOne.getNaam()));

        // Page size 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].code").value("ART-010"))
                .andExpect(jsonPath("$.content[1].code").value("ART-011"));

        // Page size 1, page 2
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?size=1&page=2").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(1)))
                .andExpect(jsonPath("$.content[0].code").value("ART-012"));

        // Sorted on merk
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=merk").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].merk").value("Abus"))
                .andExpect(jsonPath("$.content[1].merk").value("Dynamic"))
                .andExpect(jsonPath("$.content[2].merk").value("Dynamic"));

        // Sorted on merk desc
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "?sort=merk,desc").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.*", hasSize(3)))
                .andExpect(jsonPath("$.content[0].merk").value("Dynamic"))
                .andExpect(jsonPath("$.content[1].merk").value("Dynamic"))
                .andExpect(jsonPath("$.content[2].merk").value("Abus"));
    }

    @Test
    public void testCreateArtikel() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancier = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelDto artikelToCreate = TestDataBuilder.generateTestArtikelDto(createdLeverancier.getUuid(), "AUI30F", "ABUS", "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm");

        String response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<ArtikelJpaEntity> artikelJpaEntity = artikelRepository.findByUuid(UUID.fromString(response));
        assertTrue(artikelJpaEntity.isPresent());

        validateArtikelJpaToArtikelDto(artikelJpaEntity.get(), artikelToCreate);
    }

    @Test
    public void testCreateArtikelInvalidRequestData() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancier = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelDto artikelToCreate = TestDataBuilder.generateTestArtikelDto(createdLeverancier.getUuid(), "AUI31F", "ABUS", "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm");

        artikelToCreate.setCode(null);
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        artikelToCreate.setCode("");
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        artikelToCreate.setCode("AUI32F");
        artikelToCreate.setLeverancierId(UUID.randomUUID());
        mockMvc.perform(post(BASE_URL ).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToCreate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreateArtikelInvalidData() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancier = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelJpaEntity existingArtikel = artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancier, "ART-001", "Dynamic", "Dynamic Lens Love Spray 33ml"));

        ArtikelDto artikelToCreate = TestDataBuilder.generateTestArtikelDto(createdLeverancier.getUuid(), existingArtikel.getCode(), "ABUS", "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm");

        // Already existing code
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        artikelToCreate.setCode("ART-100");

        // VerkoopPrijs lower then AankoopPrijs
        artikelToCreate.setVerkoopPrijs(BigDecimal.valueOf(artikelToCreate.getAankoopPrijs().doubleValue() - 0.5));
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // ActuelePrijs lower than AankoopPrijs
        artikelToCreate.setActuelePrijs(BigDecimal.valueOf(artikelToCreate.getAankoopPrijs().doubleValue() - 0.5));
        mockMvc.perform(post(BASE_URL ).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateArtikel() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancierOne = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelLeverancierJpaEntity createdLeverancierTwo = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("ABUS BE"));

        ArtikelJpaEntity createdArtikel = artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancierOne, "ART-041", "Koga", "Koga Mountainbike Blue X29-Runner"));
        ArtikelDto artikelToUpdate = TestDataBuilder.generateTestArtikelDto(createdLeverancierTwo.getUuid(), "ART-042", "Scott", "Scott Mountainbike Black 980");

        mockMvc.perform(put(BASE_URL + "/" + createdArtikel.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ArtikelJpaEntity> updatedArtikel = artikelRepository.findByUuid(createdArtikel.getUuid());
        assertTrue(updatedArtikel.isPresent());

        validateArtikelJpaToArtikelDto(updatedArtikel.get(), artikelToUpdate);
    }

    @Test
    public void testUpdateArtikelInvalidRequestData() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancier = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));

        ArtikelJpaEntity createdArtikel = artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancier, "ART-041", "Koga", "Koga Mountainbike Blue X29-Runner"));
        ArtikelDto artikelToUpdate = TestDataBuilder.generateTestArtikelDto(createdLeverancier.getUuid(), "ART-043", "Scott", "Scott Mountainbike Black 980");

        artikelToUpdate.setCode(null);
        mockMvc.perform(put(BASE_URL + "/" + createdArtikel.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        artikelToUpdate.setCode("");
        mockMvc.perform(put(BASE_URL + "/" + createdArtikel.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        artikelToUpdate.setCode("ART-044");
        artikelToUpdate.setLeverancierId(UUID.randomUUID());
        mockMvc.perform(put(BASE_URL + "/" + createdArtikel.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testUpdateArtikelInvalidData() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancier = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelJpaEntity existingArtikel = artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancier, "ART-001", "Dynamic", "Dynamic Lens Love Spray 33ml"));
        ArtikelJpaEntity createdArtikelToUpdate = artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancier, "ART-041", "Koga", "Koga Mountainbike Blue X29-Runner"));

        ArtikelDto artikelToUpdate = TestDataBuilder.generateTestArtikelDto(createdLeverancier.getUuid(), existingArtikel.getCode(), "Scott", "Scott Mountainbike Black 980");

        // Already existing code
        mockMvc.perform(put(BASE_URL + "/" + createdArtikelToUpdate.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        artikelToUpdate.setCode("ART-200");

        // VerkoopPrijs lower then AankoopPrijs
        artikelToUpdate.setVerkoopPrijs(BigDecimal.valueOf(artikelToUpdate.getAankoopPrijs().doubleValue() - 0.5));
        mockMvc.perform(put(BASE_URL + "/" + createdArtikelToUpdate.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // ActuelePrijs lower than AankoopPrijs
        artikelToUpdate.setActuelePrijs(BigDecimal.valueOf(artikelToUpdate.getAankoopPrijs().doubleValue() - 0.5));
        mockMvc.perform(put(BASE_URL + "/" + createdArtikelToUpdate.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateArtikelNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        ArtikelDto artikelToUpdate = TestDataBuilder.generateTestArtikelDto(UUID.randomUUID(), "ART-050", "Specialized", "Specialized Race fiets 2de-hands 2002");
        artikelToUpdate.setArtikelId(uuidToUse);

        mockMvc.perform(put(BASE_URL + "/" + uuidToUse).contentType(APPLICATION_JSON).content(GSON.toJson(artikelToUpdate)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteArtikel() throws Exception {
        ArtikelLeverancierJpaEntity createdLeverancier = artikelLeverancierRepository.save(TestDataBuilder.generateTestLeverancierJpaEntity("DYNAMIC BE"));
        ArtikelJpaEntity createdArtikel = artikelRepository.save(TestDataBuilder.generateTestArtikelJpaEntity(createdLeverancier, "ART-050", "Cube", "Cube Attain Race fiets"));

        mockMvc.perform(delete(BASE_URL + "/" + createdArtikel.getUuid()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<ArtikelJpaEntity> deletedArtikel = artikelRepository.findByUuid(createdArtikel.getUuid());
        assertTrue(deletedArtikel.isEmpty());
    }

    @Test
    public void testDeleteArtikelNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        mockMvc.perform(delete(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private void validateArtikelJpaToArtikelDto(ArtikelJpaEntity entity, ArtikelDto artikelDto) {
        assertEquals(artikelDto.getMerk(), entity.getMerk());
        assertEquals(artikelDto.getCode(), entity.getCode());
        assertEquals(artikelDto.getOmschrijving(), entity.getOmschrijving());
        assertEquals(artikelDto.getLeverancierId(), entity.getLeverancier().getUuid());
        assertEquals(artikelDto.getAantalInStock(), entity.getAantalInStock());
        assertEquals(artikelDto.getMinimumInStock(), entity.getMinimumInStock());
        assertEquals(artikelDto.getAankoopPrijs(), entity.getAankoopPrijs());
        assertEquals(artikelDto.getVerkoopPrijs(), entity.getVerkoopPrijs());
        assertEquals(artikelDto.getActuelePrijs(), entity.getActuelePrijs());
    }

}
