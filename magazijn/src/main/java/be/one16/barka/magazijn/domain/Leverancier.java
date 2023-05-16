package be.one16.barka.magazijn.domain;

import be.one16.barka.magazijn.common.StatusLeverancier;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Leverancier {

    private UUID leverancierId;
    private String naam;
    private StatusLeverancier status;

}
