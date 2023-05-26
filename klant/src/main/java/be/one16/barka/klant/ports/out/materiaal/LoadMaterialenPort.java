package be.one16.barka.klant.ports.out.materiaal;
import be.one16.barka.klant.domain.Materiaal;
import java.util.List;
import java.util.UUID;

public interface LoadMaterialenPort {

    Materiaal retrieveMateriaalOfVerkoop(UUID id, UUID verkoopId);

    List<Materiaal> retrieveMaterialenOfVerkoop(UUID verkoopId);

}
