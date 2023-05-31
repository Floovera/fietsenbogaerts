package be.one16.barka.klant.ports.out.werkuur;
import be.one16.barka.klant.domain.Werkuur;

import java.util.List;
import java.util.UUID;

public interface LoadWerkurenPort {

    Werkuur retrieveWerkuurOfOrder(UUID id, UUID orderId);

    List<Werkuur> retrieveWerkurenOfOrder(UUID orderId);

}
