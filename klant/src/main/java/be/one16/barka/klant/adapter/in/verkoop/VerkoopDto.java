package be.one16.barka.klant.adapter.in.verkoop;

import be.one16.barka.klant.common.KlantType;
import be.one16.barka.klant.common.OrderType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class VerkoopDto {

    private UUID verkoopId;
    private OrderType orderType;
    private String naam;
    private String opmerkingen;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate datum;
    private UUID klantId;

}
