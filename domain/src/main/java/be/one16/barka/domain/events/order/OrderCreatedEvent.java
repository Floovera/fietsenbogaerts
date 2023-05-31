package be.one16.barka.domain.events.order;

import java.util.UUID;

public record OrderCreatedEvent(UUID id, String naam) {
}
