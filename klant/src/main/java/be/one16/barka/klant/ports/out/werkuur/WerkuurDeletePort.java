package be.one16.barka.klant.ports.out.werkuur;

import java.util.UUID;

public interface WerkuurDeletePort {

    void deleteWerkuur(UUID werkuurId, UUID verkoopId);

}
