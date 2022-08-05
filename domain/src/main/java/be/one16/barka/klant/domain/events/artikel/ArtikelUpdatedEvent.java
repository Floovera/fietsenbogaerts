package be.one16.barka.klant.domain.events.artikel;

import java.math.BigDecimal;
import java.util.UUID;

public record ArtikelUpdatedEvent(UUID id, String code, String omschrijving, String merk, BigDecimal actuelePrijs, UUID leverancierId){

}
