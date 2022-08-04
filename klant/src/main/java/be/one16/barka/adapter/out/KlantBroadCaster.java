package be.one16.barka.adapter.out;

import be.one16.barka.domain.Klant;
import be.one16.barka.domain.events.KlantCreatedEvent;
import be.one16.barka.domain.events.KlantDeletedEvent;
import be.one16.barka.domain.events.KlantUpdatedEvent;
import be.one16.barka.port.out.CreateKlantPort;
import be.one16.barka.port.out.DeleteKlantPort;
import be.one16.barka.port.out.UpdateKlantPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Order
public class KlantBroadCaster implements CreateKlantPort, UpdateKlantPort, DeleteKlantPort {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void createKlant(Klant klant) {
        applicationEventPublisher.publishEvent(new KlantCreatedEvent(klant.getKlantId(), klant.getNaam()));
    }

    @Override
    public void updateKlant(Klant klant) {
        applicationEventPublisher.publishEvent(new KlantUpdatedEvent(klant.getKlantId(), klant.getNaam()));
    }

    @Override
    public void deleteKlant(UUID id) {
        applicationEventPublisher.publishEvent(new KlantDeletedEvent(id));
    }
}
