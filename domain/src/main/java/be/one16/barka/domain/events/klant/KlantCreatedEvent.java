package be.one16.barka.domain.events.klant;

import java.util.UUID;

public record KlantCreatedEvent (UUID id, String naam) {
}
