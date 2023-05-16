package be.one16.barka.klant.adapter.out.verkoop;

import be.one16.barka.domain.events.verkoop.VerkoopCreatedEvent;
import be.one16.barka.domain.events.verkoop.VerkoopDeletedEvent;
import be.one16.barka.domain.events.verkoop.VerkoopUpdatedEvent;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.port.out.verkoop.CreateVerkoopPort;
import be.one16.barka.klant.port.out.verkoop.DeleteVerkoopPort;
import be.one16.barka.klant.port.out.verkoop.UpdateVerkoopPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Order
public class VerkoopBroadCaster implements CreateVerkoopPort, UpdateVerkoopPort, DeleteVerkoopPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public VerkoopBroadCaster(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void createVerkoop(Verkoop verkoop) {
        applicationEventPublisher.publishEvent(new VerkoopCreatedEvent(verkoop.getVerkoopId(), verkoop.getNaam()));
    }

    @Override
    public void updateVerkoop(Verkoop verkoop) {
        applicationEventPublisher.publishEvent(new VerkoopUpdatedEvent(verkoop.getVerkoopId(), verkoop.getNaam()));
    }

    @Override
    public void deleteVerkoop(UUID id) {
        applicationEventPublisher.publishEvent(new VerkoopDeletedEvent(id));
    }
}
