package be.one16.barka.klant.adapter.in.order;

import be.one16.barka.klant.common.OrderType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class OrderDto {

    private UUID orderId;
    private OrderType orderType;
    private String naam;
    private String opmerkingen;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate datum;
    private UUID klantId;

    private String reparatieNummer;
    private String orderNummer;

    private double totaalExclusBtw;
    private double totaalInclusBtw;
    private double btwBedrag;

}
