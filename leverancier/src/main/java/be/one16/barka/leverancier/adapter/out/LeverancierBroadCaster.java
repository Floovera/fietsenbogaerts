package be.one16.barka.leverancier.adapter.out;

import be.one16.barka.domain.events.leverancier.LeverancierCreatedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierDeletedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierUpdatedEvent;
import be.one16.barka.leverancier.adapter.mapper.LeverancierDtoMapper;
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
    private final LeverancierDtoMapper leverancierDtoMapper;

    public LeverancierBroadCaster(ApplicationEventPublisher applicationEventPublisher, LeverancierDtoMapper leverancierDtoMapper) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.leverancierDtoMapper = leverancierDtoMapper;
    }

    @Override
    public void createLeverancier(Leverancier leverancier) {
        applicationEventPublisher.publishEvent(new LeverancierCreatedEvent(leverancierDtoMapper.mapLeverancierToMessage(leverancier)));
    }

    @Override
    public void updateLeverancier(Leverancier leverancier) {
        applicationEventPublisher.publishEvent(new LeverancierUpdatedEvent(leverancierDtoMapper.mapLeverancierToMessage(leverancier)));
    }

    @Override
    public void deleteLeverancier(UUID id) {
        applicationEventPublisher.publishEvent(new LeverancierDeletedEvent(id));
    }

}
