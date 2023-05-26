package be.one16.barka.klant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Verkoop {

    private UUID verkoopId;
    private String naam;
    private String opmerkingen;
    private LocalDate datum;
    private UUID klantId;

}
