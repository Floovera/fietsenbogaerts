package be.one16.barka.klant.ports.in.werkuur;

import be.one16.barka.klant.domain.Werkuur;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


public interface WerkurenQuery {

    @Transactional(readOnly = true)
    Werkuur retrieveWerkuurById(UUID id, UUID verkoopId);

    @Transactional(readOnly = true)
    List<Werkuur> retrieveWerkurenOfVerkoop(UUID verkoopId);

}