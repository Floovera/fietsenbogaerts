package be.one16.barka.klant.core.materiaal;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.in.materiaal.MaterialenQuery;
import be.one16.barka.klant.ports.out.materiaal.LoadMaterialenPort;
import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultMaterialenQuery implements MaterialenQuery {

    private final LoadMaterialenPort loadMaterialenPort;

    public DefaultMaterialenQuery(LoadMaterialenPort loadMaterialenPort) {
        this.loadMaterialenPort = loadMaterialenPort;
    }

    @Override
    public Materiaal retrieveMateriaalById(UUID id, UUID orderId) {
        return loadMaterialenPort.retrieveMateriaalOfOrder(id,orderId);
    }

    @Override
    public List<Materiaal> retrieveMaterialenOfOrder(UUID orderId) {
        return loadMaterialenPort.retrieveMaterialenOfOrder(orderId);
    }

}
