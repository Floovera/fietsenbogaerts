package be.one16.barka.domain.events.order;

import java.util.UUID;

public record OrderUpdatedEvent(UUID id, String naam) {
}
