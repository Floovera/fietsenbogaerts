package be.one16.barka.klant.domain;

import be.one16.barka.klant.common.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Order {

    private UUID orderId;
    private OrderType orderType;
    private String naam;
    private String opmerkingen;
    private LocalDate datum;
    private UUID klantId;

    private String reparatieNummer;
    private String orderNummer;

}
