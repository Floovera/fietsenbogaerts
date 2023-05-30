package be.one16.barka.klant.ports.in.materiaal;
import be.one16.barka.klant.domain.Materiaal;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

public interface MaterialenQuery {

    @Transactional(readOnly = true)
    Materiaal retrieveMateriaalById(UUID id, UUID verkoopId);

    @Transactional(readOnly = true)
    List<Materiaal> retrieveMaterialenOfVerkoop(UUID verkoopId);

}
