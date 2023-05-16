package be.one16.barka.domain.events.verkoop;

import java.util.UUID;

public record VerkoopCreatedEvent(UUID id, String naam) {
}
