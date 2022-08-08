package be.one16.barka.domain.events.leverancier;

import java.util.UUID;

public record LeverancierCreatedEvent(UUID leverancierId, String naam) {
}
