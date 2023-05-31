package be.one16.barka.klant.ports.out.materiaal;
import java.util.UUID;

public interface MateriaalDeletePort {

    void deleteMateriaal(UUID materiaalId, UUID orderId);

}
