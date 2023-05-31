package be.one16.barka.klant.ports.in.materiaal;
import java.util.UUID;

public record DeleteMateriaalCommand(UUID materiaalId, UUID orderId) {
}
