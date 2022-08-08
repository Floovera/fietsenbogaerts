package be.one16.barka.leverancier.core;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.ports.in.DeleteLeverancierUnitOfWork;
import be.one16.barka.leverancier.ports.out.LeverancierDeletePort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultDeleteLeverancierUnitOfWork implements DeleteLeverancierUnitOfWork {

    private final List<LeverancierDeletePort> leverancierDeletePorts;

    public DefaultDeleteLeverancierUnitOfWork(List<LeverancierDeletePort> leverancierDeletePorts) {
        this.leverancierDeletePorts = leverancierDeletePorts;
    }

    @Override
    public void deleteLeverancier(UUID id) {
        leverancierDeletePorts.forEach(port -> port.deleteLeverancier(id));
    }
}