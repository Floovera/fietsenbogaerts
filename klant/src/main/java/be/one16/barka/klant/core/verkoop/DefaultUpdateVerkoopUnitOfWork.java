package be.one16.barka.klant.core.verkoop;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.port.in.klant.UpdateKlantCommand;
import be.one16.barka.klant.port.in.klant.UpdateKlantUnitOfWork;
import be.one16.barka.klant.port.in.verkoop.UpdateVerkoopCommand;
import be.one16.barka.klant.port.in.verkoop.UpdateVerkoopUnitOfWork;
import be.one16.barka.klant.port.out.klant.UpdateKlantPort;
import be.one16.barka.klant.port.out.verkoop.UpdateVerkoopPort;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static be.one16.barka.klant.common.KlantType.ONBEKEND;

@UnitOfWork
public class DefaultUpdateVerkoopUnitOfWork implements UpdateVerkoopUnitOfWork {

    private final List<UpdateVerkoopPort> updateVerkoopPorts;

    public DefaultUpdateVerkoopUnitOfWork(List<UpdateVerkoopPort> updateVerkoopPorts) {
        this.updateVerkoopPorts = updateVerkoopPorts;
    }

    @Override
    public void updateVerkoop(UpdateVerkoopCommand updateVerkoopCommand) {
        if (StringUtils.isEmpty(updateVerkoopCommand.naam())) {
            throw new IllegalArgumentException("Value for 'naam' can not be null or empty");
        }

        Verkoop verkoop = Verkoop.builder()
                .verkoopId(updateVerkoopCommand.verkoopId())
                .naam(updateVerkoopCommand.naam())
                .opmerkingen(updateVerkoopCommand.opmerkingen())
                .klantId(updateVerkoopCommand.klantId())
                .build();

        updateVerkoopPorts.forEach(port -> port.updateVerkoop(verkoop));
    }
}
