package be.one16.barka.klant.adapter.in.verkoop;

import lombok.Data;

import java.util.UUID;

@Data
public class VerkoopDto {

    private UUID verkoopId;
    private String naam;
    private String opmerkingen;
    private UUID klantId;

}
