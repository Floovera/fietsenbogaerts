package be.one16.barka.domain.events.verkoop;

import java.util.UUID;

public record VerkoopUpdatedEvent(UUID id, String naam) {
}
