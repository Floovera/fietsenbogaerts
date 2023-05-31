package be.one16.barka.klant.adapter.in;

import be.one16.barka.klant.BarkaKlantenApplication;
import be.one16.barka.klant.BaseIntegrationTesting;
import be.one16.barka.klant.adapter.in.klant.KlantDto;
import be.one16.barka.klant.adapter.in.order.OrderDto;
import be.one16.barka.klant.adapter.out.order.OrderJpaEntity;
import be.one16.barka.klant.adapter.out.repository.OrderRepository;
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
public class OrderControllerIntegrationTest extends BaseIntegrationTesting {

    private final static String BASE_URL = "/api/orders";

    @Autowired
    private OrderRepository orderRepository;

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
    public void testGetOrderById() throws Exception {
        OrderJpaEntity createdOrder = orderRepository.save(TestDataBuilder.generateTestOrderJpaEntity("Fietsbel"));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + createdOrder.getUuid()).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.orderId").value(createdOrder.getUuid().toString()))
                .andExpect(jsonPath("$.naam").value(createdOrder.getNaam()))
                .andExpect(jsonPath("$.opmerkingen").value(createdOrder.getOpmerkingen()));
    }

    @Test
    public void testGetOrderByIdNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetOrdersFilteredAndPaginated() throws Exception {
        orderRepository.save(TestDataBuilder.generateTestOrderJpaEntity("Fietsbel"));
        orderRepository.save(TestDataBuilder.generateTestOrderJpaEntity("Licht"));
        orderRepository.save(TestDataBuilder.generateTestOrderJpaEntity("Zadel"));

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
    public void testCreateOrder() throws Exception {
        OrderDto orderToCreate = TestDataBuilder.generateTestOrderDto("Drink fles houder");

        String response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(orderToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<OrderJpaEntity> orderJpaEntity = orderRepository.findByUuid(UUID.fromString(response));
        Assertions.assertTrue(orderJpaEntity.isPresent());

        validateOrderJpaToOrderDto(orderJpaEntity.get(), orderToCreate);
    }

    @Test
    public void testCreateOrderWithClient() throws Exception {
        KlantDto klant = TestDataBuilder.generateTestKlantDto("Floo");
        UUID klantUUID = klant.getKlantId();
        OrderDto orderToCreate = TestDataBuilder.generateTestOrderWithClientDto("Drink fles houder",klantUUID);


        String response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(orderToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        Optional<OrderJpaEntity> orderJpaEntity = orderRepository.findByUuid(UUID.fromString(response));
        Assertions.assertTrue(orderJpaEntity.isPresent());

        validateOrderJpaToOrderDto(orderJpaEntity.get(), orderToCreate);
    }

    @Test
    public void testCreateOrderInvalid() throws Exception {
        OrderDto orderToCreate = TestDataBuilder.generateTestOrderDto("Band");

        orderToCreate.setNaam(null);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(orderToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        orderToCreate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(APPLICATION_JSON).content(GSON.toJson(orderToCreate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        OrderJpaEntity createdOrder = orderRepository.save(TestDataBuilder.generateTestOrderJpaEntity("Wiel"));
        OrderDto orderToUpdate = TestDataBuilder.generateTestOrderDto("Spaken");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdOrder.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(orderToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<OrderJpaEntity> updatedOrder = orderRepository.findByUuid(createdOrder.getUuid());
        assertTrue(updatedOrder.isPresent());

        validateOrderJpaToOrderDto(updatedOrder.get(), orderToUpdate);
    }

    @Test
    public void testUpdateOrderInvalid() throws Exception {
        OrderJpaEntity createdOrder = orderRepository.save(TestDataBuilder.generateTestOrderJpaEntity("Bagagedrager"));
        OrderDto orderToUpdate = TestDataBuilder.generateTestOrderDto("Rekker");

        orderToUpdate.setNaam(null);
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdOrder.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(orderToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        orderToUpdate.setNaam("");
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + createdOrder.getUuid()).contentType(APPLICATION_JSON).content(GSON.toJson(orderToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateOrderNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        OrderDto orderToUpdate = TestDataBuilder.generateTestOrderDto("Band");
        orderToUpdate.setOrderId(uuidToUse);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + uuidToUse).contentType(APPLICATION_JSON).content(GSON.toJson(orderToUpdate)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        OrderJpaEntity createdOrder = orderRepository.save(TestDataBuilder.generateTestOrderJpaEntity("Ketting"));

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + createdOrder.getUuid()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Optional<OrderJpaEntity> deletedOrder = orderRepository.findByUuid(createdOrder.getUuid());
        assertTrue(deletedOrder.isEmpty());
    }

    @Test
    public void testDeleteOrderNotExisting() throws Exception {
        UUID uuidToUse = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + uuidToUse))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private void validateOrderJpaToOrderDto(OrderJpaEntity entity, OrderDto orderDto) {
        assertEquals(orderDto.getNaam(), entity.getNaam());
        assertEquals(orderDto.getOpmerkingen(), entity.getOpmerkingen());
    }
}
