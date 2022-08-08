package be.one16.barka.domain.events.leverancier;

import java.util.UUID;

public record LeverancierUpdatedEvent(UUID leverancierId, String naam){

}
