package be.one16.barka.leverancier.adapter.out;

import be.one16.barka.domain.events.leverancier.LeverancierCreatedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierDeletedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierUpdatedEvent;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierCreatePort;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierDeletePort;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierUpdatePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LeverancierBroadCaster implements LeverancierCreatePort, LeverancierUpdatePort, LeverancierDeletePort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public LeverancierBroadCaster(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void createLeverancier(Leverancier leverancier) {
        applicationEventPublisher.publishEvent(new LeverancierCreatedEvent(leverancier.getLeverancierId(), leverancier.getNaam()));
    }

    @Override
    public void updateLeverancier(Leverancier leverancier) {
        applicationEventPublisher.publishEvent(new LeverancierUpdatedEvent(leverancier.getLeverancierId(), leverancier.getNaam()));
    }

    @Override
    public void deleteLeverancier(UUID id) {
        applicationEventPublisher.publishEvent(new LeverancierDeletedEvent(id));
    }

}
