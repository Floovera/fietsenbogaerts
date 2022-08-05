package be.one16.barka.magazijn.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Leverancier {

    private UUID leverancierId;
    private String naam;

}
