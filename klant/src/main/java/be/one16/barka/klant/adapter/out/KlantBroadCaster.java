package be.one16.barka.klant.adapter.out;

import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.events.klant.KlantCreatedEvent;
import be.one16.barka.klant.domain.events.klant.KlantDeletedEvent;
import be.one16.barka.klant.domain.events.klant.KlantUpdatedEvent;
import be.one16.barka.klant.port.out.CreateKlantPort;
import be.one16.barka.klant.port.out.DeleteKlantPort;
import be.one16.barka.klant.port.out.UpdateKlantPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Order
public class KlantBroadCaster implements CreateKlantPort, UpdateKlantPort, DeleteKlantPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public KlantBroadCaster(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

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
