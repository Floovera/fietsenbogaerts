package be.one16.barka.klant.ports.in.werkuur;

import java.util.UUID;

public record DeleteWerkuurCommand(UUID werkuurId, UUID orderId) {
}
